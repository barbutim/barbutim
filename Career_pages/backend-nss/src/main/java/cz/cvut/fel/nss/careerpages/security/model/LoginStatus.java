package cz.cvut.fel.nss.careerpages.security.model;

/**
 * Security login status
 */
public class LoginStatus {

    private boolean loggedIn;
    private String username;
    private String errorMessage;
    private boolean success;

    /**
     * constructor
     */
    public LoginStatus() {
    }

    /**
     * @param loggedIn
     * @param success
     * @param username
     * @param errorMessage
     * constructor
     */
    public LoginStatus(boolean loggedIn, boolean success, String username, String errorMessage) {
        this.loggedIn = loggedIn;
        this.username = username;
        this.errorMessage = errorMessage;
        this.success = success;
    }
}
