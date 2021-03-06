package cz.cvut.fel.nss.careerpages.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Model for abstract user
 */
@Entity
@Table(name = "ABSTRACT_USER")
@NamedQueries({
        @NamedQuery(name = "AbstractUser.findByEmail", query = "SELECT u FROM AbstractUser u WHERE u.email = :email AND u.deleted_at is null")
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractUser extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    @Size(max = 30, min = 1, message = "First name is in incorrect format.")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 30, min = 1, message = "Last name is in incorrect format.")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 255, min = 6, message = "Password is in incorrect format.")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Email(message = "Email should be valid")
    @Basic(optional = false)
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    /**
     * @param role
     * constructor
     */
    public AbstractUser(Role role) {
        this.role = role;
    }

    /**
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param role
     * constructor
     */
    public AbstractUser(String password, String firstName, String lastName, String email, Role role){
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    /**
     * @param email
     * @param password
     * constructor
     */
    public AbstractUser(@Email(message = "Email should be valid") String email,
                        @Size(max = 255, min = 6, message = "Password is in incorrect format.") String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * constructor
     */
    public AbstractUser() {

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
     * @return get first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     * set first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return get last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     * set last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param email
     * set email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return get password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     * set password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * erase password
     */
    public void erasePassword() {
        this.password = null;
    }

    /**
     * @return get email
     */
    public String getEmail() {
        return email;
    }

    /**
     * encode password
     */
    public void encodePassword() {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    /**
     * @param address
     * set address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return get address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", address=" + address +
                '}';
    }
}
