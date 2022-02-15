package cz.cvut.fel.omo.reports;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.visitors.OutageVisitor;

/**
 * Outages report shows the number of outages, longest and shortest outage and average time of outages.
 */
public class OutagesReport {

    public void printOutagesReport(FactoryController factory) {
        OutageVisitor visitor = new OutageVisitor();

        factory.getProductionLine().accept(visitor);

        System.out.println("\nOUTAGES REPORT:");
        System.out.println("------------------------------------------------");
        System.out.println(visitor);
        System.out.println("------------------------------------------------");
    }
}
