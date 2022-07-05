package cz.cvut.fel.nss.careerpages.dto;

import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import cz.cvut.fel.nss.careerpages.model.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Wrapper request
 */
public class RequestWrapper {

    private AbstractUser user;

    @Size(max = 255, min = 6, message = "Password control is in incorrect format.")
    @NotBlank(message = "Password control cannot be blank")
    private String password_control;

    /**
     * @param user
     * @param password_control
     * constructor
     */
    public RequestWrapper(AbstractUser user, String password_control) {
        this.user = user;
        this.password_control = password_control;
    }

    /**
     * constructor
     */
    public RequestWrapper() {

    }

    /**
     * @return get user
     */
    public AbstractUser getUser() {
        return (User) user;
    }

    /**
     * @return get password
     */
    public String getPassword_control() {
        return password_control;
    }
}
