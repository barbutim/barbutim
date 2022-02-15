package cz.cvut.fel.omo.scripts;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.humans.Repairman;
import cz.cvut.fel.omo.humans.Worker;
import cz.cvut.fel.omo.storages.MaterialStorage;

import java.util.ArrayList;
import java.util.List;

public class Script3  {

    /**
     * In Script 3 machine breaks but there are no repairmen to repair them, so the production
     * has to be stopped.
     */
    public void play() {
        FactoryController controller = new FactoryController(9999, 1000, 50, 100);
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

        controller.chooseCarType(CarType.AUDI);
        controller.startProductionLine();
    }

    private void hireWorkers(FactoryController controller) {
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("John Doe"));
        workers.add(new Worker("Dex Rotom"));

        List<Repairman> repairmen = new ArrayList<>();

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
        materialStorage.receiveMaterials(50, MaterialType.AUDI_ELECTRONICS);
        materialStorage.receiveMaterials(40, MaterialType.AUDI_ENGINE);
        materialStorage.receiveMaterials(222, MaterialType.AUDI_HOOD);
        materialStorage.receiveMaterials(254, MaterialType.AUDI_WHEEL);
        materialStorage.receiveMaterials(55, MaterialType.TIRE_20_IN);
        materialStorage.receiveMaterials(20, MaterialType.WHITE_PAINT);
    }
}
