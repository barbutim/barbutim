package cz.cvut.fel.nss.careerpages.rest;

import cz.cvut.fel.nss.careerpages.dto.ApplicationDto;
import cz.cvut.fel.nss.careerpages.dto.RequestWrapperEnrollment;
import cz.cvut.fel.nss.careerpages.dto.RequestWrapperEnrollmentGet;
import cz.cvut.fel.nss.careerpages.exception.NotAllowedException;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.security.SecurityConstants;
import cz.cvut.fel.nss.careerpages.service.ApplicationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Rest application controller
 */
@RestController
@CrossOrigin(origins = SecurityConstants.FRONTEND)
@RequestMapping("/enrollment")
public class ApplicationController {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);
    private final ApplicationFacade applicationFacade;

    /**
     * @param applicationFacade
     * constructor
     */
    @Autowired
    public ApplicationController(ApplicationFacade applicationFacade) {
        this.applicationFacade = applicationFacade;
    }

    /**
     * Method find and return enrollent by id. Available only for user with ADMIN or MANAGER role.
     * @param id
     * @return response with ApplicationDto
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationDto> get(@PathVariable Long id)  {
        return ResponseEntity.status(HttpStatus.OK).body(applicationFacade.get(id));
    }

    /**
     * Method find all completed enrollments of current user. Available only for user with USER role.
     * @return response with list of ApplicationDto
     * @throws NotAllowedException
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicationDto>> getAllOfUserFinished() throws NotAllowedException {
        return ResponseEntity.status(HttpStatus.OK).body(applicationFacade.getAllFinished());
    }

    /**
     * Method find all completed enrollments of user by user id. Available only for user with MANAGER of ADMIN role.
     * @return response with list of ApplicationDto
     * @throws NotAllowedException
     * @throws NotFoundException
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping(value = "/complete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicationDto>> getAllOfUserFinishedAdmin(@PathVariable Long id) throws NotAllowedException, NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(applicationFacade.getAllFinished(id));
    }

    /**
     * Method find all active enrollments of current user. Available only for user with USER role.
     * @return response with list of ApplicationDto
     * @throws NotAllowedException
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicationDto>> getAllOfUserActiveAndCancel() throws NotAllowedException {
        return ResponseEntity.status(HttpStatus.OK).body(applicationFacade.getAllActiveAndCancel());
    }

    /**
     * Method find all active enrollments of user by user id. Available only for user with MANAGER of ADMIN role.
     * @return response with list of ApplicationDto
     * @throws NotAllowedException
     * @throws NotFoundException
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping(value = "/active/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicationDto>> getAllOfUserActiveAndCancelAdmin(@PathVariable Long id) throws NotAllowedException, NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(applicationFacade.getAllActiveAndCancel(id));
    }

    /**
     * Method find all enrollments and theirs owner after end_date that should be finished. Available only for user with MANAGER of ADMIN role.
     * @return response with list of RequestWrapper (with ApplicationDto and ApplicantDto)
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping(value = "/close", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RequestWrapperEnrollmentGet>> getAllActiveEnded() {
        return ResponseEntity.status(HttpStatus.OK).body(applicationFacade.getAllWithUserToClose());
    }

    /**
     * Close concrete enrollment with edited reward and make user review. Available only for user with MANAGER role.
     * @param requestWrapperEnrollment - contains ApplicationDto and ApplicantDto
     * @return response 204
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PatchMapping(value = "/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> close(@RequestBody RequestWrapperEnrollment requestWrapperEnrollment) throws Exception {
        applicationFacade.close(requestWrapperEnrollment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Method find enrollment by id and its owner after end_date that should be finished. Available only for user with MANAGER of ADMIN role.
     * @return response with list of RequestWrapper (with ApplicationDto and ApplicantDto)
     * @throws NotAllowedException
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping(value = "/close/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestWrapperEnrollmentGet> getWithUser(@PathVariable Long id) throws NotAllowedException {
        return ResponseEntity.status(HttpStatus.OK).body(applicationFacade.getWithUserToClose(id));
    }

    /**
     * Close concrete enrollment by id with full reward. Available only for user with MANAGER role.
     * @return response 204
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping(value = "/close/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> closeOk(@PathVariable Long id) throws Exception {
        applicationFacade.closeFull(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Cancel enrollemt by id.
     * @param id
     * @return response 204
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @PostMapping(value = "cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> cancel(@PathVariable Long id) throws Exception {
        applicationFacade.cancel(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
