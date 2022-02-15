package cz.cvut.fel.omo.reports;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.machines.Machine;
import cz.cvut.fel.omo.visitors.ConsumptionMachineVisitor;

import java.util.List;

/**
 * Consumption report with electricity consumption and prices and overall consumption.
 */
public class ConsumptionReport {

    public void printConsumption(FactoryController factory) {
        ConsumptionMachineVisitor visitor = new ConsumptionMachineVisitor();

        List<Machine> machines = factory.getProductionLine().getActiveMachines();

        for (Machine machine : machines) {
            machine.accept(visitor);
        }

        System.out.println("\nCONSUMPTION REPORT:");
        System.out.println("------------------------------------------------");
        System.out.println(visitor);
        System.out.println("------------------------------------------------");
    }
}
