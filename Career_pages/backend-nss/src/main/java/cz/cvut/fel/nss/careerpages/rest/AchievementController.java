package cz.cvut.fel.nss.careerpages.rest;

import cz.cvut.fel.nss.careerpages.dto.AchievementDto;
import cz.cvut.fel.nss.careerpages.model.User;
import cz.cvut.fel.nss.careerpages.security.SecurityConstants;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import cz.cvut.fel.nss.careerpages.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest achievement controller
 */
@RestController
@CrossOrigin(origins = SecurityConstants.FRONTEND)
@RequestMapping("/achievement")
public class AchievementController {

    private final AchievementService achievementService;

    /**
     * @param achievementService
     * constructor
     */
    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    /**
     * Method is looking for all achievements that current user gained.
     * @return response with list of AchievementDto
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AchievementDto>> getAllOfUser() {
        return ResponseEntity.status(HttpStatus.OK).body(achievementService.findAllOfUser((User) SecurityUtils.getCurrentUser()));
    }
}
