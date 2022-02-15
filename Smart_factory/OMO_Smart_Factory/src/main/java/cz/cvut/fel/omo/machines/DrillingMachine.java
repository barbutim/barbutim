package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.auxiliary.ConstantValues;
import cz.cvut.fel.omo.enums.MachineType;

public class DrillingMachine extends Machine {

    public DrillingMachine(Integer id) {
        super(id, ConstantValues.DRILLING_MACHINE_CONSUMPTION);
        type = MachineType.DRILLING;
    }

    @Override
    protected void processRequest(Car car) {
        switch (car.getType()) {
            case AUDI:
                car.setDrillingStyle("Audi Drilling");
                break;
            case SKODA:
                car.setDrillingStyle("Skoda Drilling");
                break;
            case MERCEDES:
                car.setDrillingStyle("Mercedes Drilling");
                break;
            case BULLET_TIME_CAR:
                car.setDrillingStyle("BulletTime Drilling");
                break;
        }
    }

    @Override
    public String toString() {
        return "DrillingMachine " + super.toString();
    }
}
