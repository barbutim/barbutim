package cz.cvut.fel.nss.careerpages.rest;

import cz.cvut.fel.nss.careerpages.dto.AbstractUserDto;
import cz.cvut.fel.nss.careerpages.dto.ApplicantDto;
import cz.cvut.fel.nss.careerpages.dto.JobJournalDto;
import cz.cvut.fel.nss.careerpages.dto.RequestWrapper;
import cz.cvut.fel.nss.careerpages.exception.BadPassword;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.exception.UnauthorizedException;
import cz.cvut.fel.nss.careerpages.model.User;
import cz.cvut.fel.nss.careerpages.security.SecurityConstants;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import cz.cvut.fel.nss.careerpages.service.UserService;
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
 * Rest applicant controller
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = SecurityConstants.FRONTEND, allowCredentials="true")
public class ApplicantController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicantController.class);
    private final UserService userService;

    /**
     * @param userService
     * constructor
     */
    @Autowired
    public ApplicantController(UserService userService) {
        this.userService = userService;
    }

    /**
     * method create user
     * @param requestWrapper - JSON representation of User and password control
     * @return response Void
     * @throws BadPassword
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody RequestWrapper requestWrapper) throws BadPassword {
        userService.createUser((User) requestWrapper.getUser(), requestWrapper.getPassword_control());
        LOG.info("User {} created.", requestWrapper.getUser().getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * method return list of ApplicantDto for manager and admin
     * @return response of list userDto
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicantDto>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    /**
     * method return jobJournal of current user
     * @return response of JobJournalDto
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value= "/jobJournal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobJournalDto> getJobJournal() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getJobJournal());
    }

    /**
     * method return current logged in user
     * @return response of AbstractUserDto
     * @throws UnauthorizedException
     */
    @GetMapping(value= "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AbstractUserDto> showCurrentUser() throws UnauthorizedException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.showCurrentUser());
    }

    /**
     * method updated current user
     * @param applicantDto
     * @return void response
     * @throws NotFoundException
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody ApplicantDto applicantDto) throws NotFoundException {
        userService.update(applicantDto, SecurityUtils.getCurrentUser());
        LOG.info("User {} updated.", applicantDto.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * method delete user
     * @param id which user I want deleted
     * @return void response
     * @throws NotFoundException
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @DeleteMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable long id) throws NotFoundException {
        userService.delete(id);
        LOG.info("User with id:{} deleted.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

