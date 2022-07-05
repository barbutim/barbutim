package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.UserReview;
import org.springframework.stereotype.Repository;

/**
 * user review dao
 */
@Repository
public class UserReviewDao extends BaseDao<UserReview> {
    /**
     * constructor
     */
    protected UserReviewDao() {
        super(UserReview.class);
    }
}
