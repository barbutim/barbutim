package cz.cvut.fel.omo.car;

import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.enums.CarType;
import javafx.util.Pair;

/**
 * This class knows all the materials needed to create a car of a certain type.
 */
public class CarBlueprint {
    private CarType type;
    private MaterialType electronics;
    private MaterialType engine;
    private MaterialType hood;
    private Pair<MaterialType, Integer> wheels;
    private Pair<MaterialType, Integer> tires;
    private MaterialType paint;
    //private String drillingStyle;
    //private Integer weldingTier;

    public CarBlueprint(CarType carType) {
        switch (carType) {
            case AUDI:
                type = CarType.AUDI;
                electronics = MaterialType.AUDI_ELECTRONICS;
                engine = MaterialType.AUDI_ENGINE;
                hood = MaterialType.AUDI_HOOD;
                wheels = new Pair<>(MaterialType.AUDI_WHEEL, 4);
                tires = new Pair<>(MaterialType.TIRE_20_IN, 4);
                paint = MaterialType.WHITE_PAINT;
                //drillingStyle = "Audi Drilling";
                //weldingTier = 3;
                break;

            case SKODA:
                type = CarType.SKODA;
                electronics = MaterialType.SKODA_ELECTRONICS;
                engine = MaterialType.SKODA_ENGINE;
                hood = MaterialType.SKODA_HOOD;
                wheels = new Pair<>(MaterialType.SKODA_WHEEL, 4);
                tires = new Pair<>(MaterialType.TIRE_18_IN, 4);
                paint = MaterialType.BLUE_PAINT;
                //drillingStyle = "Skoda Drilling";
                //weldingTier = 2;
                break;

            case MERCEDES:
                type = CarType.MERCEDES;
                electronics = MaterialType.MERCEDES_ELECTRONICS;
                engine = MaterialType.MERCEDES_ENGINE;
                hood = MaterialType.MERCEDES_HOOD;
                wheels = new Pair<>(MaterialType.MERCEDES_WHEEL, 4);
                tires = new Pair<>(MaterialType.TIRE_22_IN, 4);
                paint = MaterialType.BLACK_PAINT;
                //drillingStyle = "Mercedes Drilling";
                //weldingTier = 4;
                break;

            case BULLET_TIME_CAR:
                type = CarType.BULLET_TIME_CAR;
                electronics = MaterialType.BULLET_TIME_ELECTRONICS;
                engine = MaterialType.BULLET_TIME_ENGINE;
                hood = MaterialType.BULLET_TIME_HOOD;
                wheels = new Pair<>(MaterialType.BULLET_TIME_WHEEL, 6);
                tires = new Pair<>(MaterialType.TIRE_30_IN, 6);
                paint = MaterialType.RED_PAINT;
                //drillingStyle = "BulletTime Drilling";
                //weldingTier = 6;
                break;
        }
    }

    public CarType getType() {
        return type;
    }

    public MaterialType getElectronics() {
        return electronics;
    }

    public MaterialType getEngine() {
        return engine;
    }

    public MaterialType getHood() {
        return hood;
    }

    public Pair<MaterialType, Integer> getWheels() {
        return wheels;
    }

    public Pair<MaterialType, Integer> getTires() {
        return tires;
    }

    public MaterialType getPaint() {
        return paint;
    }
}
