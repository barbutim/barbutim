package cz.cvut.fel.nss.careerpages.dto;

import javax.persistence.Basic;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Achievements special dto
 */
public class AchievementSpecialDto extends AchievementDto{
    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String description;

    @Basic(optional = false)
    private String icon;

    private List<Long> trips;
    private List<Long> owned_travel_journals;

    /**
     * @param id
     * @param name
     * @param description
     * @param icon
     * @param trips
     * @param owned_travel_journals
     * constructor
     */
    public AchievementSpecialDto(@NotNull(message = "Id cannot be blank") Long id, String name, String description, String icon, List<Long> trips, List<Long> owned_travel_journals) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.trips = trips;
        this.owned_travel_journals = owned_travel_journals;
    }

    /**
     * constructor
     */
    public AchievementSpecialDto() {

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
     * @return get name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return get description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     * set description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
