package cz.cvut.fel.nss.careerpages.service.security;

import cz.cvut.fel.nss.careerpages.dao.AdminDao;
import cz.cvut.fel.nss.careerpages.dao.RecruiterDao;
import cz.cvut.fel.nss.careerpages.dto.AbstractUserDto;
import cz.cvut.fel.nss.careerpages.model.Role;
import cz.cvut.fel.nss.careerpages.service.TranslateService;
import cz.cvut.fel.nss.careerpages.dao.ApplicantDao;
import cz.cvut.fel.nss.careerpages.exception.AlreadyLoginException;
import cz.cvut.fel.nss.careerpages.security.DefaultAuthenticationProvider;
import cz.cvut.fel.nss.careerpages.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Login service security
 */
@Service
public class LoginService {

    private DefaultAuthenticationProvider provider;
    private TranslateService translateService;
    private ApplicantDao applicantDao;
    private RecruiterDao recruiterDao;
    private AdminDao adminDao;

    /**
     * @param provider
     * @param translateService
     * @param applicantDao
     * @param recruiterDao
     * @param adminDao
     * constructor
     */
    @Autowired
    public LoginService(DefaultAuthenticationProvider provider, TranslateService translateService, ApplicantDao applicantDao, RecruiterDao recruiterDao, AdminDao adminDao) {
        this.provider = provider;
        this.translateService = translateService;
        this.applicantDao = applicantDao;
        this.recruiterDao = recruiterDao;
        this.adminDao = adminDao;
    }

    /**
     * @param email
     * @param password
     * @return
     * @throws AlreadyLoginException
     * login
     */
    @Transactional(readOnly = true)
    public AbstractUserDto login(String email, String password) throws AlreadyLoginException {
        if (SecurityUtils.getCurrentUserDetails() != null) throw new AlreadyLoginException();
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
        provider.authenticate(auth);
        Role role = SecurityUtils.getCurrentUser().getRole();
        if( role == Role.MANAGER) return translateService.translateManager(recruiterDao.find(SecurityUtils.getCurrentUser().getId()));
        else if( role == Role.ADMIN) return translateService.translateAdmin(adminDao.find(SecurityUtils.getCurrentUser().getId()));
        return translateService.translateUser(applicantDao.find(SecurityUtils.getCurrentUser().getId()));
    }
}
