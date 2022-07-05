package cz.cvut.fel.nss.careerpages.exception;

/**
 * Exception when user is already logged in his account
 */
public class AlreadyLoginException extends Exception {
    public AlreadyLoginException() {
        super("You are already login.");
    }
}
