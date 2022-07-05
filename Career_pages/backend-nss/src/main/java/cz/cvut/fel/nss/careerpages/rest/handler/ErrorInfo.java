package cz.cvut.fel.nss.careerpages.rest.handler;

/**
 * Contains information about an error and can be send to client as JSON to let them know what went wrong.
 */
public class ErrorInfo {

    private String message;

    private String requestUri;

    /**
     * Constructor
     */
    public ErrorInfo() {
    }

    /**
     * @param message
     * @param requestUri
     * Constructor
     */
    public ErrorInfo(String message, String requestUri) {
        this.message = message;
        this.requestUri = requestUri;
    }

    /**
     * @return get message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     * set message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ErrorInfo{" + requestUri + ", message = " + message + "}";
    }
}
