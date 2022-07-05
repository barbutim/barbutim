package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.dao.CategoryDao;
import cz.cvut.fel.nss.careerpages.dao.JobJournalDao;
import cz.cvut.fel.nss.careerpages.dao.WorkOfferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Service for job journal
 */
@Service
public class JobJournalService {
    private final JobJournalDao dao;
    private final WorkOfferDao workOfferDao;
    private final CategoryDao categoryDao;
    private final AchievementCategorizedService achievementCategorizedService;

    /**
     * @param dao
     * @param workOfferDao
     * @param categoryDao
     * @param achievementCategorizedService
     * constructor
     */
    @Autowired
    public JobJournalService(JobJournalDao dao, WorkOfferDao workOfferDao, CategoryDao categoryDao, AchievementCategorizedService achievementCategorizedService) {
        this.dao = dao;
        this.workOfferDao = workOfferDao;
        this.categoryDao = categoryDao;
        this.achievementCategorizedService = achievementCategorizedService;
    }

    /**
     * Add offer to jobJournal.
     * @param jobJournalId
     * @param offerId
     */
    @Transactional
    public void addTrip(Long jobJournalId, Long offerId) {
        Objects.requireNonNull(jobJournalId);
        Objects.requireNonNull(offerId);

        JobJournal jobJournal = dao.find(jobJournalId);
        Offer offer = workOfferDao.find(offerId);
        Category category = categoryDao.find(offer.getCategory().getId());
        System.out.println("ADDING OFFER" + category.getName());
        jobJournal.addTrip(offer.getCategory().getId());
        dao.update(jobJournal);
        checkCategorizedAchievements(offer.getCategory(), jobJournal);
    }

    /**
     * Add achievementsSpecial to jobJournal.
     * @param jobJournal
     * @param achievementSpecial
     */
    @Transactional
    public void addOwnedSpecialAchievement(JobJournal jobJournal, AchievementSpecial achievementSpecial) {
        Objects.requireNonNull(jobJournal);
        Objects.requireNonNull(achievementSpecial);
        jobJournal.addEarnedAchievementSpecial(achievementSpecial);
        dao.update(jobJournal);
    }

    /**
     * It is used after finalizing/closing the enrollment and adding new offer to hashmap in job journal
     * @param category
     * @param currentJobJournal
     */
    @Transactional
    public void checkCategorizedAchievements(Category category, JobJournal currentJobJournal) {
        int numberOfTripsInCat = currentJobJournal.findAndGetCategoryValueIfExists(category.getId());
        List<AchievementCategorized> categorizedAchievements = achievementCategorizedService.findAllInCategory(category);
        List<AchievementCategorized> ownedAchievements = currentJobJournal.getEarnedAchievementsCategorized();
        if((numberOfTripsInCat == -1) || (categorizedAchievements == null)) return;

        for(AchievementCategorized cA : categorizedAchievements) {
            if((cA.getLimit() <= numberOfTripsInCat) && (!ownedAchievements.contains(cA))) {
                currentJobJournal.addEarnedAchievementCategorized(cA);
                cA.addJobJournal(currentJobJournal);
                achievementCategorizedService.update(cA);
            }
        }
        dao.update(currentJobJournal);
    }

    /**
     * Add xp to users job Journal.
     * @param jobJournalId
     * @param actual_xp_reward
     */
    @Transactional
    public void addXP(Long jobJournalId,int actual_xp_reward) {
        JobJournal jobJournal = dao.find(jobJournalId);
        jobJournal.addsXp(actual_xp_reward);
        dao.update(jobJournal);
    }
}
