package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.factoryexceptions.MaterialMissing;

import java.util.logging.Level;

public class AssemblyEngineMachine extends AssemblyMachine {

    public AssemblyEngineMachine(Integer id) {
        super(id);
        type = MachineType.ASSEMBLY_ENGINE;
    }

    @Override
    protected void processRequest(Car car) throws MaterialMissing {
        try {
            switch (car.getType()) {
                case AUDI:
                    car.setEngine(storage.getMaterial(MaterialType.AUDI_ENGINE));
                    break;
                case SKODA:
                    car.setEngine(storage.getMaterial(MaterialType.SKODA_ENGINE));
                    break;
                case MERCEDES:
                    car.setEngine(storage.getMaterial(MaterialType.MERCEDES_ENGINE));
                    break;
                case BULLET_TIME_CAR:
                    car.setEngine(storage.getMaterial(MaterialType.BLUE_PAINT));
                    break;
            }
        } catch (MaterialMissing exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            throw exception;
        }
    }

    @Override
    public String toString() {
        return "AssemblyEngineMachine " + super.toString();
    }
}
