package cz.cvut.fel.nss.careerpages.model;

/**
 * Enum for application states - active, cancelled, finished
 */
public enum ApplicationState {
    ACTIVE("ACTIVE"), CANCELED("CANCELED"), FINISHED("FINISHED");

    private final String enrollmentState;

    /**
     * @param enrollmentState
     * constructor
     */
    ApplicationState(String enrollmentState) {
        this.enrollmentState = enrollmentState;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return enrollmentState;}
}
