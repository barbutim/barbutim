package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.AchievementSpecial;
import org.springframework.stereotype.Repository;

/**
 * Achievements dao
 */
@Repository
public class AchievementSpecialDao extends BaseDao<AchievementSpecial>{
    protected AchievementSpecialDao() {
        super(AchievementSpecial.class);
    }
}
