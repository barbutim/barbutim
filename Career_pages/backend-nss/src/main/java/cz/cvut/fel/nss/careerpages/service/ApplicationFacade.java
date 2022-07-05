package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dto.ApplicationDto;
import cz.cvut.fel.nss.careerpages.dto.RequestWrapperEnrollment;
import cz.cvut.fel.nss.careerpages.dto.RequestWrapperEnrollmentGet;
import cz.cvut.fel.nss.careerpages.exception.NotAllowedException;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Service for application facade
 */
@Component
public class ApplicationFacade {
    private final ApplicationService applicationService;
    private final UserReviewService userReviewService;

    /**
     * @param applicationService
     * @param userReviewService
     * constructor
     */
    @Autowired
    public ApplicationFacade(ApplicationService applicationService, UserReviewService userReviewService) {
        this.applicationService = applicationService;
        this.userReviewService = userReviewService;
    }

    /**
     * @param requestWrapperEnrollment
     * @throws Exception
     * close application
     */
    public void close(RequestWrapperEnrollment requestWrapperEnrollment) throws Exception {
        applicationService.close(requestWrapperEnrollment.getEnrollmentDto());
        userReviewService.create(requestWrapperEnrollment.getEnrollmentDto().getId(), SecurityUtils.getCurrentUser(),requestWrapperEnrollment.getTripSessionId(),requestWrapperEnrollment.getUserReview());
    }

    /**
     * @param id
     * @throws Exception
     * close full application
     */
    public void closeFull(Long id) throws Exception {
        applicationService.closeOk(id);
        userReviewService.create(id,SecurityUtils.getCurrentUser());
    }

    /**
     * @return get all
     */
    public List<RequestWrapperEnrollmentGet> getAllWithUserToClose(){
        return applicationService.findAllActiveEndedWithUser();
    }

    /**
     * @param id
     * cancel application
     */
    public void cancel(Long id){
        applicationService.cancel(id);
    }

    /**
     * @param id
     * @return
     * @throws NotAllowedException
     * get user to close
     */
    public RequestWrapperEnrollmentGet getWithUserToClose(Long id) throws NotAllowedException {
        return applicationService.findActiveEndedWithUser(id);
    }

    /**
     * @param id
     * @return get all finished
     * @throws NotFoundException
     * @throws NotAllowedException
     */
    public List<ApplicationDto> getAllFinished(Long id) throws NotFoundException, NotAllowedException {
        return applicationService.findAllOfUserFinished(id);
    }

    /**
     * @return get all finished
     * @throws NotAllowedException
     */
    public List<ApplicationDto>  getAllFinished() throws NotAllowedException {
        return applicationService.findAllOfUserFinished(SecurityUtils.getCurrentUser());
    }

    /**
     * @param id
     * @return get all active and cancelled
     * @throws NotFoundException
     * @throws NotAllowedException
     */
    public List<ApplicationDto> getAllActiveAndCancel(Long id) throws NotFoundException, NotAllowedException {
        return applicationService.findAllOfUserActive(id);
    }

    /**
     * @return get all active and cancelled
     * @throws NotAllowedException
     */
    public List<ApplicationDto> getAllActiveAndCancel() throws NotAllowedException {
        return applicationService.findAllOfUserActive(SecurityUtils.getCurrentUser());
    }

    /**
     * @param id
     * @return get
     */
    public ApplicationDto get(Long id){
        return applicationService.findDto(id);
    }
}
