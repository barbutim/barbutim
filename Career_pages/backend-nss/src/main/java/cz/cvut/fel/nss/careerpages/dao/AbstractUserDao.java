package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Objects;

/**
 * Abstract User class for User, Admin and Manager
 */
@Repository
public class  AbstractUserDao extends BaseDao<AbstractUser> {

    /**
     * constructor
     */
    public AbstractUserDao(){super(AbstractUser.class);}

    /**
     * @param id
     * @return find - id
     */
    public AbstractUser find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(AbstractUser.class, id);
    }

    /**
     * @param email
     * @return find - email
     */
    public AbstractUser find(String email) {
        Objects.requireNonNull(email);
        return em.find(AbstractUser.class, email);
    }

    /**
     * @param email
     * @return find by email, creates namedquery
     */
    public AbstractUser findByEmail(String email) {
        try {
            return em.createNamedQuery("AbstractUser.findByEmail", AbstractUser.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
