package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dao.AchievementSpecialDao;
import cz.cvut.fel.nss.careerpages.dao.ApplicationDao;
import cz.cvut.fel.nss.careerpages.dao.JobJournalDao;
import cz.cvut.fel.nss.careerpages.dao.ApplicantDao;
import cz.cvut.fel.nss.careerpages.dto.AchievementSpecialDto;
import cz.cvut.fel.nss.careerpages.dto.ApplicationDto;
import cz.cvut.fel.nss.careerpages.dto.RequestWrapperEnrollmentGet;
import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.exception.NotAllowedException;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import cz.cvut.fel.nss.careerpages.service.security.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service for application
 */
@Service
public class ApplicationService {
    private final ApplicationDao applicationDao;
    private final TranslateService translateService;
    private final AccessService accessService;
    private final ApplicantDao applicantDao;
    private final AchievementSpecialDao achievementSpecialDao;
    private final JobJournalService jobJournalService;

    /**
     * @param applicationDao
     * @param translateService
     * @param accessService
     * @param applicantDao
     * @param achievementSpecialDao
     * @param jobJournalService
     * @param jobJournalDao
     * constructor
     */
    @Autowired
    public ApplicationService(ApplicationDao applicationDao, TranslateService translateService, AccessService accessService, ApplicantDao applicantDao, AchievementSpecialDao achievementSpecialDao, JobJournalService jobJournalService, JobJournalDao jobJournalDao) {
        this.applicationDao = applicationDao;
        this.translateService =  translateService;
        this.accessService = accessService;
        this.applicantDao = applicantDao;
        this.achievementSpecialDao = achievementSpecialDao;
        this.jobJournalService = jobJournalService;
    }

    /**
     * Get all Enrollments from database.
     * @return List<Application>
     */
    private List<Application> findAll(){
        return applicationDao.findAll();
    }

    /**
     * Get active and expired enrollment and its owner by enrollment id.
     * @param enrollId
     * @return RequestWrapperEnrollmentGet is wrapper for returning enrollment and its owner.
     * @throws NotAllowedException if logged in user is not creator of trip.
     */
    @Transactional
    public RequestWrapperEnrollmentGet findActiveEndedWithUser(Long enrollId) throws NotAllowedException {
        if (!find(enrollId).getTrip().getAuthor().getId().equals(SecurityUtils.getCurrentUser().getId())) throw new NotAllowedException("Not for you");
        RequestWrapperEnrollmentGet wrapperEnrollmentGet = new RequestWrapperEnrollmentGet();

        if (findDto(enrollId).getState() != ApplicationState.ACTIVE || findDto(enrollId).getTripSession().getTo_date().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) throw new NotAllowedException();
        wrapperEnrollmentGet.setEnrollmentDto(translateService.translateEnrollment(find(enrollId)));
        wrapperEnrollmentGet.setOwner(translateService.translateUser(applicantDao.find(find(enrollId).getJobJournal().getUser().getId())));
        return wrapperEnrollmentGet;
    }

    /**
     * Get active and expired enrollments and its owner.
     * @return List<RequestWrapperEnrollmentGet> are wrappers for returning enrollment and its owner.
     */
    @Transactional
    public List<RequestWrapperEnrollmentGet> findAllActiveEndedWithUser(){
        List<RequestWrapperEnrollmentGet> requestWrappers = new ArrayList<>();

        for (Application e: findAllActiveEnded()) {
            RequestWrapperEnrollmentGet wrapperEnrollmentGet = new RequestWrapperEnrollmentGet();
            wrapperEnrollmentGet.setEnrollmentDto(translateService.translateEnrollment(e));
            wrapperEnrollmentGet.setOwner(translateService.translateUser(e.getJobJournal().getUser()));
            requestWrappers.add(wrapperEnrollmentGet);
        }
        return requestWrappers;
    }

    /**
     * Get all Application to close. When they are active and expired.
     * @return List<Application>
     */
    @Transactional
    public List<Application> findAllActiveEnded(){
        List<Application> applications = findAll();
        List<Application> newApplications = new ArrayList<>();
        for (Application e: applications) {
            if (e.getState().equals(ApplicationState.ACTIVE) && e.getTripSession().getTo_date().isBefore(ChronoLocalDate.from(LocalDateTime.now()))){
                if ((SecurityUtils.getCurrentUser().getRole() == Role.MANAGER && SecurityUtils.getCurrentUser().getId().equals(e.getTrip().getAuthor().getId())) || SecurityUtils.getCurrentUser().getRole() == Role.ADMIN){
                    newApplications.add(e);
                }
            }
        }
        Collections.sort(newApplications, new Comparator<Application>() {
            @Override
            public int compare(Application e1, Application e2)
            {
                return  e1.getTripSession().getTo_date().compareTo(e2.getTripSession().getTo_date());
            }
        });
        return newApplications;
    }

    /**
     * Get Application by id.
     * @param id
     * @return Application
     */
    private Application find(Long id){
        return applicationDao.find(id);
    }

    /**
     * Get ApplicationDto by id.
     * @param id
     * @return ApplicationDto
     */
    @Transactional
    public ApplicationDto findDto(Long id){
       return translateService.translateEnrollment(applicationDao.find(id));
    }

    /**
     * Get all EnrollmentsDto of current logged in user.
     * @param current_user
     * @return List<ApplicationDto>
     * @throws NotAllowedException if nobody is logged in.
     */
    @Transactional
    public List<ApplicationDto> findAllOfUser(AbstractUser current_user) throws NotAllowedException {
        User user = applicantDao.find(current_user.getId());
        if (user == null) throw new NotAllowedException();
        List<Application> applications = user.getTravel_journal().getEnrollments();
        List<ApplicationDto> applicationDtos = new ArrayList<ApplicationDto>();

        if (applications != null && applications.size()>0){
            for (Application e : applications) {
                applicationDtos.add(translateService.translateEnrollment(e));
            }
        }
        return applicationDtos;
    }

    /**
     * Get all EnrollmentsDto of user and enrollments are finished.
     * @param current_user
     * @return  List<ApplicationDto>
     * @throws NotAllowedException  if nobody is logged in.
     */
    @Transactional
    public List<ApplicationDto> findAllOfUserFinished(AbstractUser current_user) throws NotAllowedException {
        List<ApplicationDto> userEnrollments = findAllOfUser(current_user);
        List<ApplicationDto> finished = new ArrayList<ApplicationDto>();

        for (ApplicationDto applicationDto : userEnrollments) {
            if (applicationDto.getState()== ApplicationState.FINISHED) finished.add(applicationDto);
        }
        return finished;
    }

    /**
     * Get all EnrollmentsDto of user and enrollments are active or canceled.
     * @param current_user
     * @return List<ApplicationDto>
     * @throws NotAllowedException if nobody is logged in.
     */
    @Transactional
    public List<ApplicationDto> findAllOfUserActive(AbstractUser current_user) throws NotAllowedException {
        List<ApplicationDto> userEnrollments = findAllOfUser(current_user);
        List<ApplicationDto> active_canceled = new ArrayList<ApplicationDto>();
        for (ApplicationDto applicationDto : userEnrollments) {
            if (applicationDto.getState()!= ApplicationState.FINISHED) active_canceled.add(applicationDto);
        }
        return active_canceled;
    }

    /**
     *Get all EnrollmentsDto of user by id which are finished .
     * @param id
     * @return List<ApplicationDto>
     * @throws NotFoundException
     * @throws NotAllowedException
     */
    @Transactional
    public List<ApplicationDto> findAllOfUserFinished(Long id) throws NotFoundException, NotAllowedException {
        AbstractUser user = applicantDao.find(id);
        if (user == null) throw new NotFoundException();
        return findAllOfUserFinished(user);
    }

    /**
     * Get all EnrollmentsDto of user by id which are active and canceled.
     * @param id
     * @return List<ApplicationDto>
     * @throws NotFoundException
     * @throws NotAllowedException
     */
    @Transactional
    public List<ApplicationDto> findAllOfUserActive(Long id) throws NotFoundException, NotAllowedException {
        AbstractUser user = applicantDao.find(id);
        if (user == null) throw new NotFoundException();
        return findAllOfUserActive(user);
    }

    /**
     * Closing enrollment when something went wrong and user gained less xp or achievements.
     * @param applicationDto new enrollment with new xp and achievement parameters
     * @throws NotAllowedException
     */
    @Transactional
    public void close(ApplicationDto applicationDto) throws NotAllowedException {
        Application application = find(applicationDto.getId());
        if (!application.getTrip().getAuthor().getId().equals(SecurityUtils.getCurrentUser().getId())) throw new NotAllowedException("Not for you");

        application.setState(ApplicationState.FINISHED);
        application.setDeposit_was_paid(true);
        application.setActual_xp_reward(applicationDto.getActual_xp_reward());

        List<AchievementSpecial> achievementSpecials = new ArrayList<>();
        for (AchievementSpecialDto achievementSpecialDto : applicationDto.getRecieved_achievements_special()) {
            achievementSpecials.add(achievementSpecialDao.find(achievementSpecialDto.getId()));
        }

        application.setRecieved_achievements_special(achievementSpecials);
        applicationDao.update(application);

        jobJournalService.addXP(application.getJobJournal().getId(), application.getActual_xp_reward());
        jobJournalService.addTrip(application.getJobJournal().getId(), application.getTrip().getId());
        for(AchievementSpecial as : application.getRecieved_achievements()) {
            jobJournalService.addOwnedSpecialAchievement(application.getJobJournal(), as);
        }
    }

    /**
     * Closing enrollment by id when everything is ok and user gained all xp and achievements.
     * @param id
     * @throws NotAllowedException
     * @throws NotFoundException
     */
    @Transactional
    public void closeOk(Long id) throws NotAllowedException, NotFoundException {
        Application application = find(id);
        if (application == null) throw new NotFoundException();
        if (!application.getTrip().getAuthor().getId().equals(SecurityUtils.getCurrentUser().getId())) throw new NotAllowedException("Not for you");

        List<AchievementSpecial> achievementSpecials = application.getTrip().getGain_achievements_special();
        application.setState(ApplicationState.FINISHED);
        application.setDeposit_was_paid(true);
        application.setActual_xp_reward(application.getTrip().getPossible_xp_reward());
        application.setRecieved_achievements_special(new ArrayList());
        application.getRecieved_achievements().addAll(achievementSpecials);
        applicationDao.update(application);
        jobJournalService.addXP(application.getJobJournal().getId(), application.getActual_xp_reward());
        jobJournalService.addTrip(application.getJobJournal().getId(), application.getTrip().getId());
        for(AchievementSpecial as : application.getRecieved_achievements()) {
            jobJournalService.addOwnedSpecialAchievement(application.getJobJournal(), as);
        }
    }

    /**
     * Cancel enrollment by id.
     * @param id
     */
    @Transactional
    public void cancel(Long id) {
        Application application = find(id);
        application.setState(ApplicationState.CANCELED);
        applicationDao.update(application);
    }
}
