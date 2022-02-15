package cz.cvut.fel.omo.events;

import cz.cvut.fel.omo.machines.Machine;

/**
 * Event of a machine.
 */
public abstract class MachineEvent extends Event {

    private final Machine machine;

    protected MachineEvent(Machine machine) {
        this.machine = machine;
    }

    @Override
    public String toString() {
        return machine.toString();
    }
}
