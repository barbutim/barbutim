package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.Application;
import org.springframework.stereotype.Repository;

/**
 * Standard application dao
 */
@Repository
public class ApplicationDao extends BaseDao<Application> {
    protected ApplicationDao() {
        super(Application.class);
    }
}
