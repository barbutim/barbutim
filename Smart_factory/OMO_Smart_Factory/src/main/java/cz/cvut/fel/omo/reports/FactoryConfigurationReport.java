package cz.cvut.fel.omo.reports;

import cz.cvut.fel.omo.factory.FactoryController;

/**
 * Configuration report shows configuration of the factory with the state of machines, material storage
 * contents and car storage contents.
 */
public class FactoryConfigurationReport {

    public void printConfiguration(FactoryController factory) {
        System.out.println("\nCONFIGURATION REPORT:");
        System.out.println("------------------------------------------------");
        System.out.print(factory);
        System.out.println("------------------------------------------------");
    }
}
