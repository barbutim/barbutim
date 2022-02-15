package cz.cvut.fel.omo.visitors;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.factory.ProductionLine;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.humans.Worker;
import cz.cvut.fel.omo.machines.AssemblyElectronicsMachine;
import cz.cvut.fel.omo.machines.Machine;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager extends Visitor {

    private final Logger logger = Logger.getLogger("Manager's logger");

    /**
     * Manager visits a machine and workers.
     * @param machine - machine to be visited
     */
    @Override
    public void visitMachine(Machine machine) {
        logger.log(Level.INFO, "Manager has visited " + machine.toString());
        if (machine.getType() == MachineType.ASSEMBLY_ELECTRONICS) {
            Worker worker = ((AssemblyElectronicsMachine) machine).getWorker();
            if (worker != null) {
                logger.log(Level.INFO, "Manager shook hands with " + worker + ".");
            }
        }
    }

    /**
     * Manager visits a production line.
     * @param productionLine - production line to be visited
     */
    @Override
    public void visitProductionLine(ProductionLine productionLine) {
        logger.log(Level.INFO, "Manager has visited Production line");
        List<Machine> machines = productionLine.getActiveMachines();
        for (Machine machine : machines) {
            machine.accept(this);
        }
    }

    /**
     * Manager visits factory.
     * @param factory - factory to be visited
     */
    @Override
    public void visitFactory(FactoryController factory) {
        logger.log(Level.INFO, "Manager has visited Factory");
        factory.getProductionLine().accept(this);
    }
}
