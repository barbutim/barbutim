package cz.cvut.fel.omo.humans;

import cz.cvut.fel.omo.machines.Machine;

/**
 * Repairman repairing machines at a certain production line.
 */
public class Repairman extends Human {

    Machine machine;

    public Repairman(String name) {
        super(name);
    }

    /**
     * Repairman is currently not assigned to any machine.
     * @return - true if remairman is free, false otherwise
     */
    public boolean free() {
        return machine == null;
    }

    /**
     * Repair an assigned machine.
     */
    public void repair() {
        if (machine != null) {
            if (machine.repair()) {
                machine = null;
            }
        }
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
