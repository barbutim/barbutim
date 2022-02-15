package cz.cvut.fel.omo.reports;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.machines.Machine;
import cz.cvut.fel.omo.visitors.AllEventVisitor;

import java.util.List;

/**
 * Event report shows all the events that happened grouping them by the type of the event and by source.
 */
public class EventReport {

    public void printEventReport(FactoryController factory) {
        AllEventVisitor visitor = new AllEventVisitor();

        List<Machine> machines = factory.getProductionLine().getActiveMachines();

        for (Machine machine : machines) {
            machine.accept(visitor);
        }

        factory.getProductionLine().accept(visitor);

        System.out.println("\nEVENT REPORT:");
        System.out.println("------------------------------------------------");
        System.out.print(visitor);
        System.out.println("------------------------------------------------");
    }
}
