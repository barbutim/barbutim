package cz.cvut.fel.nss.careerpages.dto;

import cz.cvut.fel.nss.careerpages.model.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * abstract user default dto class
 */
public class AbstractUserDto  {

    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Size(max = 30, min = 1, message = "First name is in incorrect format.")
    @NotNull(message = "First name cannot be blank")
    private String firstName;

    @NotNull(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be blank")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private AddressDto address;

    /**
     * @param role
     * constructor
     */
    public AbstractUserDto(Role role) {
        this.role = role;
    }


    /**
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param address
     * @param role
     * constructor
     */
    public AbstractUserDto(@NotNull(message = "Id cannot be blank") Long id,
                           @Size(max = 30, min = 1, message = "First name is in incorrect format.") @NotNull(message = "First name cannot be blank") String firstName,
                           @NotNull(message = "Last name cannot be blank") String lastName,
                           @Email(message = "Email should be valid") @NotNull(message = "Email cannot be blank") String email,
                           AddressDto address, Role role) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
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
     * @return get first name
     */
    public String getFirstName() {

        return firstName;
    }

    /**
     * @return get last name
     */
    public String getLastName() {

        return lastName;
    }

    /**
     * @return get email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     * set email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return get role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     * set role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return get address
     */
    public AddressDto getAddress() {
        return address;
    }

    /**
     * @param address
     * set address
     */
    public void setAddress(AddressDto address) {
        this.address = address;
    }
}

