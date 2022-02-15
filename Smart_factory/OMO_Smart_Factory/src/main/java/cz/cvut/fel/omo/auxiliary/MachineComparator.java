package cz.cvut.fel.omo.auxiliary;

import cz.cvut.fel.omo.machines.Machine;

import java.util.Comparator;

/**
 * Comparator for Inspector to sort machines based on remaining health.
 */
public class MachineComparator implements Comparator<Machine> {
    public int compare(Machine a, Machine b)
    {
        return a.getHealth() - b.getHealth();
    }
}
