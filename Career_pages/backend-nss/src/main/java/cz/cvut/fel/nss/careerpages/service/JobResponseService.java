package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dao.*;
import cz.cvut.fel.nss.careerpages.model.Application;
import cz.cvut.fel.nss.careerpages.model.JobResponse;
import cz.cvut.fel.nss.careerpages.model.Offer;
import cz.cvut.fel.nss.careerpages.exception.AlreadyExistsException;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.exception.UnauthorizedException;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Service job response
 */
@Service
public class JobResponseService {
    private final JobResponseDao jobResponseDao;
    private final ApplicantDao applicantDao;
    private final WorkOfferDao workOfferDao;
    private final JobSessionDao jobSessionDao;
    private final ApplicationDao applicationDao;

    /**
     * @param jobResponseDao
     * @param applicantDao
     * @param workOfferDao
     * @param jobSessionDao
     * @param applicationDao
     * constructor
     */
    public JobResponseService(JobResponseDao jobResponseDao, ApplicantDao applicantDao, WorkOfferDao workOfferDao, JobSessionDao jobSessionDao, ApplicationDao applicationDao) {
        this.jobResponseDao = jobResponseDao;
        this.applicantDao = applicantDao;
        this.workOfferDao = workOfferDao;
        this.jobSessionDao = jobSessionDao;
        this.applicationDao = applicationDao;
    }

    /**
     * Get all JobReviews from database.
     * @return List<JobResponse>
     */
    @Transactional
    public List<JobResponse> findAll() {
        return jobResponseDao.findAll();
    }

    /**
     * Get JobResponse by id.
     * @param id
     * @return JobResponse
     */
    @Transactional
    public JobResponse find(Long id) {
        return jobResponseDao.find(id);
    }

    /**
     * Create new JobResponse to enrollment.
     * @param jobResponse new JobResponse
     * @param enrollmentId
     * @throws AlreadyExistsException if enrollment has alreadz job review
     * @throws UnauthorizedException if nobody is logged in
     * @throws NotFoundException if enrollment doesnt exist
     */
    @Transactional
    public void create(JobResponse jobResponse, Long enrollmentId) throws AlreadyExistsException, UnauthorizedException, NotFoundException {
        Objects.requireNonNull(jobResponse);
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new UnauthorizedException();
        Application application = applicationDao.find(enrollmentId);
        if (application == null) throw new NotFoundException();
        if (application.hasJobReview()) throw new AlreadyExistsException();

        jobResponse.setTrip(application.getTrip());
        jobResponse.setAuthor(applicantDao.find(SecurityUtils.getCurrentUser().getId()));
        jobResponse.setEnrollment(application);
        jobResponseDao.persist(jobResponse);

        Offer trip = application.getTrip();
        long noReviews = trip.getJobReviews().size();
        double currentRating = trip.getRating();
        trip.setRating((currentRating*(noReviews-1) + jobResponse.getRating())/noReviews);
        workOfferDao.update(trip);
    }

    /**
     * Update JobResponse.
     * @param jobResponse
     */
    @Transactional
    public void update(JobResponse jobResponse) {
        Objects.requireNonNull(jobResponse);
        JobResponse old = jobResponseDao.find(jobResponse.getId());
        double oldRating = old.getRating();
        double newRating = jobResponse.getRating();
        Offer trip = old.getTrip();
        double currentRating = trip.getRating();
        long noReviews = trip.getJobReviews().size();

        trip.setRating((currentRating*(noReviews) + newRating - oldRating)/noReviews);

        workOfferDao.update(trip);
        jobResponseDao.update(jobResponse);
    }
}
