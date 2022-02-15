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
 * In Script 4 material storage is limited only to 400 pieces of components, but there are
 * stored many more. This results into missing material for creating even one car and the production
 * can't be started.
 */
public class Script4 {

    public void play() {
        FactoryController controller = new FactoryController(9999, 400, 20, 100);
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
        workers.add(new Worker("Dex Rotom"));

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
        materialStorage.receiveMaterials(220, MaterialType.AUDI_ELECTRONICS);
        materialStorage.receiveMaterials(190, MaterialType.AUDI_ENGINE);
        materialStorage.receiveMaterials(150, MaterialType.AUDI_HOOD);
        materialStorage.receiveMaterials(210, MaterialType.AUDI_WHEEL);
        materialStorage.receiveMaterials(220, MaterialType.TIRE_20_IN);
        materialStorage.receiveMaterials(200, MaterialType.WHITE_PAINT);
    }
}