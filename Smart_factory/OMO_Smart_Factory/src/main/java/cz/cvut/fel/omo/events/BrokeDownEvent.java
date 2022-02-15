package cz.cvut.fel.omo.events;

import cz.cvut.fel.omo.machines.Machine;

/**
 * Event of a broken machine.
 */
public class BrokeDownEvent extends MachineEvent {

    private final Integer tick;

    public BrokeDownEvent( Integer tick, Machine machine) {
        super(machine);
        this.tick = tick;
    }

    @Override
    public String toString() {
        return "On " + tick + " tick " + super.toString() + " broke down.";
    }
}
