package cz.cvut.fel.omo.scripts;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.humans.Repairman;
import cz.cvut.fel.omo.humans.Worker;
import cz.cvut.fel.omo.storages.MaterialStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Script 2 is meant to create 100 Skoda type cars and 10 Audi type cars. Ticks are not limited.
 * Two workers and two repairmen work here. Repairmen can help each other when they are not busy,
 * so sometimes it can take only two ticks to repair a machine. Reports are printed
 * except Event report as it would be too long.
 */
public class Script2 {

    public void play() {
        FactoryController controller = new FactoryController(9999, 10000, 20, 120);
        startFactory(controller);

        // print reports
        controller.printConsumption();
        controller.printConfiguration();
        controller.printOutages();
    }

    private void startFactory(FactoryController controller) {
        receiveMaterials(controller.getMaterialStorage());
        receiveMachines(controller);
        hireWorkers(controller);

        controller.chooseCarType(CarType.SKODA);
        controller.startProductionLine();

        controller.chooseCarType(CarType.AUDI);
        controller.startProductionLine();
    }

    private void hireWorkers(FactoryController controller) {
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("Alex Clark"));
        workers.add(new Worker("Lex Zoom"));

        List<Repairman> repairmen = new ArrayList<>();
        repairmen.add(new Repairman("Silver Ton"));
        repairmen.add(new Repairman("Josh Green"));

        controller.hireWorkers(workers, repairmen);
    }

    private void receiveMachines(FactoryController controller) {
        controller.receiveAssemblyElectronicsMachine();
        controller.receiveAssemblyEngineMachine();
        controller.receiveAssemblyEngineMachine();
        controller.receiveAssemblyHoodMachine();
        controller.receiveAssemblyWheelsMachine();
        controller.receiveDrillingMachine();
        controller.receivePaintingMachine();
        controller.receivePaintingMachine();
        controller.receiveWeldingMachine();
        controller.receiveWeldingMachine();
    }

    private void receiveMaterials(MaterialStorage materialStorage) {
        materialStorage.receiveMaterials(120, MaterialType.SKODA_ELECTRONICS);
        materialStorage.receiveMaterials(100, MaterialType.SKODA_ENGINE);
        materialStorage.receiveMaterials(200, MaterialType.SKODA_HOOD);
        materialStorage.receiveMaterials(450, MaterialType.SKODA_WHEEL);
        materialStorage.receiveMaterials(444, MaterialType.TIRE_18_IN);
        materialStorage.receiveMaterials(110, MaterialType.BLUE_PAINT);

        materialStorage.receiveMaterials(10, MaterialType.AUDI_ELECTRONICS);
        materialStorage.receiveMaterials(10, MaterialType.AUDI_ENGINE);
        materialStorage.receiveMaterials(10, MaterialType.AUDI_HOOD);
        materialStorage.receiveMaterials(42, MaterialType.AUDI_WHEEL);
        materialStorage.receiveMaterials(40, MaterialType.TIRE_20_IN);
        materialStorage.receiveMaterials(11, MaterialType.WHITE_PAINT);
    }
}
