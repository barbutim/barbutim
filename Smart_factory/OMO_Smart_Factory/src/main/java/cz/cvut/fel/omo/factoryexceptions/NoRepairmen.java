package cz.cvut.fel.omo.factoryexceptions;

/**
 * There are no repairmen.
 */
public class NoRepairmen extends Exception {

    public NoRepairmen(String message) {
        super(message);
    }
}
