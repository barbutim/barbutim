package cz.cvut.fel.omo.car;

import cz.cvut.fel.omo.enums.CarType;

/**
 * Singleton. This class has blueprints of all the cars.
 */
public class CarBlueprintController {
    private static CarBlueprintController instance;

    CarBlueprint audi;
    CarBlueprint skoda;
    CarBlueprint mercedes;
    CarBlueprint bulletTimeCar;

    private CarBlueprintController() {
        audi = new CarBlueprint(CarType.AUDI);
        skoda = new CarBlueprint(CarType.SKODA);
        mercedes = new CarBlueprint(CarType.MERCEDES);
        bulletTimeCar = new CarBlueprint(CarType.BULLET_TIME_CAR);
    }

    public synchronized static CarBlueprintController getInstance(){
        if (instance == null) {
            instance = new CarBlueprintController();
        }
        return instance;
    }

    /**
     * Return specific blueprint.
     * @param carType - type of needed car
     * @return - blueprint
     */
    public CarBlueprint getBlueprint(CarType carType) {
        switch (carType) {
            case AUDI:
                return audi;
            case SKODA:
                return skoda;
            case MERCEDES:
                return mercedes;
            case BULLET_TIME_CAR:
                return bulletTimeCar;
        }
        return null;
    }

}
