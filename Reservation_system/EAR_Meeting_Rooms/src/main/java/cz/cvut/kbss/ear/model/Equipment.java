package cz.cvut.kbss.ear.model;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment AS e"),
        @NamedQuery(name = "Equipment.findByName", query = "SELECT e FROM Equipment AS e WHERE e.name = :name")
})
public class Equipment extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    public Equipment(String name) {
        this.name = name;
    }

    public Equipment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
