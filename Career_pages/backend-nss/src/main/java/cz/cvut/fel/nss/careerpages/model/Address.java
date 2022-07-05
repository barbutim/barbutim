package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Model for addresses
 */
@Entity
@Table(name = "ADDRESS")
public class Address extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 100, min = 1)
    @NotBlank(message = "City cannot be blank")
    private String city;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 100, min = 1)
    @NotBlank(message = "Street cannot be blank")
    private String street;

    @Basic(optional = false)
    @Column(nullable = false)
    private int houseNumber;

    @Basic(optional = false)
    @Column(nullable = false)
    @NotBlank(message = "ZIP code cannot be blank")
    private String zipCode;

    @Basic(optional = false)
    @Column(nullable = false)
    @NotBlank(message = "Country cannot be blank")
    private String country;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private AbstractUser user;

    /**
     * @param city
     * @param street
     * @param houseNumber
     * @param zipCode
     * @param country
     * @param user
     * constructor
     */
    public Address(@NotBlank(message = "City cannot be blank") String city,
                   @NotBlank(message = "Street cannot be blank") String street,
                   int houseNumber,
                   @NotBlank(message = "ZIP code cannot be blank") String zipCode,
                   @NotBlank(message = "Country cannot be blank") String country,
                   AbstractUser user) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.country = country;
        this.user = user;
    }

    /**
     * constructor
     */
    public Address() {

    }

    /**
     * @return get city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return get street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return get house number
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * @return get zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @return get country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return get user
     */
    public AbstractUser getUser() {
        return user;
    }

    /**
     * @param city
     * set city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @param street
     * set street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @param houseNumber
     * set house number
     */
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * @param zipCode
     * set zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @param country
     * set country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @param user
     * set user
     */
    public void setUser(AbstractUser user) {
        this.user = user;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
