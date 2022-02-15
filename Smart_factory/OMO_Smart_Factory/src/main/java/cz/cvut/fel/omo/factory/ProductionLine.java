package cz.cvut.fel.omo.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.events.OutageEvent;
import cz.cvut.fel.omo.factoryexceptions.*;
import cz.cvut.fel.omo.humans.Repairman;
import cz.cvut.fel.omo.humans.Worker;
import cz.cvut.fel.omo.machines.AssemblyElectronicsMachine;
import cz.cvut.fel.omo.machines.Machine;
import cz.cvut.fel.omo.storages.*;
import cz.cvut.fel.omo.visitors.Visitor;

/**
 * Production where cars are assembled, machines are set, workers are assigned to machines, changed, and they
 * work and rest. Repairmen repair machines.
 */
public class ProductionLine extends Visitable {

    private final List<OutageEvent> outageEvents = new ArrayList<>();
    private List<Machine> activeMachines = new ArrayList<>();
    private List<Worker> workers;
    private final List<Worker> activeWorkers = new ArrayList<>();
    private List<Repairman> repairmen;
    private CarType carType = null;
    private Integer currentTick = 0;
    private final Integer finishingTick;
    private Integer idCounter = 1;
    private final Logger logger = Logger.getLogger("Production Line logger");

    public ProductionLine(Integer finishingTick) {
        this.finishingTick = finishingTick;
    }

    /**
     * Sets machines in order based on sequence. Assigns workers to needed machines. Puts back unneeded machines.
     * @param carType - car to be created
     * @param machineStorage - machines to take and return machines to
     * @param sequence - sequence of machines
     * @param materialStorage - material storage to take material from
     * @throws MachineMissing - machine needed in a sequence is not present
     */
    public void setProductionLine(CarType carType, MachineStorage machineStorage, List<MachineType> sequence,
                                  MaterialStorage materialStorage) throws MachineMissing {
        this.carType = carType;

        // put active machines and workers back
        putBack(machineStorage);

        try {
            // create new production line
            for (MachineType type : sequence) {
                // set workers if needed
                if (type == MachineType.ASSEMBLY_ELECTRONICS) {
                    if (workers.size() > 0) {
                        Machine machine = machineStorage.getMachine(type);
                        Worker worker = workers.get(0);
                        workers.remove(worker);
                        activeWorkers.add(worker);
                        ((AssemblyElectronicsMachine) machine).setWorker(worker);
                        activeMachines.add(machine);
                    } else {
                        logger.log(Level.SEVERE, "Workers not available.");
                        putBack(machineStorage);
                        return;
                    }
                } else {
                    activeMachines.add(machineStorage.getMachine(type));
                }
            }
        } catch (MachineMissing exception) {
            activeMachines = new ArrayList<>();
            throw exception;
        }

        // set material storage
        for (Machine machine : activeMachines) {
            machine.setStorage(materialStorage);
        }

        logger.log(Level.FINE, "New production line set.");
    }

    /**
     * Puts back all the active machines and workers.
     * @param machineStorage - storage to put back machines
     */
    private void putBack(MachineStorage machineStorage) {
        // put active machines and workers back
        if (!activeMachines.isEmpty()) {
            for (Machine machine : activeMachines) {
                if(machine.getType() == MachineType.ASSEMBLY_ELECTRONICS) {
                    Worker worker = ((AssemblyElectronicsMachine) machine).getWorker();
                    workers.add(worker);
                    activeWorkers.remove(worker);
                    ((AssemblyElectronicsMachine) machine).setWorker(null);
                }
                machineStorage.add(machine);
            }
        }
        activeMachines = new ArrayList<>();
    }

    /**
     * Launch the production line. Creates cars using machines. Checks if machines break and repairs them.
     * Checks if workers are tired and if so, changes them. Puts cars to car storage.
     * @param carStorage - where to put cars
     * @param materialStorage - material storage of machines. To check material availability.
     */
    public void launch(CarStorage carStorage, MaterialStorage materialStorage) {
        if (activeMachines.isEmpty()) {
            logger.log(Level.SEVERE, "Factory can't start, because machines were not set for production.");
            return;
        }
        if (currentTick.equals(finishingTick)) {
            logger.log(Level.INFO, "Tick " + currentTick + " quitting production.");
            return;
        }

        Car car = null;
        try {

            while (materialStorage.checkMaterialAvailability(carType)) {
                if (machinesBroken()) {
                    repairMachines();
                }
                if (workersTired()) {
                    changeWorkers();
                }

                car = new Car(carType);

                for (Machine machine : activeMachines) {
                    machine.use(car, currentTick);
                    tick();
                }

                carStorage.add(car);
            }

        } catch (TicksReached e) {
            carStorage.add(car);
            endOfOutage();
            logger.log(Level.INFO, e.getMessage());
        } catch (NoRepairmen e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (MaterialMissing exception) {
            System.out.println(exception.getMessage());
        }

        logger.log(Level.INFO, "Tick " + currentTick + " quitting production.");
    }

    /**
     * Repairs broken machines. Repairs all the machines until none is broken.
     * @throws NoRepairmen - the are no repairmen
     * @throws TicksReached - ticks were reached
     */
    private void repairMachines() throws NoRepairmen, TicksReached {
        if (repairmen.isEmpty()) {
            throw new NoRepairmen("There are no repairmen and a machine is broken.");
        }
        outageEvents.add(new OutageEvent(idCounter++, currentTick, "Waiting for a machine repair."));

        while (machinesBroken()) {
            for (Machine machine : activeMachines) {
                if (machine.isBroken()) {
                    for (Repairman repairman : repairmen) {
                        if (repairman.free()) {
                            repairman.setMachine(machine);
                        }
                    }
                }
            }
            for (Repairman repairman : repairmen) {
                repairman.repair();
            }
            tick();
        }
        endOfOutage();
    }

    /**
     * Boolean method to check if machines are broken.
     * @return - true if there are broken machines, false otherwise
     */
    private boolean machinesBroken() {
        for (Machine machine : activeMachines) {
            if (machine.isBroken()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Change workers that are assigned to machines.
     * @throws TicksReached - ticks were reached
     */
    private void changeWorkers() throws TicksReached {
        // may not turn into outage, workers might be available right away
        boolean once = true;
        outageEvents.add(new OutageEvent(idCounter++, currentTick, "Waiting for available workers."));
        while(!workersAvailable()) {
            if (once) {
                logger.log(Level.WARNING, "No workers available, outage happening.");
                once = false;
            }
            tick();
        }
        endOfOutage();
    }

    /**
     * Outage ended, set the ending tick.
     */
    private void endOfOutage() {
        if (outageEvents.isEmpty()) {
            return;
        }
        OutageEvent currentEvent = outageEvents.get(0);
        for (OutageEvent event : outageEvents) {
            if (currentEvent.getId() < event.getId()) {
                currentEvent = event;
            }
        }

        if (currentEvent.getStartTick().equals(currentTick)) {  // no ticks passed
            outageEvents.remove(currentEvent);
        } else if (currentEvent.getEndTick() == null){
            currentEvent.setEndTick(currentTick);
        }
    }

    /**
     * Boolean method to check if there is any tired worker.
     * @return - true if there is a tired worker, false otherwise
     */
    private boolean workersTired() {
        for (Worker worker : activeWorkers) {
            if (worker.isTired()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the workers in active machines.
     * @return - true if all workers were changed, false otherwise
     */
    private boolean workersAvailable() {
        boolean workersAvailable = true;

        // remove tired workers
        for (Machine machine : activeMachines) {
            if (machine.getType() == MachineType.ASSEMBLY_ELECTRONICS) {
                Worker worker = ((AssemblyElectronicsMachine) machine).getWorker();
                if (worker != null) {
                    if (worker.isTired()) {
                        workers.add(worker);
                        activeWorkers.remove(worker);
                        ((AssemblyElectronicsMachine) machine).setWorker(null);
                    }
                }
            }
        }

        // assign rested workers
        for (Machine machine : activeMachines) {
            if (machine.getType() == MachineType.ASSEMBLY_ELECTRONICS) {
                if (((AssemblyElectronicsMachine) machine).getWorker() == null) {
                    Worker worker = getRestedWorker();
                    if (worker != null) {
                        workers.remove(worker);
                        activeWorkers.add(worker);
                        ((AssemblyElectronicsMachine) machine).setWorker(worker);
                    } else {
                        workersAvailable = false;
                    }
                }
            }
        }
        return workersAvailable;
    }

    private Worker getRestedWorker() {
        for (Worker worker: workers) {
            if (worker.isRested()) {
                return worker;
            }
        }
        return null;
    }


    private void tick() throws TicksReached {
        currentTick++;
        if (finishingTick != null) {
            if (finishingTick.equals(currentTick)) {
                throw new TicksReached("Reached " + finishingTick + " ticks.");
            }
        }
        restAll(workers);
    }

    private void restAll(List<Worker> workers) {
        for (Worker worker : workers) {
            worker.rest();
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitProductionLine(this);
    }

    public List<Machine> getActiveMachines() {
        return activeMachines;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public void setRepairmen(List<Repairman> repairmen) {
        this.repairmen = repairmen;
    }

    public List<OutageEvent> getOutageEvents() {
        return outageEvents;
    }

    @Override
    public String toString() {
        System.out.println("PRODUCTION LINE:\nLine\n");
        System.out.println("ACTIVE MACHINES:");
        for (Machine machine : activeMachines) {
            System.out.println(machine + " (" + machine.getState().name() + ")");
        }
        return "";
    }
}
