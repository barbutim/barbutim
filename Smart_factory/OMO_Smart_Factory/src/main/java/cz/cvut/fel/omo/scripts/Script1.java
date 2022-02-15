package cz.cvut.fel.omo.scripts;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.humans.Repairman;
import cz.cvut.fel.omo.humans.Worker;
import cz.cvut.fel.omo.storages.MaterialStorage;
import cz.cvut.fel.omo.visitors.Inspector;
import cz.cvut.fel.omo.visitors.Manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Script 1 is meant to create 25 Audi type cars. But it's meant to stop after 150 ticks, so not all the cars
 * are produced yet. Some material is left in the material storage. One worker is assigned to work and there is
 * only one repairman. One worker needs 16 ticks to rest. It takes 3 ticks to repair a machine with one repairman.
 * Script 1 prints all the available Reports:
 *      - Consumption report with electricity consumption and prices and overall consumption.
 *      - Configuration report showing configuration of the factory with the state of machines, material storage
 *      contents and car storage contents.
 *      - Event report showing all the events that happened grouping them by the type of the event and by source.
 *      - Outages report showing the number of outages, longest and shortest outage and average time of outages.
 * Script 1 also makes the Manager visit the factory and Inspector check machines based on their health.
 */
public class Script1 {

    public void play() {
        FactoryController controller = new FactoryController(150, 500, 20, 100);
        startFactory(controller);

        // print reports
        controller.printConsumption();
        controller.printConfiguration();
        controller.printEvents();
        controller.printOutages();

        // get manager
        Manager manager = new Manager();
        controller.accept(manager);

        // get inspector
        Inspector inspector = new Inspector();
        controller.accept(inspector);
    }

    private void startFactory(FactoryController controller) {
        receiveMaterials(controller.getMaterialStorage());
        receiveMachines(controller);
        hireWorkers(controller);

        controller.chooseCarType(CarType.AUDI);
        controller.startProductionLine();
    }

    private void hireWorkers(FactoryController controller) {
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("John Doe"));

        List<Repairman> repairmen = new ArrayList<>();
        repairmen.add(new Repairman("Adam Gold"));

        controller.hireWorkers(workers, repairmen);
    }

    private void receiveMachines(FactoryController controller) {
        controller.receiveAssemblyElectronicsMachine();
        controller.receiveAssemblyEngineMachine();
        controller.receiveAssemblyHoodMachine();
        controller.receiveAssemblyHoodMachine();
        controller.receiveAssemblyWheelsMachine();
        controller.receiveDrillingMachine();
        controller.receiveDrillingMachine();
        controller.receivePaintingMachine();
        controller.receiveWeldingMachine();
    }

    private void receiveMaterials(MaterialStorage materialStorage) {
        materialStorage.receiveMaterials(25, MaterialType.AUDI_ELECTRONICS);
        materialStorage.receiveMaterials(25, MaterialType.AUDI_ENGINE);
        materialStorage.receiveMaterials(25, MaterialType.AUDI_HOOD);
        materialStorage.receiveMaterials(110, MaterialType.AUDI_WHEEL);
        materialStorage.receiveMaterials(100, MaterialType.TIRE_20_IN);
        materialStorage.receiveMaterials(30, MaterialType.WHITE_PAINT);
    }
}
