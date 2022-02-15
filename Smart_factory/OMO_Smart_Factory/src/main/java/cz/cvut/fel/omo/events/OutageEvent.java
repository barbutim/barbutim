package cz.cvut.fel.omo.events;

/**
 * Event for a happening outage.
 */
public class OutageEvent extends Event {
    private final Integer id;
    private final Integer StartTick;
    private Integer EndTick;

    public OutageEvent(Integer id, Integer startTick, String message) {
        super(message);
        this.id = id;
        StartTick = startTick;
    }

    public Integer getId() {
        return id;
    }

    public Integer getEndTick() {
        return EndTick;
    }

    public void setEndTick(Integer endTick) {
        EndTick = endTick;
    }

    public Integer getStartTick() {
        return StartTick;
    }

    /**
     * @return - How long the outage took.
     */
    public Integer getTimeTaken() {
        if (EndTick == null) {
            return 0;
        }
        return EndTick-StartTick;
    }

    @Override
    public String toString() {
        return "Outage lasting for " + getTimeTaken() + " ticks - " + getMessage();
    }
}
