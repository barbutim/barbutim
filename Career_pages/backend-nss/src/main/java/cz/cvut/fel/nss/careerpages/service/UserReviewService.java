package cz.cvut.fel.nss.careerpages.service;


import cz.cvut.fel.nss.careerpages.dao.*;
import cz.cvut.fel.nss.careerpages.dto.UserReviewDto;
import cz.cvut.fel.nss.careerpages.exception.NotAllowedException;
import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.exception.UnauthorizedException;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service for user review
 */
@Service
public class UserReviewService {
    private final TranslateService translateService;
    private final UserReviewDao userReviewDao;
    private final ApplicantDao applicantDao;
    private final JobSessionDao jobSessionDao;
    private final ApplicationDao applicationDao;
    private final RecruiterDao recruiterDao;

    /**
     * @param translateService
     * @param userReviewDao
     * @param applicantDao
     * @param jobSessionDao
     * @param applicationDao
     * @param recruiterDao
     * constructor
     */
    @Autowired
    public UserReviewService(TranslateService translateService, UserReviewDao userReviewDao, ApplicantDao applicantDao, JobSessionDao jobSessionDao, ApplicationDao applicationDao, RecruiterDao recruiterDao) {
        this.translateService = translateService;
        this.userReviewDao = userReviewDao;
        this.applicantDao = applicantDao;
        this.jobSessionDao = jobSessionDao;
        this.applicationDao = applicationDao;
        this.recruiterDao = recruiterDao;
    }

    /**
     * Get UserReview by id.
     * @param id
     * @return UserReviewDto
     */
    @Transactional
    public UserReviewDto find(Long id) {
        Objects.requireNonNull(id);
        return translateService.translateUserReview(userReviewDao.find(id));
    }

    /**
     * Get all UserReviews.
     * @return List<UserReviewDto>
     */
    @Transactional
    public List<UserReviewDto> findAll() {
        List<UserReviewDto> userReviewDtos = new ArrayList<>();

        for (UserReview userReview : userReviewDao.findAll()) {
            userReviewDtos.add(translateService.translateUserReview(userReview));
        }
        return userReviewDtos;
    }

    /**
     * Get all UserReviews which own user by his id.
     * @param userId
     * @return  List<UserReviewDto>
     * @throws NotFoundException if user doesnt exist.
     */
    @Transactional
    public List<UserReviewDto> findAllOfUser(Long userId) throws NotFoundException {
        User user = applicantDao.find(userId);
        if (user == null) throw new NotFoundException();
        if (user.getUserReviews() == null) throw new NotFoundException();

        List<UserReviewDto> userReviewDtos = new ArrayList<>();

        for (UserReview userReview : user.getUserReviews()) {
            userReviewDtos.add(translateService.translateUserReview(userReview));
        }
        return userReviewDtos;
    }

    /**
     * Get all UserReviews which own current logged in user.
     * @return List<UserReviewDto>
     * @throws UnauthorizedException
     * @throws NotFoundException
     */
    @Transactional
    public List<UserReviewDto> findAllOfUser() throws UnauthorizedException, NotFoundException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new UnauthorizedException();
        User user = applicantDao.find(SecurityUtils.getCurrentUser().getId());
        if (user == null) throw new UnauthorizedException();
        List<UserReviewDto> userReviewDtos = new ArrayList<>();
        if (user.getUserReviews() == null) throw new NotFoundException();
        for (UserReview userReview : user.getUserReviews()) {
            userReviewDtos.add(translateService.translateUserReview(userReview));
        }
        return userReviewDtos;
    }

    /**
     * Create new UserReview from currentUser manager.
     * @param enrollmentId enrollment of user whom review I create.
     * @param currentUser
     * @param tripSessionId offerSession id where was user.
     * @param userReview
     * @throws Exception
     */
    @Transactional
    public void create(long enrollmentId, AbstractUser currentUser, Long tripSessionId, UserReview userReview) throws Exception {
        Application application = applicationDao.find(enrollmentId);
        if (application == null) throw new NotFoundException();
        if (!application.getTrip().getAuthor().getId().equals(SecurityUtils.getCurrentUser().getId())) throw new NotAllowedException("Not for you");

        User user = application.getJobJournal().getUser();
        Recruiter current_user = recruiterDao.find(currentUser.getId());
        JobSession tripSession = jobSessionDao.find(tripSessionId);
        if (user == null || tripSession==null) throw new NotFoundException();

        userReview.setUser(user);
        userReview.setAuthor(current_user);
        userReview.setTripSession(tripSession);
        userReviewDao.persist(userReview);
    }

    /**
     * Create new UserReview from currentUser manager of full rating.
     * @param enrollmentId enrollment of user whom review I create.
     * @param currentUser
     * @throws Exception
     */
    @Transactional
    public void create(long enrollmentId, AbstractUser currentUser) throws Exception {
        Application application = applicationDao.find(enrollmentId);
        if (application == null) throw new NotFoundException();
        if (!application.getTrip().getAuthor().getId().equals(SecurityUtils.getCurrentUser().getId())) throw new NotAllowedException("Not for you");

        User user = application.getJobJournal().getUser();
        Recruiter current_user = recruiterDao.find(currentUser.getId());
        JobSession tripSession = jobSessionDao.find(application.getTripSession().getId());
        UserReview userReview = new UserReview();
        if (user == null || tripSession == null) throw new NotFoundException();

        userReview.setRating(5);
        userReview.setNote(null);
        userReview.setUser(user);
        userReview.setAuthor(current_user);
        userReview.setTripSession(tripSession);
        userReviewDao.persist(userReview);
    }
}
