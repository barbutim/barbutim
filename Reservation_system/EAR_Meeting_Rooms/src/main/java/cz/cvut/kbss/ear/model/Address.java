package cz.cvut.kbss.ear.model;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address AS a"),
        @NamedQuery(name = "Address.findByStreet", query = "SELECT a FROM Address AS a WHERE a.street = :street")
})
public class Address extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String city;

    @Basic(optional = false)
    @Column(nullable = false)
    private String street;

    @Basic(optional = false)
    @Column(nullable = false)
    private String houseNumber;

    @Basic(optional = false)
    @Column(nullable = false)
    private String postcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
