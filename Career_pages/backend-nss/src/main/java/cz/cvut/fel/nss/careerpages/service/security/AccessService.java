package cz.cvut.fel.nss.careerpages.service.security;

import cz.cvut.fel.nss.careerpages.dao.ApplicantDao;
import cz.cvut.fel.nss.careerpages.model.Role;
import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import cz.cvut.fel.nss.careerpages.exception.NotAllowedException;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * security access service
 */
@Service
public class AccessService {
    private final PasswordEncoder passwordEncoder;
    private ApplicantDao applicantDao;

    /**
     * @param passwordEncoder
     * @param applicantDao
     * constructor
     */
    @Autowired
    public AccessService(PasswordEncoder passwordEncoder, ApplicantDao applicantDao) {
        this.passwordEncoder = passwordEncoder;
        this.applicantDao = applicantDao;
    }

    /**
     * @param currentUser
     * @return finds user
     */
    @Transactional
    public AbstractUser getUser(AbstractUser currentUser){
        return applicantDao.find(currentUser.getId());
    }
}
