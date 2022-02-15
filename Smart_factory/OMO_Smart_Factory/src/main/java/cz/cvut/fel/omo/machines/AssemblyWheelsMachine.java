package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.factoryexceptions.MaterialMissing;
import javafx.util.Pair;

import java.util.logging.Level;

public class AssemblyWheelsMachine extends AssemblyMachine {

    public AssemblyWheelsMachine(Integer id) {
        super(id);
        type = MachineType.ASSEMBLY_WHEELS;
    }

    @Override
    protected void processRequest(Car car) throws MaterialMissing {
        try {
            switch (car.getType()) {
                case AUDI:
                    car.setWheels(new Pair<>(storage.getMaterial(MaterialType.AUDI_WHEEL, 4), 4));
                    car.setTires(new Pair<>(storage.getMaterial(MaterialType.TIRE_20_IN, 4), 4));
                    break;
                case SKODA:
                    car.setWheels(new Pair<>(storage.getMaterial(MaterialType.SKODA_WHEEL, 4), 4));
                    car.setTires(new Pair<>(storage.getMaterial(MaterialType.TIRE_18_IN, 4), 4));
                    break;
                case MERCEDES:
                    car.setWheels(new Pair<>(storage.getMaterial(MaterialType.MERCEDES_WHEEL, 4), 4));
                    car.setTires(new Pair<>(storage.getMaterial(MaterialType.TIRE_22_IN, 4), 4));
                    break;
                case BULLET_TIME_CAR:
                    car.setWheels(new Pair<>(storage.getMaterial(MaterialType.BULLET_TIME_WHEEL, 6), 6));
                    car.setTires(new Pair<>(storage.getMaterial(MaterialType.TIRE_30_IN, 6), 6));
                    break;
            }
        } catch (MaterialMissing exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            throw exception;
        }
    }

    @Override
    public String toString() {
        return "AssemblyWheelsMachine " + super.toString();
    }
}
