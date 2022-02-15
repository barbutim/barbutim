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
 * Script 5 concerns a problem where an AssemblyEngine machine is not in storage and therefore
 * production can't start.
 */
public class Script5 {

    public void play() {
        FactoryController controller = new FactoryController(999, 500, 20, 100);
        startFactory(controller);

        // print reports
        controller.printConsumption();
        controller.printConfiguration();
        controller.printEvents();
        controller.printOutages();
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
