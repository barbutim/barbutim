package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.Recruiter;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Objects;

/**
 * dao for recruiters
 */
@Repository
public class RecruiterDao extends BaseDao<Recruiter>{

    /**
     * constructor
     */
    public RecruiterDao() {
        super(Recruiter.class);
    }


    /**
     * @param id
     * @return find - id
     * find method
     */
    public Recruiter find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(Recruiter.class, id);
    }

    /**
     * @param email
     * @return query
     * find by email
     */
    public Recruiter findByEmail(String email) {
        try {
            return em.createNamedQuery("Manager.findByEmail", Recruiter.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
