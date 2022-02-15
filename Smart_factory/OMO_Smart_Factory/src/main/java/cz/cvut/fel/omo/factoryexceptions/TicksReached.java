package cz.cvut.fel.omo.factoryexceptions;

/**
 * Final tick was reached.
 */
public class TicksReached extends Exception {

    public TicksReached(String message) {
        super(message);
    }
}
