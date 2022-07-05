package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for categorized achievements
 */
@Entity
@Table(name="ACHIEVEMENT_CATEGORIZED")
public class AchievementCategorized extends Achievement {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Basic(optional = false)
    @Column(nullable = false, name = "limitOf")
    private int limit;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "achievement_categorized_owned_travel_journals",
            joinColumns = @JoinColumn(name = "achievement_categorized_id"),
            inverseJoinColumns = @JoinColumn(name = "traveljournal_id"))
    private List<JobJournal> owned_travel_journals;

    /**
     * constructor
     */
    public AchievementCategorized() {
        owned_travel_journals = new ArrayList<JobJournal>();
    }

    /**
     * @param name
     * @param description
     * @param icon
     * constructor
     */
    public AchievementCategorized(String name, String description, String icon) {
        super(name, description, icon);
        owned_travel_journals = new ArrayList<JobJournal>();
    }

    /**
     * @return get category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category
     * set category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return get limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @return get owned travel journals
     */
    public List<JobJournal> getOwned_travel_journals() {
        return owned_travel_journals;
    }

    /**
     * @param jobJournal
     * add job journal
     */
    public void addJobJournal(JobJournal jobJournal) {
        this.owned_travel_journals.add(jobJournal);
    }
}
