package cz.cvut.fel.nss.careerpages.security;

/**
 * constants needed for security
 */
public class SecurityConstants {

    /**
     * constructor
     */
    private SecurityConstants() {
        throw new AssertionError();
    }

    public static final String SESSION_COOKIE_NAME = "EAR_JSESSIONID";

    public static final String REMEMBER_ME_COOKIE_NAME = "remember-me";

    public static final String USERNAME_PARAM = "email";

    public static final String PASSWORD_PARAM = "password";

    public static final String SECURITY_CHECK_URI = "/j_spring_security_check";

    public static final String LOGOUT_URI = "/logout";

    public static final int SESSION_TIMEOUT = 300;

    public static final String FRONTEND = "https://frontend-nss.herokuapp.com";
}
