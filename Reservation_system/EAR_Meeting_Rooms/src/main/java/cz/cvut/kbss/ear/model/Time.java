package cz.cvut.kbss.ear.model;

public enum Time {
    MORNING("TIME_MORNING"), AFTERNOON("TIME_AFTERNOON"), ALLDAY("TIME_ALLDAY");

    private final String time;

    Time(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return time;
    }
}
