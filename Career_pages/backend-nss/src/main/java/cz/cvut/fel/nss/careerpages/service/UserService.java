package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dao.*;
import cz.cvut.fel.nss.careerpages.dto.AbstractUserDto;
import cz.cvut.fel.nss.careerpages.dto.ApplicantDto;
import cz.cvut.fel.nss.careerpages.dto.JobJournalDto;
import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.exception.BadPassword;
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
 * Service for user
 */
@Service
public class UserService {
    private final ApplicantDao dao;
    private final AdminDao adminDao;
    private final RecruiterDao recruiterDao;
    private final JobResponseDao jobResponseDao;
    private final JobJournalDao jobJournalDao;
    private final AddressDao addressDao;
    private final TranslateService translateService;
    private final TranslateBackService translateBackService;

    /**
     * @param dao
     * @param adminDao
     * @param recruiterDao
     * @param jobResponseDao
     * @param jobJournalDao
     * @param addressDao
     * @param translateService
     * @param translateBackService
     * constructor
     */
    @Autowired
    public UserService(ApplicantDao dao, AdminDao adminDao, RecruiterDao recruiterDao, JobResponseDao jobResponseDao, JobJournalDao jobJournalDao, AddressDao addressDao, TranslateService translateService, TranslateBackService translateBackService) {
        this.dao = dao;
        this.adminDao = adminDao;
        this.recruiterDao = recruiterDao;
        this.jobResponseDao = jobResponseDao;
        this.jobJournalDao = jobJournalDao;
        this.addressDao = addressDao;
        this.translateService = translateService;
        this.translateBackService = translateBackService;
    }

    /**
     * Create new User.
     * @param user new user
     * @param passwordAgain control of password
     * @throws BadPassword if password and passwordAgain is not same.
     */
    @Transactional
    public void createUser(User user, String passwordAgain) throws BadPassword {
        Objects.requireNonNull(user);
        if (!user.getPassword().equals(passwordAgain)) throw new BadPassword();
        user.encodePassword();

        if (user.getRole() != Role.USER) user.setRole(Role.USER);
        dao.persist(user);
        if (user.getAddress() != null){
            user.getAddress().setUser(user);
            addressDao.persist(user.getAddress());
        }
        if (user.getTravel_journal() == null) {
            JobJournal tj = new JobJournal(user);
            jobJournalDao.persist(tj);
            user.setTravel_journal(tj);
        }
        dao.update(user);
    }

    /**
     * Whether exist user with login email.
     * @param login
     * @return boolean
     */
    @Transactional(readOnly = true)
    public boolean exists(String login) {
        return dao.findByEmail(login) != null;
    }

    /**
     * Get current logged in user.
     * @return AbstractUserDto
     * @throws UnauthorizedException
     */
    @Transactional
    public AbstractUserDto showCurrentUser() throws UnauthorizedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new UnauthorizedException();
        if(SecurityUtils.getCurrentUser().getRole().equals(Role.USER)) return translateService.translateUser(dao.find(SecurityUtils.getCurrentUser().getId()));
        if(SecurityUtils.getCurrentUser().getRole().equals(Role.ADMIN)) return translateService.translateAdmin(adminDao.find(SecurityUtils.getCurrentUser().getId()));
        return translateService.translateManager(recruiterDao.find(SecurityUtils.getCurrentUser().getId()));
    }

    /**
     * Get JobJournal of current logged in user.
     * @return JobJournalDto
     */
    @Transactional
    public JobJournalDto getJobJournal() {
        return translateService.translateJobJournal(dao.find(SecurityUtils.getCurrentUser().getId()).getTravel_journal());
    }

    /**
     * Delete user.
     * @param id
     * @throws NotFoundException
     */
    @Transactional
    public void delete(Long id) throws NotFoundException {
        User user = dao.find(id);
        if (user == null) throw new NotFoundException();
        user.getAddress().softDelete();
        addressDao.update(user.getAddress());
        for (Application e :user.getTravel_journal().getEnrollments()) {
            e.softDelete();
        }
        user.getTravel_journal().softDelete();
        jobJournalDao.update(user.getTravel_journal());

        for (JobResponse tr: user.getJobReviews()) {
            tr.softDelete();
            jobResponseDao.update(tr);
        }
        user.softDelete();
        dao.update(user);
    }

    /**
     * Update user.
     * @param applicantDto new object of updated user
     * @param current_user which user I want updated
     * @throws NotFoundException
     */
    @Transactional
    public void update(ApplicantDto applicantDto, AbstractUser current_user) throws NotFoundException {
        Objects.requireNonNull(applicantDto);
        User user = dao.find(current_user.getId());
        if (user == null) throw new NotFoundException();
        User newUser = (User) translateBackService.translateUser(applicantDto);

        newUser.setId(user.getId());
        newUser.setRole(user.getRole());
        newUser.setJobReviews(user.getJobReviews());

        if (newUser.getAddress() != null ) {
            Address oldAddress = user.getAddress();
            newUser.getAddress().setId(oldAddress.getId());
            oldAddress = newUser.getAddress();
            addressDao.update(oldAddress);
        }
        if (newUser.getTravel_journal() != null){
            JobJournal oldJobJournal = user.getTravel_journal();
            newUser.getTravel_journal().setId(oldJobJournal.getId());
            oldJobJournal = newUser.getTravel_journal();
            jobJournalDao.update(oldJobJournal);
        }

        user=newUser;
        dao.update(user);
    }

    /**
     * Get user by id.
     * @param id
     * @return ApplicantDto
     */
    @Transactional
    public ApplicantDto find(Long id) {
        Objects.requireNonNull(id);
        return translateService.translateUser(dao.find(id));
    }

    /**
     * Get user by email.
     * @param email
     * @return ApplicantDto
     */
    @Transactional
    public ApplicantDto findByEmail(String email) {
        Objects.requireNonNull(email);
        return translateService.translateUser(dao.findByEmail(email));
    }

    /**
     * Get all users.
     * @return List<ApplicantDto>
     */
    @Transactional
    public List<ApplicantDto> findAll() {
        List<ApplicantDto> applicantDtos = new ArrayList<>();
        for (User user:dao.findAll()) {
            applicantDtos.add(translateService.translateUser(user));
        }
        return applicantDtos;
    }

    /**
     * Whether user exists.
     * @param id
     * @return boolean
     */
    @Transactional
    public boolean exists(Long id) {
        Objects.requireNonNull(id);
        return dao.exists(id);
    }
}
