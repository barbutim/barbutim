package cz.cvut.fel.nss.careerpages.exception;

/**
 * Exception for not allowed operation
 */
public class NotAllowedException extends Exception{
    public NotAllowedException() {
        super("Forbidden operation.");
    }
    public NotAllowedException(String message) {
        super(message);
    }
}
