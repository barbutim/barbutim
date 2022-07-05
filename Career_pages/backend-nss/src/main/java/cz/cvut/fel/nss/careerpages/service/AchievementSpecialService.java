package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.model.AchievementSpecial;
import cz.cvut.fel.nss.careerpages.dao.AchievementSpecialDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Service for special achievements
 */
@Service
public class AchievementSpecialService {

    private final AchievementSpecialDao achievementSpecialDao;

    /**
     * @param achievementSpecialDao
     * constructor
     */
    @Autowired
    public AchievementSpecialService(AchievementSpecialDao achievementSpecialDao) {
        this.achievementSpecialDao = achievementSpecialDao;
    }

    /**
     * Get all AchievementsSpecial from database.
     * @return  List<AchievementSpecial>
     */
    @Transactional
    public List<AchievementSpecial> findAll() {
        return achievementSpecialDao.findAll();
    }

    /**
     * Get one AchievementSpecial by id.
     * @param id
     * @return AchievementSpecial
     */
    @Transactional
    public AchievementSpecial find(Long id) {
        return achievementSpecialDao.find(id);
    }

    /**
     * Create new AchievementSpecial.
     * @param achievement
     */
    @Transactional
    public void create(AchievementSpecial achievement) {
        achievementSpecialDao.persist(achievement);
    }

    /**
     * Update AchievementSpecial.
     * @param achievement
     */
    @Transactional
    public void update(AchievementSpecial achievement) {
        Objects.requireNonNull(achievement);
        achievementSpecialDao.update(achievement);
    }
}
