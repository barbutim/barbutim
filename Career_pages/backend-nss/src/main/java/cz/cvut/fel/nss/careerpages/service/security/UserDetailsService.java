package cz.cvut.fel.nss.careerpages.service.security;

import cz.cvut.fel.nss.careerpages.dao.AbstractUserDao;
import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * user details
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AbstractUserDao userDao;

    /**
     * @param userDao
     * constructor
     */
    @Autowired
    public UserDetailsService(AbstractUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * @param email
     * @return
     * @throws UsernameNotFoundException
     * load by username
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final AbstractUser user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }
        return new cz.cvut.fel.nss.careerpages.security.model.UserDetails(user);
    }

}
