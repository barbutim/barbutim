package cz.cvut.fel.nss.careerpages.dto;

import javax.persistence.Basic;
import javax.validation.constraints.NotNull;

/**
 * dto for addresses
 */
public class AddressDto {
    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    @NotNull(message = "City cannot be blank")
    private String city;

    @Basic(optional = false)
    @NotNull(message = "Street cannot be blank")
    private String street;

    @Basic(optional = false)
    private int houseNumber;

    @Basic(optional = false)
    @NotNull(message = "ZIP code cannot be blank")
    private String zipCode;

    @Basic(optional = false)
    @NotNull(message = "Country cannot be blank")
    private String country;

    private Long userId;

    /**
     * @param id
     * @param city
     * @param street
     * @param houseNumber
     * @param zipCode
     * @param country
     * @param userId
     * constructor
     */
    public AddressDto(@NotNull(message = "Id cannot be blank") Long id, @NotNull(message = "City cannot be blank") String city,
                      @NotNull(message = "Street cannot be blank") String street, int houseNumber, @NotNull(message = "ZIP code cannot be blank") String zipCode,
                      @NotNull(message = "Country cannot be blank") String country, Long userId) {

        this.id = id;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.country = country;
        this.userId = userId;
    }

    /**
     * constructor
     */
    public AddressDto() {
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
     * @return string
     */
    @Override
    public String toString() {
        return "AddressDto{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", userId=" + userId +
                '}';
    }
}
