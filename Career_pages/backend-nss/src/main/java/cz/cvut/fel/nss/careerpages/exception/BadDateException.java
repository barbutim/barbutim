package cz.cvut.fel.nss.careerpages.exception;

/**
 * Exception for wrong date
 */
public class BadDateException extends Exception {

    public BadDateException() {
        super("Incorrect date.");
    }

    public BadDateException(String message) {
        super(message);
    }


}
