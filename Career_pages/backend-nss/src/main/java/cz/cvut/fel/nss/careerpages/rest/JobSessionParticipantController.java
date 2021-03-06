package cz.cvut.fel.nss.careerpages.rest;

import cz.cvut.fel.nss.careerpages.dto.RequestWrapperTripSessionsParticipants;
import cz.cvut.fel.nss.careerpages.security.SecurityConstants;
import cz.cvut.fel.nss.careerpages.service.JobSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Rest job session participant controller
 */
@CrossOrigin(origins = SecurityConstants.FRONTEND)
@RestController
@RequestMapping("/trip/participants")
public class JobSessionParticipantController {

    private JobSessionService jobSessionService;

    /**
     * @param jobSessionService
     * constructor
     */
    @Autowired
    public JobSessionParticipantController(JobSessionService jobSessionService) {
        this.jobSessionService = jobSessionService;
    }

    /**
     * method returns all participants of jobSession
     * @param trip_short_name
     * @return response of list of RequestWrapperTripSessionsParticipants
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{trip_short_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RequestWrapperTripSessionsParticipants>> findAllParticipants(@PathVariable String trip_short_name) {
        return ResponseEntity.status(HttpStatus.OK).body(jobSessionService.findAllParticipants(trip_short_name));
    }
}
