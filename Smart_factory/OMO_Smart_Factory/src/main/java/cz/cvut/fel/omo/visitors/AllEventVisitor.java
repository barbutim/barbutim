package cz.cvut.fel.omo.visitors;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.factory.ProductionLine;
import cz.cvut.fel.omo.events.BrokeDownEvent;
import cz.cvut.fel.omo.events.ConsumptionEvent;
import cz.cvut.fel.omo.events.Event;
import cz.cvut.fel.omo.events.OutageEvent;
import cz.cvut.fel.omo.machines.Machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Visitor for EventReport.
 */
public class AllEventVisitor extends Visitor {

    private final HashMap<Machine, List<Event>> producerEvents = new HashMap<>();
    private final List<BrokeDownEvent> brokeDownEvents = new ArrayList<>();
    private final List<ConsumptionEvent> consumptionEvents = new ArrayList<>();
    private List<OutageEvent> outageEvents = new ArrayList<>();

    /**
     * Take events from the machine.
     * @param machine - machine to be visited
     */
    @Override
    public void visitMachine(Machine machine) {
        List<Event> newList = new ArrayList<>();
        newList.addAll(machine.getBrokeDownEvents());
        newList.addAll(machine.getConsumptionEvents());
        producerEvents.put(machine, newList);

        brokeDownEvents.addAll(machine.getBrokeDownEvents());
        consumptionEvents.addAll(machine.getConsumptionEvents());
    }

    @Override
    public void visitProductionLine(ProductionLine productionLine) {
        outageEvents = productionLine.getOutageEvents();
    }

    @Override
    public void visitFactory(FactoryController factory) {
        // Do nothing
    }

    @Override
    public String toString() {
        System.out.println("TYPE OF EVENT:");
        System.out.println("BROKE DOWN EVENTS:");
        for (Event event : brokeDownEvents) {
            System.out.println(event);
        }
        System.out.println("CONSUMPTION EVENTS:");
        for (Event event : consumptionEvents) {
            System.out.println(event);
        }
        System.out.println("OUTAGE EVENTS:");
        for (Event event : outageEvents) {
            System.out.println(event);
        }

        System.out.println("\nSOURCE OF EVENT:");
        for (HashMap.Entry<Machine, List<Event>> entry : producerEvents.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Event event : entry.getValue()) {
                System.out.println(event);
            }
            System.out.println();
        }

        if (!outageEvents.isEmpty()) {
            System.out.println("Production Line:");
            for (Event event : outageEvents) {
                System.out.println(event);
            }
        }
        return "";
    }
}
