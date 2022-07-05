package cz.cvut.fel.nss.careerpages.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model for user - admin
 */
@Entity
@Table(name = "ADMIN")
public class Admin extends AbstractUser {
    /**
     * constructor
     */
    public Admin() {
        super(Role.ADMIN);
    }

    /**
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * constructor
     */
    public Admin(String password, String firstName, String lastName, String email) {
        super(password, firstName, lastName, email, Role.ADMIN);
    }
}
