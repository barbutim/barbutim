package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dao.AbstractUserDao;
import cz.cvut.fel.nss.careerpages.dao.RecruiterDao;
import cz.cvut.fel.nss.careerpages.dto.RecruiterDto;
import cz.cvut.fel.nss.careerpages.model.Address;
import cz.cvut.fel.nss.careerpages.model.Recruiter;
import cz.cvut.fel.nss.careerpages.model.Role;
import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import cz.cvut.fel.nss.careerpages.dao.AddressDao;
import cz.cvut.fel.nss.careerpages.exception.BadPassword;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service for recruiter
 */
@Service
public class RecruiterService {
    private final RecruiterDao recruiterDao;
    private final AddressDao addressDao;
    private final AbstractUserDao abstractUserDao;
    private final TranslateService translateService;

    /**
     * @param recruiterDao
     * @param addressDao
     * @param abstractUserDao
     * @param translateService
     * constructor
     */
    public RecruiterService(RecruiterDao recruiterDao, AddressDao addressDao, AbstractUserDao abstractUserDao, TranslateService translateService) {
        this.recruiterDao = recruiterDao;
        this.addressDao = addressDao;
        this.abstractUserDao = abstractUserDao;
        this.translateService = translateService;
    }

    /**
     * Create new rectuiter.
     * @param recruiter is rectuiter who I want to create
     * @param passwordAgain verification if password is correct.
     * @throws BadPassword exception, if password and password again is not same.
     */
    @Transactional
    public void create(Recruiter recruiter, String passwordAgain) throws BadPassword {
        Objects.requireNonNull(recruiter);
        if (!recruiter.getPassword().equals(passwordAgain)) throw new BadPassword();
        recruiter.encodePassword();
        recruiter.setRole(Role.MANAGER);
        recruiterDao.persist(recruiter);
        if (recruiter.getAddress() != null){
            recruiter.getAddress().setUser(recruiter);
            addressDao.persist(recruiter.getAddress());
        }
        recruiterDao.update(recruiter);
    }

    /**
     * Get Rectuiter by id.
     * @param id
     * @return RecruiterDto
     */
    @Transactional
    public RecruiterDto find(Long id) {
        Objects.requireNonNull(id);
        Recruiter user = recruiterDao.find(id);
        if(user != null && user.getRole() == Role.MANAGER) return translateService.translateManager(user);
        else return null;
    }

    /**
     * Get all Managers from database.
     * @return List<RecruiterDto>
     */
    @Transactional
    public List<RecruiterDto> findAll() {
        List<RecruiterDto> recruiterDtos = new ArrayList<>();
        for (Recruiter recruiter : recruiterDao.findAll()) {
            recruiterDtos.add(translateService.translateManager(recruiter));
        }
        return recruiterDtos;
    }

    /**
     * Update Rectuiter.
     * @param newUser is updated Rectuiter.
     * @param current_user is current logged in user.
     * @throws NotFoundException if manager who I want to update doesnt exist.
     */
    @Transactional
    public void update(Recruiter newUser, AbstractUser current_user) throws NotFoundException{
        Objects.requireNonNull(newUser);
        current_user = recruiterDao.find(current_user.getId());
        AbstractUser user = recruiterDao.findByEmail(newUser.getEmail());
        if (user == null) throw NotFoundException.create("Admin", newUser.getEmail());
        else if (current_user.getRole() == Role.MANAGER) {
            user = current_user;
        }

        newUser.setId(user.getId());
        if (newUser.getAddress() != null ) {
            Address oldAddress = user.getAddress();
            newUser.getAddress().setId(oldAddress.getId());
            oldAddress = newUser.getAddress();
            addressDao.update(oldAddress);
        }

        user = newUser;
        abstractUserDao.update(user);
    }
}
