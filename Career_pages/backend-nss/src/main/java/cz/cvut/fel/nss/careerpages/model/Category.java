package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for categories
 */
@Entity
@Table(name = "CATEGORY")
public class Category extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false, length = 30, unique = true)
    @NotBlank(message = "Name of category cannot be blank")
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private List<Offer> trips;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<AchievementCategorized> achievementsWithCategory;

    /**
     * constructor
     */
    public Category() {

    }

    /**
     * @param name
     * constructor
     */
    public Category(@NotBlank(message = "Name of category cannot be blank") String name) {
        this.name = name;
        this.trips = new ArrayList<>();
    }

    /**
     * @param trip
     * @return add category to offer
     */
    public boolean add(Offer trip){
        return trips.add(trip);
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
}

