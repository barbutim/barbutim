package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.JobResponse;
import org.springframework.stereotype.Repository;


/**
 * dao for responding on job offer
 */
@Repository
public class JobResponseDao extends BaseDao<JobResponse> {
    /**
     * constructor
     */
    public JobResponseDao() {
        super(JobResponse.class);
    }
}