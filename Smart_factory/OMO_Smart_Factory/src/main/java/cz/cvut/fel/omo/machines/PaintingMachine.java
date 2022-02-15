package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.auxiliary.ConstantValues;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.factoryexceptions.MaterialMissing;

import java.util.logging.Level;

public class PaintingMachine extends Machine {

    public PaintingMachine(Integer id) {
        super(id, ConstantValues.PAINTING_MACHINE_CONSUMPTION);
        type = MachineType.PAINTING;
    }

    @Override
    protected void processRequest(Car car) throws MaterialMissing {
        try {
            switch (car.getType()) {
                case AUDI:
                    car.setPaint(storage.getMaterial(MaterialType.WHITE_PAINT));
                    break;
                case SKODA:
                    car.setPaint(storage.getMaterial(MaterialType.BLUE_PAINT));
                    break;
                case MERCEDES:
                    car.setPaint(storage.getMaterial(MaterialType.BLACK_PAINT));
                    break;
                case BULLET_TIME_CAR:
                    car.setPaint(storage.getMaterial(MaterialType.RED_PAINT));
                    break;
            }
        } catch (MaterialMissing exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            throw exception;
        }
    }

    @Override
    public String toString() {
        return "PaintingMachine " + super.toString();
    }
}
