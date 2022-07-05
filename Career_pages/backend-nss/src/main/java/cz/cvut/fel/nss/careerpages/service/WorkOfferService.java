package cz.cvut.fel.nss.careerpages.service;

import com.hazelcast.core.HazelcastInstance;
import cz.cvut.fel.nss.careerpages.dao.*;
import cz.cvut.fel.nss.careerpages.dto.WorkOfferDto;
import cz.cvut.fel.nss.careerpages.dto.JobSessionDto;
import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.exception.BadDateException;
import cz.cvut.fel.nss.careerpages.exception.MissingVariableException;
import cz.cvut.fel.nss.careerpages.exception.NotAllowedException;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import cz.cvut.fel.nss.careerpages.security.model.UserDetails;
import cz.cvut.fel.nss.careerpages.service.security.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service for work offer
 */
@Service
public class WorkOfferService {
    private static final Logger LOG = LoggerFactory.getLogger(WorkOfferService.class);
    private final WorkOfferDao workOfferDao;
    private final JobSessionDao jobSessionDao;
    private final JobResponseDao jobResponseDao;
    private final TranslateService translateService;
    private final AccessService accessService;
    private final ApplicantDao applicantDao;
    private final ApplicationDao applicationDao;
    private final JobJournalDao jobJournalDao;
    private final RecruiterDao recruiterDao;
    private final AdminDao adminDao;
    private final HazelcastInstance cacheInstance;
    private Map<Long, WorkOfferDto> cache;

    /**
     * @param workOfferDao
     * @param jobSessionDao
     * @param jobResponseDao
     * @param translateService
     * @param accessService
     * @param applicantDao
     * @param applicationDao
     * @param jobJournalDao
     * @param recruiterDao
     * @param adminDao
     * @param hazelcastInstance
     * constructor
     */
    @Autowired
    public WorkOfferService(WorkOfferDao workOfferDao, JobSessionDao jobSessionDao, JobResponseDao jobResponseDao, TranslateService translateService, AccessService accessService, ApplicantDao applicantDao, ApplicationDao applicationDao, JobJournalDao jobJournalDao, RecruiterDao recruiterDao, AdminDao adminDao, HazelcastInstance hazelcastInstance) {
        this.workOfferDao = workOfferDao;
        this.jobSessionDao = jobSessionDao;
        this.jobResponseDao = jobResponseDao;
        this.translateService = translateService;
        this.accessService = accessService;
        this.applicantDao = applicantDao;
        this.applicationDao = applicationDao;
        this.jobJournalDao = jobJournalDao;
        this.recruiterDao = recruiterDao;
        this.adminDao = adminDao;
        this.cacheInstance = hazelcastInstance;
        this.cache = cacheInstance.getMap("offers");
    }

    /**
     * Get all Offers from database.
     * @return <List<Offer>
     */
    @Transactional
    public List<Offer> findAll() {
        return workOfferDao.findAll();
    }

    /**
     * Get all Offers.
     * @return  List<WorkOfferDto>
     */
    @Transactional
    public List<WorkOfferDto> findAllDto() {
        List<WorkOfferDto> workOfferDtos = new ArrayList<>();
        for (Offer offer: workOfferDao.findAll()) {
            if (SecurityUtils.getCurrentUser().getRole() == Role.MANAGER && offer.getAuthor().getId().equals(SecurityUtils.getCurrentUser().getId())) workOfferDtos.add(translateService.translateTrip(offer));
            else if (SecurityUtils.getCurrentUser().getRole() == Role.ADMIN) workOfferDtos.add(translateService.translateTrip(offer));
        }
        return workOfferDtos;
    }

    /**
     * Get all active offers.
     * @return Collection<WorkOfferDto>
     */
    @Transactional
    public Collection<WorkOfferDto> findAllDtoFiltered() {
        if(cache.isEmpty()) offersToCache();
        removeOldOffersFromCache();
        return cache.values();
    }

    /**
     * Putting offers to empty cache.
     */
    private void offersToCache(){
        LOG.info("Putting offers to empty cache.");
        List<WorkOfferDto> workOfferDtos = new ArrayList<>();
        for (Offer offer: workOfferDao.findAll()) workOfferDtos.add(translateService.translateTrip(offer));
        workOfferDtos.removeIf(offerDto -> !isTripActive(offerDto));
        for(WorkOfferDto offer: workOfferDtos) cache.put(offer.getId(),offer);
    }

    /**
     * Remove offers from cache.
     */
    private void removeOldOffersFromCache(){
        int sizeBefore = cache.size();
        cache.values().removeIf(offerDto -> !isTripActive(offerDto));
        int sizeAfter = cache.size();
        if (sizeAfter < sizeBefore) LOG.info("Removed "+ (sizeBefore - sizeAfter) + " offers from cache.");
    }

    /**
     * Get Offer by id.
     * @param id
     * @return WorkOfferDto
     */
    @Transactional
    public WorkOfferDto find(Long id) {
        Offer trip = workOfferDao.find(id);
        UserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        if(userDetails != null){
            System.out.println("THIS?");
                if (!userDetails.getUser().getRole().equals(Role.USER)) {
                    System.out.println("AM I doing this?");
                    return translateService.translateTrip(trip);
                }
        }
        List<JobSession> sessions = new ArrayList<>();
        for(JobSession tripSession : trip.getSessions()) {
            if(tripSession.isNotDeleted() && tripSession.getTo_date().isAfter(LocalDate.now())) {
                sessions.add(tripSession);
            }
        }
        trip.setSessions(sessions);

        return translateService.translateTrip(trip);
    }

    /**
     * Get offer by string id.
     * @param stringId
     * @return WorkOfferDto
     */
    @Transactional
    public WorkOfferDto findByString(String stringId) {
        Offer trip = workOfferDao.find(stringId);
        return translateService.translateTrip(trip);
    }

    /**
     * Get offer by string id and active.
     * @param stringId
     * @return
     */
    @Transactional
    public WorkOfferDto findByStringFiltered(String stringId) {
        Offer trip = workOfferDao.find(stringId);
        WorkOfferDto tripDto = translateService.translateTrip(trip);

        List<JobSessionDto> sessions = new ArrayList<>();
        for(JobSession tripSession : trip.getSessions()) {
            if(tripSession.isNotDeleted() &&
                    tripSession.getTo_date().isAfter(LocalDate.now()) &&
                    tripSession.getFrom_date().isAfter(LocalDate.now())) {
                sessions.add(translateService.translateSession(tripSession));
            }
        }
        tripDto.setSessions(sessions);
        return tripDto;
    }

    /**
     * Create new Offer.
     * @param offer
     * @throws BadDateException if to_date is before from_date
     * @throws MissingVariableException if offer has not sessions
     */
    @Transactional
    public void create(Offer offer) throws BadDateException, MissingVariableException {
        Objects.requireNonNull(offer);
        if (offer.getSessions().size()<=0) throw new MissingVariableException();
        offer.setAuthor(recruiterDao.find(SecurityUtils.getCurrentUser().getId()));
        workOfferDao.persist(offer);
        for (JobSession session: offer.getSessions()) {
            if (session.getTo_date().isBefore(session.getFrom_date())) {
                workOfferDao.remove(offer);
                throw new BadDateException();
            }
            session.setTrip(offer);
            jobSessionDao.persist(session);
        }
        workOfferDao.update(offer);
        addOfferToCache(translateService.translateTrip(offer));
    }

    /**
     * add offer to cache
     * @param workOfferDto
     */
    private void addOfferToCache(WorkOfferDto workOfferDto) {
        cache.put(workOfferDto.getId(), workOfferDto);
        LOG.info("Putting new offer (ID: " + workOfferDto.getId() + ") to cache.");
    }

    /**
     * Sign up current logged in user to trip.
     * @param tripSessionDto
     * @param current_user
     * @throws NotAllowedException
     */
    @Transactional
    public void signUpToTrip(JobSessionDto tripSessionDto, AbstractUser current_user) throws NotAllowedException {
        JobSession tripSession = jobSessionDao.find(tripSessionDto.getId());
        User user = applicantDao.find(current_user.getId());
        if(checkOwnedAchievements(user.getTravel_journal(), tripSession.getTrip())) {
            Application application = new Application();
            application.setDeposit_was_paid(false);
            application.setEnrollDate(LocalDateTime.now());
            application.setActual_xp_reward(0);
            application.setTrip(tripSession.getTrip());
            application.setState(ApplicationState.ACTIVE);
            application.setTripSession(tripSession);
            application.setJobJournal(user.getTravel_journal());
            applicationDao.persist(application);
            user.getTravel_journal().addEnrollment(application);
            jobJournalDao.update(user.getTravel_journal());
        }
        else {
            System.out.println("!USER DID NOT GET SIGNED UP TO TRIP!");
        }
    }

    /**
     * Get all offer that user can afford by level.
     * @param current_user
     * @return List<Offer>
     * @throws NotAllowedException
     */
    @Transactional
    public List<Offer> findAfford(AbstractUser current_user) throws NotAllowedException {
        if (current_user == null) throw new NotAllowedException();
        User user = applicantDao.find(current_user.getId());
        if (user == null) throw new NotAllowedException();
        int level = translateService.countLevel(translateService.translateJobJournal(user.getTravel_journal()).getXp_count());
        return  workOfferDao.find(level);
    }

    /**
     * Update offer.
     * @param stringId offer string id
     * @param newOffer new updated offer
     * @throws BadDateException if to_date is before from_date
     * @throws NotFoundException if offer doesnt exist
     * @throws MissingVariableException if offer has not sessions
     * @throws NotAllowedException if own manager is not logged in
     */
    @Transactional
    public void update(String stringId, Offer newOffer) throws BadDateException, NotFoundException, MissingVariableException, NotAllowedException {
        Offer offer = workOfferDao.find(stringId);
        if (offer == null) throw new NotFoundException();

        Recruiter recruiter = recruiterDao.find(SecurityUtils.getCurrentUser().getId());
        if (recruiter !=null && !offer.getAuthor().getId().equals(recruiter.getId())) throw new NotAllowedException("You are not allowed to update this offer.");
        newOffer.setId(offer.getId());
        newOffer.setAuthor(recruiter);
        if (newOffer.getSessions().size()<=0) throw new MissingVariableException();

        if (newOffer.getSessions().size() < offer.getSessions().size()){
            for ( int i = newOffer.getSessions().size() ; i < offer.getSessions().size(); i++) {
                jobSessionDao.remove(offer.getSessions().get(i));
            }
        }

        for (int i = 0; i < newOffer.getSessions().size() ; i++) {
            JobSession newSession = newOffer.getSessions().get(i);
            if (newSession.getTo_date().isBefore(newSession.getFrom_date())) throw new BadDateException();

            if (i <= offer.getSessions().size()-1 ){
                JobSession oldSession = offer.getSessions().get(i);

                newOffer.getSessions().get(i).setId(oldSession.getId());
                oldSession = newSession;
                oldSession.setTrip(offer);
                jobSessionDao.update(oldSession);
            } else {
                newSession.setTrip(offer);
                jobSessionDao.persist(newSession);
            }
        }
        offer=newOffer;
        workOfferDao.update(offer);
    }

    /**
     * Delete offer by string id.
     * @param stringId
     * @throws NotFoundException
     * @throws NotAllowedException
     */
    @Transactional
    public void delete(String stringId) throws NotFoundException, NotAllowedException {

        Offer offer = workOfferDao.find(stringId);
        Recruiter recruiter = null;

        if (SecurityUtils.getCurrentUser().getRole() == Role.MANAGER) recruiter = recruiterDao.find(SecurityUtils.getCurrentUser().getId());
        if (offer == null) throw new NotFoundException();
        if (recruiter !=null && offer.getAuthor() != recruiter) throw new NotAllowedException("You are not allowed delete this offer.");
        for (JobSession session :offer.getSessions()) {
            session.softDelete();
            jobSessionDao.update(session);
        }

        offer.softDelete();
        workOfferDao.update(offer);
    }

    /**
     * Filter offers by location, from_date, to_date, price
     * @param location
     * @param from_date
     * @param to_date
     * @param minPrice
     * @param search
     * @return
     */
    public List<WorkOfferDto> getAllTripsByFilter(String location, String from_date, String to_date,
                                                  Double minPrice, String[] search) {
        List<WorkOfferDto> tripDtos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate local_to_date = LocalDate.parse("2030-01-01", formatter);
        if(to_date != null){ local_to_date = LocalDate.parse(to_date, formatter); }
        LocalDate local_from_date = LocalDate.parse("1999-01-01", formatter);
        if(from_date != null){ local_from_date = LocalDate.parse(from_date, formatter); }
        for (Offer trip : workOfferDao.findByFilter(location,  local_from_date, local_to_date, minPrice, search)) {
            tripDtos.add(translateService.translateTrip(trip));
        }
        return tripDtos;
    }

    /**
     * Checking whether user own enough achievements to sign up to offer
     * @param usersJournal
     * @param trip
     * @return boolean
     */
    public boolean checkOwnedAchievements(JobJournal usersJournal, Offer trip) {
        List<AchievementCategorized> ownedCat = usersJournal.getEarnedAchievementsCategorized();
        List<AchievementCertificate> ownedCer = usersJournal.getCertificates();
        List<AchievementSpecial> ownedSpec = usersJournal.getEarnedAchievementsSpecial();

        for (AchievementCategorized ac : trip.getRequired_achievements_categorized()) {
            if(!ownedCat.contains(ac)) {
                System.out.println("UserJournal " + usersJournal + " lacks this achievement" + ac.getName());
                return false;
            }
        }
        for(AchievementSpecial as : trip.getRequired_achievements_special()) {
            if(!ownedSpec.contains(as)) {
                System.out.println("UserJournal " + usersJournal + " lacks this achievement" + as.getName());
                return false;
            }
        }
        for(AchievementCertificate ac : trip.getRequired_achievements_certificate()) {
            if(!ownedCer.contains(ac)) {
                System.out.println("UserJournal " + usersJournal + " lacks this achievement" + ac.getName());
                return false;
            }
        }

        return true;
    }

    /**
     * Whether offer is active or not.
     * @param offer
     * @return boolean
     */
    public boolean isTripActive(WorkOfferDto offer) {
        for(JobSessionDto tripSession : offer.getSessions()) {
            if(tripSession.getTo_date().isAfter(LocalDate.now()) && tripSession.getFrom_date().isAfter(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }
}
