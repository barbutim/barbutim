package cz.cvut.fel.nss.careerpages.rest;

import cz.cvut.fel.nss.careerpages.exception.AlreadyExistsException;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.exception.UnauthorizedException;
import cz.cvut.fel.nss.careerpages.model.JobResponse;
import cz.cvut.fel.nss.careerpages.service.JobResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Rest job response controller
 */
@RestController
@RequestMapping("/job_review")
public class JobResponseController {

    private final JobResponseService jobResponseService;

    /**
     * @param jobResponseService
     * constructor
     */
    @Autowired
    public JobResponseController(JobResponseService jobResponseService) {
        this.jobResponseService = jobResponseService;
    }

    /**
     * method returns 1 jobReview with identificator
     * @param identificator
     * @return response of JobResponse
     */
    @GetMapping(value = "/{identificator}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobResponse> get(Long identificator) {
        return ResponseEntity.status(HttpStatus.OK).body(jobResponseService.find(identificator));
    }

    /**
     * method returns all jobReviews
     * @return response of list of JobResponse
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JobResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(jobResponseService.findAll());
    }

    /**
     * method create new jobResponse
     * @param jobResponse
     * @param enrollmentId
     * @return void response
     * @throws UnauthorizedException
     * @throws AlreadyExistsException
     * @throws NotFoundException
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/{enrollmentId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody JobResponse jobResponse, @PathVariable Long enrollmentId ) throws UnauthorizedException, AlreadyExistsException, NotFoundException {
        jobResponseService.create(jobResponse, enrollmentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
