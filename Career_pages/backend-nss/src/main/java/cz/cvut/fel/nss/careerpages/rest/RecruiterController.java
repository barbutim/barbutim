package cz.cvut.fel.nss.careerpages.rest;

import cz.cvut.fel.nss.careerpages.dto.RecruiterDto;
import cz.cvut.fel.nss.careerpages.dto.RequestWrapper;
import cz.cvut.fel.nss.careerpages.exception.BadPassword;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import cz.cvut.fel.nss.careerpages.model.Recruiter;
import cz.cvut.fel.nss.careerpages.security.SecurityConstants;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import cz.cvut.fel.nss.careerpages.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Rest recruiter controller
 */
@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = SecurityConstants.FRONTEND, allowCredentials="true")
public class RecruiterController {

    private final RecruiterService recruiterService;

    /**
     * @param recruiterService
     * constructor
     */
    @Autowired
    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    /**
     * Method create new manager. Available only for user with ADMIN role.
     * @param requestWrapper - contains JSON representation of AbstractUser and attribute password_control
     * @return response 201
     * @throws BadPassword - exception when password not match password_control
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody RequestWrapper requestWrapper) throws BadPassword {
        recruiterService.create((Recruiter) requestWrapper.getUser(), requestWrapper.getPassword_control());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method show list of existing managers. Available only for user with ADMIN role.
     * @return list of ManagerDtos
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecruiterDto>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(recruiterService.findAll());
    }

    /**
     * Method show manager by id. Available only for user with ADMIN role.
     * @param id
     * @return response with RecruiterDto
     * @throws NotFoundException - when manager was not found by id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecruiterDto> show(@PathVariable Long id) throws NotFoundException {
        final RecruiterDto recruiterDto = recruiterService.find(id);
        if (recruiterDto == null) throw NotFoundException.create("Rectuiter", id);
        return ResponseEntity.status(HttpStatus.OK).body(recruiterDto);
    }

    /**
     * Update existing manager by id. Available only for user with ADMIN role.
     * @param id
     * @param user - JSON representation with updated body of manager
     * @return response 204
     * @throws NotFoundException - when manager was not found by id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AbstractUser user) throws NotFoundException {
        recruiterService.update((Recruiter) user, SecurityUtils.getCurrentUser());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
