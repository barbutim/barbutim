package cz.cvut.fel.omo.factoryexceptions;

/**
 * Machine needed in a sequence is not present.
 */
public class MachineMissing extends Exception {

    public MachineMissing(String message) {
        super(message);
    }
}
