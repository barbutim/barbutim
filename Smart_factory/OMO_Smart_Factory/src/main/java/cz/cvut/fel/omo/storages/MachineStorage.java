package cz.cvut.fel.omo.storages;

import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.factoryexceptions.MachineMissing;
import cz.cvut.fel.omo.machines.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Storage for machines.
 */
public class MachineStorage extends Storage {

    private final List<Machine> machines = new ArrayList<>();

    public MachineStorage(Integer storageCapacity) {
        super(storageCapacity);
    }

    /**
     * Add checks a storage capacity.
     * @param machine - machine to be added to the storage
     */
    public void add(Machine machine) {
        if (machines.size() < getStorageCapacity()) {
            machines.add(machine);
        } else {
            logger.log(Level.WARNING, "Machine " + machine + " had to be thrown away");
        }
    }

    public List<Machine> getMachines() {
        return machines;
    }

    /**
     * Returns machine if present.
     * @param type - machine to get
     * @return - needed machine
     * @throws MachineMissing - machine not present
     */
    public Machine getMachine(MachineType type) throws MachineMissing {
        Machine currentMachine;
        for (Machine machine : machines) {
            if (machine.getType().equals(type)) {
                currentMachine = machine;
                machines.remove(machine);
                return currentMachine;
            }
        }
        throw new MachineMissing(type.name() + " machine missing.");
    }

    @Override
    public String toString() {
        System.out.println("IN MACHINE STORAGE:");
        for (Machine machine : machines) {
            System.out.println(machine + " (" + machine.getState().toString() + ")");
        }
        return "";
    }
}
