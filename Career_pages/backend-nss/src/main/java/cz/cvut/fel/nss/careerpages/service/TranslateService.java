package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dao.JobJournalDao;
import cz.cvut.fel.nss.careerpages.dto.*;
import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.dao.CategoryDao;
import cz.cvut.fel.nss.careerpages.dao.WorkOfferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Service for translate
 */
@Service
public class TranslateService {
    private final JobJournalDao jobJournalDao;
    private final WorkOfferDao workOfferDao;
    private final CategoryDao categoryDao;

    /**
     * @param jobJournalDao
     * @param workOfferDao
     * @param categoryDao
     * constructor
     */
    @Autowired
    public TranslateService(JobJournalDao jobJournalDao, WorkOfferDao workOfferDao, CategoryDao categoryDao) {
        this.jobJournalDao = jobJournalDao;
        this.workOfferDao = workOfferDao;
        this.categoryDao = categoryDao;
    }

    /**
     * Translate object User to UserDTo
     * @param user
     * @return UserDTO
     */
    @Transactional
    public ApplicantDto translateUser(User user) {
        System.out.println(user.toString());
        Objects.requireNonNull(user);
        List<JobResponseDto> jobResponseDtos = new ArrayList<>();
        List<JobResponse> jobResponses = user.getJobReviews();
        List<UserReviewDto> userReviewDtos = new ArrayList<>();
        List<UserReview> userReviews =  user.getUserReviews();

        if (jobResponses.size() > 0){
            jobResponses.forEach(review-> jobResponseDtos.add(translateJobReview(review)));
        }
        if (userReviews.size() > 0){
            userReviews.forEach(review-> userReviewDtos.add(translateUserReview(review)));
        }
        if (user.getTravel_journal() != null) {
            JobJournalDto jobJournalDto = translateJobJournal(user.getTravel_journal());
            return new ApplicantDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),
                    translateAddress(user.getAddress()), user.getPhone_number(), jobJournalDto, jobResponseDtos,userReviewDtos);
        }

       return new ApplicantDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),
                translateAddress(user.getAddress()), user.getPhone_number(), null, jobResponseDtos, userReviewDtos);
    }

    /**
     * Translate object Rectuiter to RecruiterDto
     * @param recruiter
     * @return RecruiterDto
     */
    @Transactional
    public RecruiterDto translateManager(Recruiter recruiter) {
        System.out.println(recruiter.toString());
        Objects.requireNonNull(recruiter);
        List<UserReviewDto> userReviewDtos = new ArrayList<>();
        List<WorkOfferDto> offersDto = new ArrayList<>();
        List<UserReview> userReviews =  recruiter.getUserReviewsAuthor();
        List<Offer> offers =  recruiter.getOffers();

        if (userReviews.size() > 0){
            userReviews.forEach(review-> userReviewDtos.add(translateUserReview(review)));
        }
        if (offers.size() > 0){
            offers.forEach(offer-> offersDto.add(translateTrip(offer)));
        }
        return new RecruiterDto(recruiter.getId(), recruiter.getFirstName(), recruiter.getLastName(), recruiter.getEmail(),
                translateAddress(recruiter.getAddress()), recruiter.getPhone_number(), recruiter.getCompany(), userReviewDtos, offersDto );
    }

    /**
     * Translate object Admin to AbstractUserDto
     * @param admin
     * @return AbstractUserDto
     */
    @Transactional
    public AbstractUserDto translateAdmin(Admin admin){
        Objects.requireNonNull(admin);
        return new AbstractUserDto(admin.getId(),admin.getFirstName(), admin.getLastName(), admin.getEmail(), translateAddress(admin.getAddress()), Role.ADMIN);
    }

    /**
     * Translate object Address to AddressDto
     * @param address
     * @return AddressDto
     */
    @Transactional
    public AddressDto translateAddress(Address address) {
        Objects.requireNonNull(address);
        return new AddressDto(address.getId(),address.getCity(),address.getStreet(),address.getHouseNumber(),address.getZipCode(),
                address.getCountry(),address.getUser().getId());
    }

    /**
     * Translate object Offer to WorkOfferDto
     * @param offer
     * @return WorkOfferDto
     */
    @Transactional
    public WorkOfferDto translateTrip(Offer offer) {
        Objects.requireNonNull(offer);
        List<JobSessionDto> sessions = new ArrayList<>();
        List<AchievementCertificateDto> required_certificates = new ArrayList<>();
        List<AchievementCategorizedDto> required_achievements_categorized = new ArrayList<>();
        List<AchievementSpecialDto> required_achievements_special = new ArrayList<>();
        List<AchievementSpecialDto> gain_achievements = new ArrayList<>();
        List<JobResponseDto> jobReviews = new ArrayList<>();
        Offer trip1 = workOfferDao.find(offer.getId());

        trip1.getRequired_achievements_certificate().forEach(achievementCertificate -> required_certificates.add(translateAchievementCertificate(achievementCertificate)));
        trip1.getRequired_achievements_categorized().forEach(achievementCategorized -> required_achievements_categorized.add(translateAchievementCategorized(achievementCategorized)));
        trip1.getRequired_achievements_special().forEach(achievementSpecial -> required_achievements_special.add(translateAchievementSpecial(achievementSpecial)));
        trip1.getGain_achievements_special().forEach(achievementSpecial -> gain_achievements.add(translateAchievementSpecial(achievementSpecial)));
        trip1.getJobReviews().forEach(review -> jobReviews.add(translateJobReview(review)));
        trip1.getSessions().forEach(session-> sessions.add(translateSession(session)));

        return new WorkOfferDto(offer.getId(),offer.getName(),offer.getShort_name(),offer.getPossible_xp_reward(),
                offer.getDescription(),offer.getRating(),offer.getSalary(),offer.getLocation(), offer.getRequired_level(),
                translateCategory(offer.getCategory()),offer.getAuthor().getId(), required_certificates, required_achievements_categorized, required_achievements_special, gain_achievements, sessions, jobReviews);
    }

    /**
     * Translate object JobSession to JobSessionDto
     * @param jobSession
     * @return JobSessionDto
     */
    @Transactional
    public JobSessionDto translateSession(JobSession jobSession) {
        Objects.requireNonNull(jobSession);
        return new JobSessionDto(jobSession.getId(),jobSession.getFrom_date(),jobSession.getTo_date(),jobSession.getCapacity(),jobSession.getTrip().getId());
    }

    /**
     * Translate object AchievementCertificate to AchievementCertificateDto
     * @param achievementCertificate
     * @return AchievementCertificateDto
     */
    @Transactional
    public AchievementCertificateDto translateAchievementCertificate(AchievementCertificate achievementCertificate){
        Objects.requireNonNull(achievementCertificate);
        List<Long> trips = new ArrayList<>();
        List<Long> owned_travel_journals = new ArrayList<>();

        achievementCertificate.getTrips().forEach(trip -> trips.add(trip.getId()));
        achievementCertificate.getOwned_travel_journals().forEach(jobJournal -> owned_travel_journals.add(jobJournal.getId()));

        return new AchievementCertificateDto(achievementCertificate.getId(),achievementCertificate.getName(),achievementCertificate.getDescription(),achievementCertificate.getIcon(),
                trips,owned_travel_journals);
    }

    /**
     * Translate object AchievementSpecial to AchievementSpecialDto
     * @param achievementSpecial
     * @return AchievementSpecialDto
     */
    @Transactional
    public AchievementSpecialDto translateAchievementSpecial(AchievementSpecial achievementSpecial){
        Objects.requireNonNull(achievementSpecial);
        List<Long> trips = new ArrayList<>();
        List<Long> owned_travel_journals = new ArrayList<>();

        achievementSpecial.getTrips().forEach(trip -> trips.add(trip.getId()));
        achievementSpecial.getOwned_travel_journals().forEach(jobJournal -> owned_travel_journals.add(jobJournal.getId()));

        return new AchievementSpecialDto(achievementSpecial.getId(),achievementSpecial.getName(),achievementSpecial.getDescription(),achievementSpecial.getIcon(),
                trips,owned_travel_journals);
    }

    /**
     * Translate object AchievementCategorized to AchievementCategorizedDto
     * @param achievementCategorized
     * @return AchievementCategorizedDto
     */
    @Transactional
    public AchievementCategorizedDto translateAchievementCategorized(AchievementCategorized achievementCategorized){
        Objects.requireNonNull(achievementCategorized);
        List<Long> trips = new ArrayList<>();
        List<Long> owned_travel_journals = new ArrayList<>();

        achievementCategorized.getTrips().forEach(trip -> trips.add(trip.getId()));
        achievementCategorized.getOwned_travel_journals().forEach(jobJournal -> owned_travel_journals.add(jobJournal.getId()));

        return new AchievementCategorizedDto(achievementCategorized.getId(),achievementCategorized.getName(),achievementCategorized.getDescription(),achievementCategorized.getIcon(),
                trips,owned_travel_journals, achievementCategorized.getLimit(), achievementCategorized.getCategory().getId());
    }

    /**
     * Translate object JobJournal to JobJournalDto
     * @param jobJournal
     * @return JobJournalDto
     */
    @Transactional
    public JobJournalDto translateJobJournal(JobJournal jobJournal){
        Objects.requireNonNull(jobJournal);
        List<AchievementCertificateDto> certificateDtos = new ArrayList<>();
        List<AchievementCategorizedDto> categorizedDtos = new ArrayList<>();
        List<AchievementSpecialDto> specialDtos = new ArrayList<>();
        HashMap<CategoryDto, Integer> trip_counter= new HashMap<CategoryDto, Integer>();
        JobJournal jobJournal1 = jobJournalDao.find(jobJournal.getId());

        for (Long categoryID : jobJournal1.getTrip_counter().keySet()) {
            Category category = categoryDao.find(categoryID);
            CategoryDto categoryDto= translateCategory(category);
            trip_counter.put(categoryDto,jobJournal.getTrip_counter().get(category));
        }

        jobJournal1.getCertificates().forEach(certificate -> certificateDtos.add(translateAchievementCertificate(certificate)));
        jobJournal1.getEarnedAchievementsCategorized().forEach(categorized -> categorizedDtos.add(translateAchievementCategorized(categorized)));
        jobJournal1.getEarnedAchievementsSpecial().forEach(special -> specialDtos.add(translateAchievementSpecial(special)));

        return new JobJournalDto(jobJournal.getId(), jobJournal.getXp_count(), trip_counter,jobJournal.getUser().getId(), certificateDtos, categorizedDtos, specialDtos, countLevel(jobJournal.getXp_count()));
    }

    /**
     * Translate object Category to CategoryDto
     * @param category
     * @return CategoryDto
     */
    @Transactional
    public CategoryDto translateCategory(Category category){
        return category == null ? null : new CategoryDto(category.getId(),category.getName());
    }

    /**
     * Translate object Application to ApplicationDto
     * @param application
     * @return ApplicationDto
     */
    @Transactional
    public ApplicationDto translateEnrollment(Application application){
        Objects.requireNonNull(application);
        List<AchievementSpecialDto> recieved_achievements_special = new ArrayList<>();
        application.getRecieved_achievements().forEach(achievement_special -> recieved_achievements_special.add(translateAchievementSpecial(achievement_special)));
        JobResponseDto jobResponseDto = application.hasJobReview() ? translateJobReview(application.getJobReview()) : null;

        return new ApplicationDto(application.getId(), application.getEnrollDate(), application.isDeposit_was_paid(), application.getActual_xp_reward(), application.getState(),
                recieved_achievements_special, application.getJobJournal().getId(),translateTrip(application.getTrip()),translateSession(application.getTripSession()), jobResponseDto);
    }

    /**
     * Translate object JobResponse to JobResponseDto
     * @param jobResponse
     * @return JobResponseDto
     */
    @Transactional
    public JobResponseDto translateJobReview(JobResponse jobResponse){
        Objects.requireNonNull(jobResponse);

        return new JobResponseDto(jobResponse.getId(), jobResponse.getNote(), jobResponse.getDate(),
                jobResponse.getRating(), jobResponse.getAuthor().getFirstName() + " " + jobResponse.getAuthor().getLastName());
    }

    /**
     * Translate object UserReview to UserReviewDto
     * @param userReview
     * @return UserReviewDto
     */
    @Transactional
    public UserReviewDto translateUserReview(UserReview userReview){
        Objects.requireNonNull(userReview);

        return new UserReviewDto(userReview.getId(),userReview.getNote(),userReview.getDate(),
                userReview.getRating(),userReview.getUser().getId(),userReview.getAuthor().getId(),translateSession(userReview.getTripSession()));
    }

    /**
     * Counting level from xp
     * @param xp
     * @return int of level
     */
    @Transactional
    public int countLevel(int xp){
        return xp/10 ;
    }
}
