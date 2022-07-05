package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for achievements
 */
@MappedSuperclass
public abstract class Achievement extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    private String icon;

    @JsonIgnore
    @ManyToMany
    private List<Offer> trips;

    /**
     * constructor
     */
    public Achievement() {
    }

    /**
     * @param name
     * @param description
     * @param icon
     * constructor
     */
    public Achievement(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.trips = new ArrayList<>();
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

    /**
     * @return get icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return get trips
     */
    public List<Offer> getTrips() {
        return trips;
    }
}
