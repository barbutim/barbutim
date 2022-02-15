package cz.cvut.fel.omo.factory;

import cz.cvut.fel.omo.machines.AssemblyElectronicsMachine;
import cz.cvut.fel.omo.auxiliary.LineSequence;
import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.humans.Repairman;
import cz.cvut.fel.omo.humans.Worker;
import cz.cvut.fel.omo.machines.*;
import cz.cvut.fel.omo.reports.ConsumptionReport;
import cz.cvut.fel.omo.reports.EventReport;
import cz.cvut.fel.omo.reports.FactoryConfigurationReport;
import cz.cvut.fel.omo.reports.OutagesReport;
import cz.cvut.fel.omo.storages.CarStorage;
import cz.cvut.fel.omo.storages.MachineStorage;
import cz.cvut.fel.omo.storages.MaterialStorage;
import cz.cvut.fel.omo.visitors.Visitor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Our factory creating cars.
 */
public class FactoryController extends Visitable {

    private final FactoryConfigurationReport factoryConfigurationReport = new FactoryConfigurationReport();
    private final ConsumptionReport consumptionReport = new ConsumptionReport();
    private final EventReport eventReport = new EventReport();
    private final OutagesReport outagesReport = new OutagesReport();

    private final ProductionLine productionLine;
    private final LineSequence lineSequence;
    private final MaterialStorage materialStorage;
    private final MachineStorage machineStorage;
    private final CarStorage carStorage;
    private final Logger logger = Logger.getLogger("Factory logger");
    private Integer idCounter = 1;

    public FactoryController(Integer ticks, Integer materialStorageCapacity, Integer machineStorageCapacity,
                             Integer carStorageCapacity) {
        productionLine = new ProductionLine(ticks);
        lineSequence = new LineSequence();
        materialStorage = new MaterialStorage(materialStorageCapacity);
        machineStorage = new MachineStorage(machineStorageCapacity);
        carStorage = new CarStorage(carStorageCapacity);
    }

    /**
     * Assign workers and repairmen to a production line.
     * @param workers - workers to be assigned
     * @param repairmen - repairmen to be assigned
     */
    public void hireWorkers(List<Worker> workers, List<Repairman> repairmen) {
        productionLine.setWorkers(workers);
        productionLine.setRepairmen(repairmen);
    }

    /**
     * Decides the car type to create on the production line and checks if the line has enough necessary machines.
     * And sets the production line sequence of machines.
     * @param type - car to be created
     */
    public void chooseCarType(CarType type) {
        try {
            lineSequence.createSequence(type);
            productionLine.setProductionLine(type, machineStorage, lineSequence.getSequence(), materialStorage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Machines could not be set, because: " + e.getMessage());
        }
    }

    /**
     * Starts production line.
     */
    public void startProductionLine() {
        productionLine.launch(carStorage, materialStorage);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitFactory(this);
    }

    public void receiveAssemblyElectronicsMachine() {
        machineStorage.add(new AssemblyElectronicsMachine(idCounter++));
    }

    public void receiveAssemblyEngineMachine() {
        machineStorage.add(new AssemblyEngineMachine(idCounter++));
    }

    public void receiveAssemblyHoodMachine() {
        machineStorage.add(new AssemblyHoodMachine(idCounter++));
    }

    public void receiveAssemblyWheelsMachine() {
        machineStorage.add(new AssemblyWheelsMachine(idCounter++));
    }

    public void receiveDrillingMachine() {
        machineStorage.add(new DrillingMachine(idCounter++));
    }

    public void receivePaintingMachine() {
        machineStorage.add(new PaintingMachine(idCounter++));
    }

    public void receiveWeldingMachine() {
        machineStorage.add(new WeldingMachine(idCounter++));
    }

    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public MaterialStorage getMaterialStorage() {
        return materialStorage;
    }

    public void printConsumption () {
        consumptionReport.printConsumption(this);
    }

    public void printConfiguration() {
        factoryConfigurationReport.printConfiguration(this);
    }

    public void printEvents() {
        eventReport.printEventReport(this);
    }

    public void printOutages() {
        outagesReport.printOutagesReport(this);
    }

    @Override
    public String toString() {
        System.out.println("FACTORY:");
        System.out.println("Factory\n");
        System.out.println(productionLine);
        System.out.println(machineStorage);
        System.out.println(materialStorage);
        System.out.print(carStorage);
        return "";
    }
}
