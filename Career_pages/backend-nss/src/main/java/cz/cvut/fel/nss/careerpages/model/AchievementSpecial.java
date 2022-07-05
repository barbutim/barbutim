package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for special achievements
 */
@Entity
@Table(name="ACHIEVEMENT_SPECIAL")
public class AchievementSpecial extends Achievement {

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "achievement_special_owned_travel_journals",
        joinColumns = @JoinColumn(name = "achievement_special_id"),
        inverseJoinColumns = @JoinColumn(name = "traveljournal_id"))
    private List<JobJournal> owned_travel_journals;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "recieved_achievement_special_trip",
            joinColumns = @JoinColumn(name = "achievement_special_id"),
            inverseJoinColumns = @JoinColumn(name = "enrollment_id"))
    private List<Application> recieved_via_applications;

    /**
     * constructor
     */
    public AchievementSpecial() {
    }

    /**
     * @param name
     * @param description
     * @param icon
     * constructor
     */
    public AchievementSpecial(String name, String description, String icon) {
        super(name, description, icon);
        owned_travel_journals = new ArrayList<>();
        recieved_via_applications = new ArrayList<>();
    }

    /**
     * @return get owned journals
     */
    public List<JobJournal> getOwned_travel_journals() {
        return owned_travel_journals;
    }
}
