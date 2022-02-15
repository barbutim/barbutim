package cz.cvut.fel.omo.visitors;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.factory.ProductionLine;
import cz.cvut.fel.omo.auxiliary.MachineComparator;
import cz.cvut.fel.omo.machines.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Inspector extends Visitor {

    private final Logger logger = Logger.getLogger("Inspectors's logger");

    /**
     * Inspector checks the machine.
     * @param machine - machine to be visited
     */
    @Override
    public void visitMachine(Machine machine) {
        logger.log(Level.INFO, "Inspector has checked " + machine.toString() + " with " +
                machine.getHealth() + " health.");
    }

    /**
     * Visitor sorts the machines based on health and visits them.
     * @param productionLine - production line to be visited
     */
    @Override
    public void visitProductionLine(ProductionLine productionLine) {
        List<Machine> machines = new ArrayList<>(productionLine.getActiveMachines());

        machines.sort(new MachineComparator());

        for (Machine machine : machines) {
            machine.accept(this);
        }
    }

    /**
     * Visitor visits its production line.
     * @param factory - factory to be visited
     */
    @Override
    public void visitFactory(FactoryController factory) {
        factory.getProductionLine().accept(this);
    }
}
