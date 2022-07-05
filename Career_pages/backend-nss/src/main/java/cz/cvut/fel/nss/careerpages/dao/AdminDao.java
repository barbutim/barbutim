package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.Admin;
import org.springframework.stereotype.Repository;

/**
 * Dao for concrete user - admin
 */
@Repository
public class AdminDao extends BaseDao<Admin>{

    /**
     * constructor
     */
    public AdminDao() {
        super(Admin.class);
    }
}
