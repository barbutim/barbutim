package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.AchievementCategorized;
import org.springframework.stereotype.Repository;

/**
 * Achievements dao
 */
@Repository
public class AchievementCategorizedDao extends BaseDao<AchievementCategorized> {
    protected AchievementCategorizedDao() {
        super(AchievementCategorized.class);
    }
}
