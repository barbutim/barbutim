package cz.cvut.fel.omo.scripts;

import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.humans.Repairman;
import cz.cvut.fel.omo.humans.Worker;
import cz.cvut.fel.omo.storages.MaterialStorage;
import cz.cvut.fel.omo.visitors.Inspector;
import cz.cvut.fel.omo.visitors.Manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Endurance test - creating 1000 Mercedes cars. Buying 18 machines. Buying over 16000 material pieces.
 * Hiring 7 workers and 5 repairmen.
 */
public class Script6 {

    public void play() {
        FactoryController controller = new FactoryController(99999, 20000, 50, 10000);
        startFactory(controller);

        // print reports
        controller.printConsumption();
        controller.printConfiguration();
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

        controller.chooseCarType(CarType.MERCEDES);
        controller.startProductionLine();
    }

    private void hireWorkers(FactoryController controller) {
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("John Doe1"));
        workers.add(new Worker("John Doe2"));
        workers.add(new Worker("John Doe3"));
        workers.add(new Worker("John Doe4"));
        workers.add(new Worker("John Doe5"));
        workers.add(new Worker("John Doe6"));
        workers.add(new Worker("John Doe7"));

        List<Repairman> repairmen = new ArrayList<>();
        repairmen.add(new Repairman("Adam Gold1"));
        repairmen.add(new Repairman("Adam Gold2"));
        repairmen.add(new Repairman("Adam Gold3"));
        repairmen.add(new Repairman("Adam Gold4"));
        repairmen.add(new Repairman("Adam Gold5"));

        controller.hireWorkers(workers, repairmen);
    }

    private void receiveMachines(FactoryController controller) {
        controller.receiveAssemblyElectronicsMachine();
        controller.receiveAssemblyElectronicsMachine();
        controller.receiveAssemblyElectronicsMachine();
        controller.receiveAssemblyEngineMachine();
        controller.receiveAssemblyEngineMachine();
        controller.receiveAssemblyEngineMachine();
        controller.receiveAssemblyHoodMachine();
        controller.receiveAssemblyHoodMachine();
        controller.receiveAssemblyHoodMachine();
        controller.receiveAssemblyWheelsMachine();
        controller.receiveAssemblyWheelsMachine();
        controller.receiveAssemblyWheelsMachine();
        controller.receiveDrillingMachine();
        controller.receiveDrillingMachine();
        controller.receiveDrillingMachine();
        controller.receivePaintingMachine();
        controller.receivePaintingMachine();
        controller.receivePaintingMachine();
        controller.receiveWeldingMachine();
    }

    private void receiveMaterials(MaterialStorage materialStorage) {
        materialStorage.receiveMaterials(1100, MaterialType.MERCEDES_ELECTRONICS);
        materialStorage.receiveMaterials(2000, MaterialType.MERCEDES_ENGINE);
        materialStorage.receiveMaterials(1000, MaterialType.MERCEDES_HOOD);
        materialStorage.receiveMaterials(4100, MaterialType.MERCEDES_WHEEL);
        materialStorage.receiveMaterials(4500, MaterialType.TIRE_22_IN);
        materialStorage.receiveMaterials(2532, MaterialType.BLACK_PAINT);
    }
}