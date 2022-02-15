package cz.cvut.fel.omo.visitors;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.factory.ProductionLine;
import cz.cvut.fel.omo.auxiliary.ConstantValues;
import cz.cvut.fel.omo.events.ConsumptionEvent;
import cz.cvut.fel.omo.machines.Machine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Visitor for ConsumptionReport.
 */
public class ConsumptionMachineVisitor extends Visitor {

    private Integer allConsumption = 0;
    private final List<ConsumptionEvent> consumptionEvents = new ArrayList<>();

    /**
     * Take events from the machine.
     * @param machine - machine to be visited
     */
    @Override
    public void visitMachine(Machine machine) {
        ConsumptionEvent newEvent = new ConsumptionEvent(null, 0, machine);

        List<ConsumptionEvent> machineConsumptionEvents = machine.getConsumptionEvents();
        for (ConsumptionEvent event : machineConsumptionEvents) {
            newEvent.setConsumption(newEvent.getConsumption() + event.getConsumption());
            allConsumption += event.getConsumption();
        }
        consumptionEvents.add(newEvent);
    }

    @Override
    public void visitProductionLine(ProductionLine productionLine) {
        // Do nothing
    }

    @Override
    public void visitFactory(FactoryController factory) {
        // Do nothing
    }

    @Override
    public String toString() {
        for (ConsumptionEvent event : consumptionEvents) {
            System.out.println(event);
        }
        DecimalFormat df = new DecimalFormat("#.#");
        return "\nProduction Line consumed " + allConsumption +
                "kWh in total (" + df.format(allConsumption*ConstantValues.PRICE_PER_KWH) + " Kč)";
    }
}
