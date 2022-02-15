package cz.cvut.fel.omo.car;

import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.enums.CarType;
import javafx.util.Pair;

/**
 * Car that is being created in the factory.
 */
public class Car {

    private CarType type;
    private MaterialType electronics = null;
    private MaterialType engine = null;
    private MaterialType hood = null;
    private Pair<MaterialType, Integer> wheels = null;
    private Pair<MaterialType, Integer> tires = null;
    private String DrillingStyle = null;
    private MaterialType paint = null;
    private Integer weldingTier = null;

    public Car(CarType type) {
        this.type = type;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public void setElectronics(MaterialType electronics) {
        this.electronics = electronics;
    }

    public void setEngine(MaterialType engine) {
        this.engine = engine;
    }

    public void setHood(MaterialType hood) {
        this.hood = hood;
    }

    public void setWheels(Pair<MaterialType, Integer> wheels) {
        this.wheels = wheels;
    }

    public void setTires(Pair<MaterialType, Integer> tires) {
        this.tires = tires;
    }

    public void setDrillingStyle(String drillingStyle) {
        DrillingStyle = drillingStyle;
    }

    public void setPaint(MaterialType paint) {
        this.paint = paint;
    }

    public void setWeldingTier(Integer weldingTier) {
        this.weldingTier = weldingTier;
    }

    /**
     * Counts the number of percent of the completed car.
     * @return - percent completed
     */
    public double getPercent() {
        double percent = 0;
        if (electronics != null) {
            percent++;
        }
        if (engine != null) {
            percent++;
        }
        if (hood != null) {
            percent++;
        }
        if (wheels != null) {
            percent++;
        }
        if (tires != null) {
            percent++;
        }
        if (DrillingStyle != null) {
            percent++;
        }
        if (paint != null) {
            percent++;
        }
        if (weldingTier != null) {
            percent++;
        }
        return percent*12.5;
    }
}
