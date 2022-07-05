package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dto.AchievementCertificateDto;
import cz.cvut.fel.nss.careerpages.dto.AchievementDto;
import cz.cvut.fel.nss.careerpages.dto.AchievementSpecialDto;
import cz.cvut.fel.nss.careerpages.model.*;
import cz.cvut.fel.nss.careerpages.dao.AchievementCategorizedDao;
import cz.cvut.fel.nss.careerpages.dao.AchievementCertificateDao;
import cz.cvut.fel.nss.careerpages.dao.AchievementSpecialDao;
import cz.cvut.fel.nss.careerpages.dto.AchievementCategorizedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service for achievements
 */
@Service
public class AchievementService {
    private final AchievementCertificateDao achievementCertificateDao;
    private final AchievementCategorizedDao achievementCategorizedDao;
    private final AchievementSpecialDao achievementSpecialDao;
    private final TranslateService translateService;
    private final AchievementCategorizedService achievementCategorizedService;
    private final AchievementSpecialService achievementSpecialService;
    private final AchievementCertificateService achievementCertificateService;

    /**
     * @param achievementCertificateDao
     * @param achievementCategorizedDao
     * @param achievementSpecialDao
     * @param translateService
     * @param achievementCategorizedService
     * @param achievementSpecialService
     * @param achievementCertificateService
     * constructor
     */
    @Autowired
    public AchievementService(AchievementCertificateDao achievementCertificateDao, AchievementCategorizedDao achievementCategorizedDao, AchievementSpecialDao achievementSpecialDao, TranslateService translateService, AchievementCategorizedService achievementCategorizedService, AchievementSpecialService achievementSpecialService, AchievementCertificateService achievementCertificateService) {
        this.achievementCertificateDao = achievementCertificateDao;
        this.achievementCategorizedDao = achievementCategorizedDao;
        this.achievementSpecialDao = achievementSpecialDao;
        this.translateService = translateService;
        this.achievementCategorizedService = achievementCategorizedService;
        this.achievementSpecialService = achievementSpecialService;
        this.achievementCertificateService = achievementCertificateService;
    }

    /**
     * Get all Achievements of User.
     * @param user
     * @return List<AchievementDto>
     */
    @Transactional
    public List<AchievementDto> findAllOfUser(User user){
        Objects.requireNonNull(user);
        List<AchievementDto> achievementDtos = new ArrayList<>();
        List<AchievementCertificateDto> achievementCertificates = new ArrayList<>();
        List<AchievementCategorizedDto> achievementCategorized = new ArrayList<>();
        List<AchievementSpecialDto> achievementSpecials = new ArrayList<>();

        for (AchievementCertificate certificate: user.getTravel_journal().getCertificates() ) {
           achievementCertificates.add(translateService.translateAchievementCertificate(certificate));
        }
        for (AchievementCategorized categorized: user.getTravel_journal().getEarnedAchievementsCategorized()) {
            achievementCategorized.add(translateService.translateAchievementCategorized(categorized));
        }
        for (AchievementSpecial special: user.getTravel_journal().getEarnedAchievementsSpecial() ) {
            achievementSpecials.add(translateService.translateAchievementSpecial(special));
        }

        achievementDtos.addAll(achievementCategorized);
        achievementDtos.addAll(achievementCertificates);
        achievementDtos.addAll(achievementSpecials);
        return achievementDtos;
    }
}
