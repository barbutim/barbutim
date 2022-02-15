package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.factoryexceptions.MaterialMissing;

import java.util.logging.Level;

public class AssemblyHoodMachine extends AssemblyMachine {

    public AssemblyHoodMachine(Integer id) {
        super(id);
        type = MachineType.ASSEMBLY_HOOD;
    }

    @Override
    protected void processRequest(Car car) throws MaterialMissing {
        try {
            switch (car.getType()) {
                case AUDI:
                    car.setHood(storage.getMaterial(MaterialType.AUDI_HOOD));
                    break;
                case SKODA:
                    car.setHood(storage.getMaterial(MaterialType.SKODA_HOOD));
                    break;
                case MERCEDES:
                    car.setHood(storage.getMaterial(MaterialType.MERCEDES_HOOD));
                    break;
                case BULLET_TIME_CAR:
                    car.setHood(storage.getMaterial(MaterialType.BULLET_TIME_HOOD));
                    break;
            }
        } catch (MaterialMissing exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            throw exception;
        }
    }

    @Override
    public String toString() {
        return "AssemblyHoodMachine " + super.toString();
    }
}
