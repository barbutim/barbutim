package cz.cvut.fel.nss.careerpages.service.security;

import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import cz.cvut.fel.nss.careerpages.model.Role;
import org.springframework.stereotype.Service;

/**
 * Role service
 */
@Service
public class RoleService {
    /**
     * @param user
     * @return is user
     */
    public boolean isUser(AbstractUser user){
        return user.getRole() == Role.USER;
    }

    /**
     * @param user
     * @return is manager
     */
    public boolean isManager(AbstractUser user){
        return user.getRole() == Role.ADMIN || user.getRole() == Role.MANAGER;
    }
}