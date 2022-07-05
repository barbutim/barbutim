package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dto.*;
import cz.cvut.fel.nss.careerpages.model.Application;
import cz.cvut.fel.nss.careerpages.model.JobSession;
import cz.cvut.fel.nss.careerpages.dao.WorkOfferDao;
import cz.cvut.fel.nss.careerpages.dao.JobSessionDao;
import cz.cvut.fel.nss.careerpages.exception.MissingVariableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for job session
 */
@Service
public class JobSessionService {

    private JobSessionDao jobSessionDao;
    private WorkOfferDao workOfferDao;
    private final TranslateService translateService;

    /**
     * @param jobSessionDao
     * @param workOfferDao
     * @param translateService
     * constructor
     */
    @Autowired
    public JobSessionService(JobSessionDao jobSessionDao, WorkOfferDao workOfferDao, TranslateService translateService) {
        this.jobSessionDao = jobSessionDao;
        this.workOfferDao = workOfferDao;
        this.translateService = translateService;
    }

    /**
     * Get all JobSessionsDto in trip.
     * @param trip_short_name where I looking for job sessions
     * @return  List<JobSessionDto>
     */
    @Transactional
    public List<JobSessionDto> findAllInTrip(String trip_short_name) {
        List<JobSession> tripSessions = jobSessionDao.find(trip_short_name);
        List<JobSessionDto> tripSessionDtos = new ArrayList<>();

        for (JobSession ts: tripSessions) {
            tripSessionDtos.add(translateService.translateSession(ts));
        }
        return tripSessionDtos;
    }

    /**
     * Create new JobSession to offer.
     * @param jobId
     * @param jobSession
     * @throws Exception if from_date is after after_date of session
     */
    @Transactional
    public void create(String jobId, JobSession jobSession) throws Exception {
        if (jobSession.getTo_date().isBefore(jobSession.getFrom_date())) throw new Exception();
        jobSession.setTrip(workOfferDao.find(jobId));
        jobSessionDao.persist(jobSession);
    }

    /**
     * Update JobSession.
     * @param oldSession
     * @param newSession
     * @return JobSession
     * @throws Exception
     */
    @Transactional
    public JobSession update(JobSession oldSession, JobSession newSession) throws Exception {
        if (oldSession == null || newSession==null) throw new MissingVariableException();

        oldSession.setFrom_date(newSession.getFrom_date());
        oldSession.setCapacity(newSession.getCapacity());
        oldSession.setTo_date(newSession.getTo_date());
        oldSession.setTrip(newSession.getTrip());
        jobSessionDao.update(oldSession);
        return oldSession;
    }

    /**
     * Get all participants of job session
     * @param trip_short_name
     * @return List<RequestWrapperTripSessionsParticipants>
     */
    @Transactional
    public List<RequestWrapperTripSessionsParticipants> findAllParticipants(String trip_short_name){
        List<JobSession> tripSessions = jobSessionDao.find(trip_short_name);
        List<RequestWrapperTripSessionsParticipants> response = new ArrayList<RequestWrapperTripSessionsParticipants>();
        for (JobSession session:tripSessions) {
            List<RequestWrapperEnrollmentGet> enrollments = new ArrayList<RequestWrapperEnrollmentGet>();
            for (Application application :session.getEnrollments()) {
                ApplicantDto applicantDto = translateService.translateUser(application.getJobJournal().getUser());
                ApplicationDto applicationDto = translateService.translateEnrollment(application);
                enrollments.add(new RequestWrapperEnrollmentGet(applicationDto, applicantDto));
            }

            response.add(new RequestWrapperTripSessionsParticipants(translateService.translateSession(session),enrollments));
        }
        return response;
    }
}
