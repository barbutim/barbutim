package cz.cvut.fel.nss.careerpages.exception;

/**
 * Exception for missing variables
 */
public class MissingVariableException extends Exception {
    public MissingVariableException() {
        super("missing variable");
    }
}
