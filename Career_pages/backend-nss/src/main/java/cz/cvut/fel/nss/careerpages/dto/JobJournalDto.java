package cz.cvut.fel.nss.careerpages.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Basic;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

/**
 * Dto for job journal
 */
public class JobJournalDto {

    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    private int xp_count;

    private int level;

    @JsonIgnore
    @Basic(optional = false)
    private HashMap<CategoryDto, Integer> trip_counter;

    private Long userId;
    private List<AchievementCertificateDto> certificates;
    private List<AchievementCategorizedDto> categorized;
    private List<AchievementSpecialDto> special;

    /**
     * @param id
     * @param xp_count
     * @param trip_counter
     * @param userId
     * @param certificates
     * @param categorized
     * @param special
     * @param level
     * constructor
     */
    public JobJournalDto(@NotNull(message = "Id cannot be blank") Long id, int xp_count, HashMap<CategoryDto, Integer> trip_counter, Long userId, List<AchievementCertificateDto> certificates, List<AchievementCategorizedDto> categorized,
                            List<AchievementSpecialDto> special, int level) {
        this.id = id;
        this.xp_count = xp_count;
        this.trip_counter = trip_counter;
        this.userId = userId;
        this.certificates = certificates;
        this.categorized = categorized;
        this.special = special;
        this.level = level;
    }

    /**
     * constructor
     */
    public JobJournalDto() {
    }

    /**
     * @return get id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     * set id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return get xp
     */
    public int getXp_count() {
        return xp_count;
    }
}
