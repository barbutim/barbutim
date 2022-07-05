package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for achievement certificate
 */
@Entity
@Table(name = "ACHIEVEMENT_CERTIFICATE")
public class AchievementCertificate extends Achievement{

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "achievement_certificate_owned_travel_journals",
            joinColumns = @JoinColumn(name = "achievement_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "traveljournal_id"))
    private List<JobJournal> owned_travel_journals;

    /**
     * constructor
     */
    public AchievementCertificate() {
    }

    /**
     * @param name
     * @param description
     * @param icon
     * constructor
     */
    public AchievementCertificate(String name, String description, String icon) {
        super(name, description, icon);
        owned_travel_journals = new ArrayList<>();
    }

    /**
     * @return get owned journals
     */
    public List<JobJournal> getOwned_travel_journals() {
        return owned_travel_journals;
    }
}
