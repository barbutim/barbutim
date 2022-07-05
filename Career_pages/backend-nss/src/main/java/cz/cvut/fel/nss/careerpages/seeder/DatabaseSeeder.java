package cz.cvut.fel.nss.careerpages.seeder;

import cz.cvut.fel.nss.careerpages.dao.*;
import cz.cvut.fel.nss.careerpages.dto.JobSessionDto;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.service.ApplicationService;
import cz.cvut.fel.nss.careerpages.service.JobJournalService;
import cz.cvut.fel.nss.careerpages.service.TranslateService;
import cz.cvut.fel.nss.careerpages.service.WorkOfferService;
import cz.cvut.fel.nss.careerpages.exception.NotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * put default values into database, initialization data
 */
@Component
public class DatabaseSeeder implements
        ApplicationListener<ContextRefreshedEvent> {

    private final Logger LOGGER = Logger.getLogger(DatabaseSeeder.class.getName());
    private final WorkOfferDao workOfferDao;
    private final JobSessionDao jobSessionDao;
    private final AchievementCertificateDao achievementCertificateDao;
    private final AchievementCategorizedDao achievementCategorizedDao;
    private final AchievementSpecialDao achievementSpecialDao;
    private final CategoryDao categoryDao;
    private final ApplicantDao applicantDao;
    private final AddressDao addressDao;
    private final ApplicationDao applicationDao;
    private final WorkOfferService workOfferService;
    private final TranslateService translateService;
    private final JobJournalService jobJournalService;
    private final JobJournalDao jobJournalDao;
    private final JobResponseDao jobResponseDao;
    private final UserReviewDao userReviewDao;
    private final ApplicationService applicationService;
    private final RecruiterDao recruiterDao;
    private final AdminDao adminDao;

    /**
     * @param workOfferDao
     * @param jobSessionDao
     * @param achievementCertificateDao
     * @param achievementCategorizedDao
     * @param achievementSpecialDao
     * @param categoryDao
     * @param applicantDao
     * @param addressDao
     * @param applicationDao
     * @param workOfferService
     * @param translateService
     * @param jobJournalService
     * @param jobJournalDao
     * @param jobResponseDao
     * @param userReviewDao
     * @param applicationService
     * @param recruiterDao
     * @param adminDao
     * constructor
     */
    @Autowired
    public DatabaseSeeder(WorkOfferDao workOfferDao, JobSessionDao jobSessionDao, AchievementCertificateDao achievementCertificateDao,
                          AchievementCategorizedDao achievementCategorizedDao, AchievementSpecialDao achievementSpecialDao,
                          CategoryDao categoryDao, ApplicantDao applicantDao, AddressDao addressDao, ApplicationDao applicationDao,
                          WorkOfferService workOfferService, TranslateService translateService, JobJournalService jobJournalService,
                          JobJournalDao jobJournalDao, JobResponseDao jobResponseDao, UserReviewDao userReviewDao, ApplicationService applicationService,
                          RecruiterDao recruiterDao, AdminDao adminDao) {
        this.workOfferDao = workOfferDao;
        this.jobSessionDao = jobSessionDao;
        this.achievementCertificateDao = achievementCertificateDao;
        this.achievementCategorizedDao = achievementCategorizedDao;
        this.achievementSpecialDao = achievementSpecialDao;
        this.categoryDao = categoryDao;
        this.applicantDao = applicantDao;
        this.addressDao = addressDao;
        this.applicationDao = applicationDao;
        this.workOfferService = workOfferService;
        this.translateService = translateService;
        this.jobJournalService = jobJournalService;
        this.jobJournalDao = jobJournalDao;
        this.jobResponseDao = jobResponseDao;
        this.userReviewDao = userReviewDao;
        this.applicationService = applicationService;
        this.recruiterDao = recruiterDao;
        this.adminDao = adminDao;
    }

    /**
     * @param contextRefreshedEvent
     * on event, do init functions
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createUsers();
        createCategories();
        createOffers();
        try {
            signUsersToTrips();
        } catch (NotAllowedException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * default users for database
     */
    private void createUsers(){
        //user Timotej
        User user = new User(BCrypt.hashpw("123456",BCrypt.gensalt()),"Timotej","Barbuš","timotej@gmail.com", "421673452");
        user.setRole(Role.USER);
        applicantDao.persist(user);
        Address address = new Address();
        address.setUser(user);
        address.setCountry("Slovakia");
        address.setCity("Kosice");
        address.setStreet("Random");
        address.setHouseNumber(12);
        address.setZipCode("08304");
        addressDao.persist(address);
        user.setAddress(address);
        applicantDao.update(user);
        user.getTravel_journal().setXp_count(11);
        applicantDao.update(user);

        //user role Jaro
        user = new User(BCrypt.hashpw("000000",BCrypt.gensalt()),"Jaro","Jelinek","jaroslav5@gmail.com", "421902591456");
        user.setRole(Role.USER);
        applicantDao.persist(user);
        address = new Address();
        address.setUser(user);
        address.setCountry("Slovakia");
        address.setCity("Bardejov");
        address.setStreet("Novy Sad");
        address.setHouseNumber(23);
        address.setZipCode("08501");
        addressDao.persist(address);
        user.setAddress(address);
        applicantDao.update(user);

        //user role Anna
        user = new User(BCrypt.hashpw("anna123",BCrypt.gensalt()),"Anna","Popradska","anka@gmail.com", "47890458893");
        applicantDao.persist(user);
        address = new Address();
        address.setUser(user);
        address.setCountry("Slovakia");
        address.setCity("Poprad");
        address.setStreet("Popradov");
        address.setHouseNumber(3);
        address.setZipCode("08452");
        addressDao.persist(address);
        user.setAddress(address);
        applicantDao.update(user);
        System.out.println("Test user.");

        //admin role Peter
        Admin admin = new Admin(BCrypt.hashpw("petkodetko",BCrypt.gensalt()),"Peter","Mrazik","petoooo@gmail.com");
        adminDao.persist(admin);
        address = new Address();
        address.setUser(user);
        address.setCountry("Slovakia");
        address.setCity("Bratislava");
        address.setStreet("Vajnorska");
        address.setHouseNumber(12);
        address.setZipCode("03412");
        addressDao.persist(address);
        admin.setAddress(address);
        adminDao.update(admin);
        System.out.println("Test admin.");

        //recruiter role Pavel
        Recruiter recruiter = new Recruiter(BCrypt.hashpw("insacek",BCrypt.gensalt()),"Pavel","Naplava","ins@gmail.com","420 986 234","TicketFactory s.r.o");
        recruiterDao.persist(recruiter);
        address = new Address();
        address.setUser(user);
        address.setCountry("Czech Republic");
        address.setCity("Praha");
        address.setStreet("Krizikova");
        address.setHouseNumber(3);
        address.setZipCode("09000");
        addressDao.persist(address);
        recruiter.setAddress(address);
        recruiterDao.update(recruiter);
        System.out.println("Test rectuiter.");

        //recruiter role Jan
        recruiter = new Recruiter(BCrypt.hashpw("insacek2",BCrypt.gensalt()),"Jan","Koci","metal@gmail.com","420 222 110","TicketFactory s.r.o");
        recruiterDao.persist(recruiter);
        address = new Address();
        address.setUser(user);
        address.setCountry("Czech Republic");
        address.setCity("Praha");
        address.setStreet("Letnany");
        address.setHouseNumber(1);
        address.setZipCode("09800");
        addressDao.persist(address);
        recruiter.setAddress(address);
        recruiterDao.update(recruiter);
        System.out.println("Test other rectuiter");
    }

    /**
     * create default work offers for database
     */
    @Transactional
    void createOffers(){
        String description;
        Offer offer;
        JobSession tripSession;

        // Tester IT
        description = "V tejto práci sa zameriate na testovanie rôznych technológií do aút napríklad ako interaktívny displej atd." ;
        offer = new Offer("Tester it zariadení do áut",10,description,"tester",150,"Praha",1, recruiterDao.findByEmail("ins@gmail.com"));
        workOfferDao.persist(offer);
        tripSession = new JobSession(offer, LocalDate.parse("2022-06-06"), LocalDate.parse("2024-06-12"), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2024-06-12"), LocalDate.parse("2025-09-18"), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2025-09-18"), LocalDate.parse("2025-10-24"), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        workOfferDao.update(offer);

        // Tester aut
        description = "V tejto práci sa zameriate na testovanie aút napríklad ako pohodlnosť, responzivita auta atd." ;
        offer = new Offer("Tester vozidiel",10,description,"testaut",120,"Praha",1, recruiterDao.findByEmail("ins@gmail.com"));
        workOfferDao.persist(offer);
        tripSession = new JobSession(offer, LocalDate.parse("2020-07-07"), LocalDate.parse("2021-02-21"), 4);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2021-02-21"), LocalDate.parse("2021-04-18"), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2021-04-18"), LocalDate.parse("2022-05-20"), 5);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.now().minusDays(16), LocalDate.now().minusDays(1), 4);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.now().minusDays(15), LocalDate.now().minusDays(2), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.now().minusDays(21), LocalDate.now().minusDays(14), 2);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        workOfferDao.update(offer);

        // Marketing analytik
        description = "V tejto práci budete pracovať ako marketingový analytik a zameriavať sa na aktuálne trendy" ;
        offer = new Offer("Marketing analytik",8,description,"markan",110,"Praha",3, recruiterDao.findByEmail("ins@gmail.com"));
        workOfferDao.persist(offer);
        tripSession = new JobSession(offer, LocalDate.parse("2020-07-07"), LocalDate.parse("2021-02-21"), 5);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2021-02-21"), LocalDate.parse("2021-04-18"), 4);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2021-04-18"), LocalDate.parse("2022-05-20"), 8);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        workOfferDao.update(offer);

        // Upratovac/ka
        description = "V tejto práci budete pôsobiť ako upratovač/ka" ;
        offer = new Offer("Upratovač/ka",3,description,"uprat",140,"Praha",0, recruiterDao.findByEmail("ins@gmail.com"));
        workOfferDao.persist(offer);
        tripSession = new JobSession(offer, LocalDate.parse("2020-06-01"), LocalDate.parse("2020-08-12"), 2);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2020-08-12"), LocalDate.parse("2022-03-21"), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2022-03-21"), LocalDate.parse("2022-06-20"), 2);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        workOfferDao.update(offer);

        // Projekt manager
        description = "V tejto práci budete pôsobiť ako projektový manažér, a viesť tým, ktorý bude smerovať firmu do budúcnosti firmy." ;
        offer = new Offer("Project Rectuiter",3,description,"projman",200,"Praha",0, recruiterDao.findByEmail("ins@gmail.com"));
        workOfferDao.persist(offer);
        tripSession = new JobSession(offer, LocalDate.parse("2022-06-06"), LocalDate.parse("2024-06-12"), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2024-06-12"), LocalDate.parse("2025-09-18"), 5);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2025-09-18"), LocalDate.parse("2025-10-24"), 3);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        workOfferDao.update(offer);

        // Recepcna
        description = "Práca recepčnej na pobočke";
        offer = new Offer("Recepčná", 12, description, "recpra", 135, "Praha", 5, recruiterDao.findByEmail("metal@gmail.com"));
        workOfferDao.persist(offer);
        tripSession = new JobSession(offer, LocalDate.now().minusDays(16), LocalDate.now().minusDays(1), 2);
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        workOfferDao.update(offer);

        // Pracovník pri páse
        description = "V tejto práci budete pracovať pri páse a pomáhať vytvárať nové súčiastky do áut" ;
        offer = new Offer("Pracovník pri páse",6,description,"pracpas",180,"Praha",1, recruiterDao.findByEmail("metal@gmail.com"));
        workOfferDao.persist(offer);
        tripSession = new JobSession(offer, LocalDate.parse("2020-08-06"), LocalDate.parse("2020-08-12"), 5); //0
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2020-08-12"), LocalDate.parse("2020-08-18"), 4);//1
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2020-08-18"), LocalDate.parse("2021-08-24"), 8);//2
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2021-08-06"), LocalDate.parse("2021-08-12"), 2);//3
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2021-08-12"), LocalDate.parse("2021-08-18"), 3);//4
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2021-08-18"), LocalDate.parse("2021-08-24"), 3);//5
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2022-08-06"), LocalDate.parse("2022-08-12"), 3);//6
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2022-08-12"), LocalDate.parse("2022-08-18"), 8);//7
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        tripSession = new JobSession(offer, LocalDate.parse("2022-08-18"), LocalDate.parse("2022-08-24"), 2);//8
        jobSessionDao.persist(tripSession);
        offer.addSession(tripSession);
        workOfferDao.update(offer);
    }

    /**
     * create categories for work offers
     */
    void createCategories(){
        Category category = new Category("Tester");//0
        categoryDao.persist(category);
        category = new Category("IT technik");//1
        categoryDao.persist(category);
        category = new Category("IT asistent");//2
        categoryDao.persist(category);
        category = new Category("Instalater");//3
        categoryDao.persist(category);
        category = new Category("Student");//4
        categoryDao.persist(category);
        category = new Category("Manager");//5
        categoryDao.persist(category);
        category = new Category("Radovy pracovnik");//6
        categoryDao.persist(category);
        category = new Category("Technicka podpora");//7
        categoryDao.persist(category);
        category = new Category("Vodic");//8
        categoryDao.persist(category);
    }

    /**
     * @throws NotAllowedException
     * @throws NotFoundException
     * Add users to job offers
     */
    void signUsersToTrips() throws NotAllowedException, NotFoundException {
        User user = applicantDao.findAll().get(0);
        Offer offer = workOfferDao.findAll().get(0);
        JobSession tripSession = offer.getSessions().get(0);
        JobJournal travelJournal;

        offer = workOfferDao.findAll().get(1);
        tripSession = offer.getSessions().get(0);
        signUserToTrip(user, tripSession);

        travelJournal = user.getTravel_journal();
        Application e = travelJournal.getEnrollments().get(0);
        e.setDeposit_was_paid(true);
        applicationDao.update(e);

        user = applicantDao.findAll().get(2);
        offer = workOfferDao.findAll().get(0);
        tripSession = offer.getSessions().get(1);
        signUserToTrip(user, tripSession);

        travelJournal = user.getTravel_journal();
        e = travelJournal.getEnrollments().get(0);
        e.setState(ApplicationState.CANCELED);
        applicationDao.update(e);

        user = applicantDao.findAll().get(1);
        offer = workOfferDao.findAll().get(1);
        tripSession = offer.getSessions().get(1);
        signUserToTrip(user, tripSession);
        signUpUserToExpiredEnrollmentsForTesting(user);

        travelJournal = user.getTravel_journal();
        e = travelJournal.getEnrollments().get(2);
        e.setDeposit_was_paid(true);
        e.setState(ApplicationState.ACTIVE);
        applicationDao.update(e);
    }

    /**
     * @param user
     * @param tripSession
     * @throws NotAllowedException
     * add user to job offer
     */
    void signUserToTrip(AbstractUser user, JobSession tripSession) throws NotAllowedException {
        JobSessionDto tripSessionDto;
        tripSessionDto = translateService.translateSession(tripSession);
        workOfferService.signUpToTrip(tripSessionDto, user);
    }

    /**
     * @param user
     * @throws NotAllowedException
     * @throws NotFoundException
     * sign up users for expired enrollment
     */
    private void signUpUserToExpiredEnrollmentsForTesting(User user) throws NotAllowedException, NotFoundException {
        JobJournal travelJournal = user.getTravel_journal();
        List<Application> applications = travelJournal.getEnrollments();
        JobSession tripSession;
        Offer offer = workOfferDao.findAll().get(1);
        Application e;

        tripSession = offer.getSessions().get(3);
        e = createEnrol(tripSession, user);
        applications.add(e);
        applicationDao.persist(e);

        tripSession = offer.getSessions().get(4);
        e = createEnrol(tripSession, user);
        applications.add(e);
        applicationDao.persist(e);

        tripSession = offer.getSessions().get(5);
        e = createEnrol(tripSession, user);
        applications.add(e);
        applicationDao.persist(e);
        travelJournal.setEnrollments(applications);
        jobJournalDao.update(travelJournal);

        offer = workOfferDao.findAll().get(6);
        for(int i = 1; i < 7; i++) {
            tripSession = offer.getSessions().get(i);
            e = createEnrol(tripSession, user);
            applications.add(e);
            applicationDao.persist(e);
        }
        e = user.getTravel_journal().getEnrollments().get(0);
        e.setDeposit_was_paid(true);
        e.setState(ApplicationState.ACTIVE);
        applicationDao.update(e);

        e = user.getTravel_journal().getEnrollments().get(1);
        e.setDeposit_was_paid(false);
        e.setState(ApplicationState.ACTIVE);
        applicationDao.update(e);

        for(int i = 5; i < 9; i++) {
            e = user.getTravel_journal().getEnrollments().get(i);
            e.setDeposit_was_paid(true);
            e.setState(ApplicationState.ACTIVE);
            applicationDao.update(e);
        }
    }

    /**
     * @param tripSession
     * @param user
     * @return app
     * create enrol
     */
    private Application createEnrol(JobSession tripSession, User user) {
        Application application = new Application();
        application.setDeposit_was_paid(false);
        application.setEnrollDate(LocalDateTime.now());
        application.setActual_xp_reward(0);
        application.setTrip(tripSession.getTrip());
        application.setState(ApplicationState.ACTIVE);
        application.setTripSession(tripSession);
        application.setJobJournal(user.getTravel_journal());

        return application;
    }
}
