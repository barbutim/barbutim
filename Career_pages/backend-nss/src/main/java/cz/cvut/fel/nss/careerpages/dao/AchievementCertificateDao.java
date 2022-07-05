package cz.cvut.fel.nss.careerpages.dao;

import cz.cvut.fel.nss.careerpages.model.AchievementCertificate;
import org.springframework.stereotype.Repository;

/**
 * Achievements dao
 */
@Repository
public class AchievementCertificateDao extends BaseDao<AchievementCertificate> {
    protected AchievementCertificateDao() {
        super(AchievementCertificate.class);
    }
}
