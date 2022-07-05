package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Objects;


/**
 * Dao for applicants, when lookiing for job
 */
@Repository
public class ApplicantDao extends BaseDao<User> {
    /**
     * constructor
     */
    public ApplicantDao(){super(User.class);}

    /**
     * @param id
     * @return find - id
     */
    public User find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(User.class, id);
    }

    /**
     * @param email
     * @return find by email
     */
    public User findByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}