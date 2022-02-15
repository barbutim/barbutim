package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.factoryexceptions.MaterialMissing;
import cz.cvut.fel.omo.humans.Worker;

import java.util.logging.Level;

/**
 * Machine with a worker. Worker needs to be assigned to this machine in order to operate. Worker gets exhausted.
 */
public class AssemblyElectronicsMachine extends AssemblyMachine {

    private Worker worker;

    public AssemblyElectronicsMachine(Integer id) {
        super(id);
        type = MachineType.ASSEMBLY_ELECTRONICS;
    }

    @Override
    protected void processRequest(Car car) throws MaterialMissing {
        try {
            switch (car.getType()) {
                case AUDI:
                    car.setElectronics(storage.getMaterial(MaterialType.AUDI_ELECTRONICS));
                    break;
                case SKODA:
                    car.setElectronics(storage.getMaterial(MaterialType.SKODA_ELECTRONICS));
                    break;
                case MERCEDES:
                    car.setElectronics(storage.getMaterial(MaterialType.MERCEDES_ELECTRONICS));
                    break;
                case BULLET_TIME_CAR:
                    car.setElectronics(storage.getMaterial(MaterialType.BULLET_TIME_ELECTRONICS));
                    break;
            }
        } catch (MaterialMissing exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            throw exception;
        }
        worker.work();
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String toString() {
        if (worker != null) {
            return "AssemblyElectronicsMachine " + super.toString() + " (with worker " + worker + ")";
        } else {
            return "AssemblyElectronicsMachine " + super.toString();
        }
    }
}
