package cz.cvut.fel.omo.events;

/**
 * Event happening in the factory.
 */
public abstract class Event {

    private String message;

    protected Event() {
    }

    protected Event(String message) {
        this.message = message;
    }

    protected String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }
}
