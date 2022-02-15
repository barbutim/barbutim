package cz.cvut.fel.omo.visitors;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.factory.ProductionLine;
import cz.cvut.fel.omo.events.OutageEvent;
import cz.cvut.fel.omo.machines.Machine;
import javafx.util.Pair;

import java.util.List;

/**
 * Visitor for OutagesReport.
 */
public class OutageVisitor extends Visitor {

    Integer inTotal = 0;
    Pair<Integer, OutageEvent> shortest = null;
    Pair<Integer, OutageEvent> longest = null;
    Integer average = 0;

    @Override
    public void visitMachine(Machine machine) {
        // Do nothing
    }

    /**
     * Take outage events from production line.
     * @param productionLine - production line to be visited
     */
    @Override
    public void visitProductionLine(ProductionLine productionLine) {
        List<OutageEvent> outageEvents = productionLine.getOutageEvents();

        if (!outageEvents.isEmpty()) {
            OutageEvent first = outageEvents.get(0);

            shortest = new Pair<>(first.getTimeTaken(), first);
            longest = new Pair<>(first.getTimeTaken(), first);
            for (OutageEvent event : outageEvents) {
                average += event.getTimeTaken();
                if (shortest.getKey() > event.getTimeTaken()) {
                    shortest = new Pair<>(event.getTimeTaken(), event);
                }
                if (longest.getKey() < event.getTimeTaken()) {
                    longest = new Pair<>(event.getTimeTaken(), event);
                }
            }
            average /= outageEvents.size();
            inTotal += outageEvents.size();
        }
    }

    @Override
    public void visitFactory(FactoryController factory) {
        // Do nothing
    }

    @Override
    public String toString() {
        if (longest == null) {
            System.out.print("No outages happened.");
        } else {
            System.out.println("There were " + inTotal + " outages in total.");
            System.out.println("Longest outage: " + longest.getValue());
            System.out.println("Shortest outage: " + shortest.getValue());
            System.out.print("Average outage lasted " + average + " ticks.");
        }
        return "";
    }
}
