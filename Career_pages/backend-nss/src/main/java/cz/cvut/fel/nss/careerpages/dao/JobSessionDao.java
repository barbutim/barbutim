package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.JobSession;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * dao for job session
 */
@Repository
public class JobSessionDao extends BaseDao<JobSession> {
    /**
     * constructor
     */
    public JobSessionDao() {
        super(JobSession.class);
    }

    /**
     * @param trip_short_name
     * @return query found
     * find jobs
     */
    public List<JobSession> find(String trip_short_name){
        {
            try {
                return em.createNamedQuery("JobSession.findByTrip", JobSession.class).setParameter("trip_short_name", trip_short_name)
                        .getResultList();
            } catch (NoResultException e) {
                return null;
            }
        }
    }
}