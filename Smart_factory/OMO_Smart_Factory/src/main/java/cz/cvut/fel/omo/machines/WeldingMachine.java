package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.auxiliary.ConstantValues;
import cz.cvut.fel.omo.enums.MachineType;

public class WeldingMachine extends Machine {

    public WeldingMachine(Integer id) {
        super(id, ConstantValues.WELDING_MACHINE_CONSUMPTION);
        type = MachineType.WELDING;
    }

    @Override
    protected void processRequest(Car car) {
        switch (car.getType()) {
            case AUDI:
                car.setWeldingTier(3);
                break;
            case SKODA:
                car.setWeldingTier(2);
                break;
            case MERCEDES:
                car.setWeldingTier(4);
                break;
            case BULLET_TIME_CAR:
                car.setWeldingTier(6);
                break;
        }
    }

    @Override
    public String toString() {
        return "WeldingMachine " + super.toString();
    }
}
