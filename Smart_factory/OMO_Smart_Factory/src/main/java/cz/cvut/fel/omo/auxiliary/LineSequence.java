package cz.cvut.fel.omo.auxiliary;

import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.enums.MachineType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sequence telling the production line how to put machines together.
 */
public class LineSequence {

    private CarType carType;
    private List<MachineType> sequence;
    private final Logger logger = Logger.getLogger("Machine logger");

    /**
     * Creates sequence based on type.
     * @param type - car type.
     */
    public void createSequence(CarType type) {
        switch (type) {
            case AUDI:
                createAudiLineSequence();
                break;
            case SKODA:
                createSkodaLineSequence();
                break;
            case MERCEDES:
                createMercedesLineSequence();
                break;
            case BULLET_TIME_CAR:
                createBulletTimeCarLineSequence();
                break;
        }
    }

    private void createAudiLineSequence() {
        logger.log(Level.INFO, "Production line is being changed for Audi cars brand.");

        carType = CarType.AUDI;
        sequence = new ArrayList<>();
        sequence.add(MachineType.ASSEMBLY_HOOD);
        sequence.add(MachineType.ASSEMBLY_ENGINE);
        sequence.add(MachineType.DRILLING);
        sequence.add(MachineType.WELDING);
        sequence.add(MachineType.ASSEMBLY_WHEELS);
        sequence.add(MachineType.ASSEMBLY_ELECTRONICS);
        sequence.add(MachineType.PAINTING);
    }

    private void createSkodaLineSequence() {
        logger.log(Level.INFO, "Production line is being changed for Skoda cars brand.");

        carType = CarType.SKODA;
        sequence = new ArrayList<>();
        sequence.add(MachineType.ASSEMBLY_ENGINE);
        sequence.add(MachineType.ASSEMBLY_HOOD);
        sequence.add(MachineType.ASSEMBLY_WHEELS);
        sequence.add(MachineType.ASSEMBLY_ELECTRONICS);
        sequence.add(MachineType.DRILLING);
        sequence.add(MachineType.WELDING);
        sequence.add(MachineType.PAINTING);
    }

    private void createMercedesLineSequence() {
        logger.log(Level.INFO, "Production line is being changed for Mercedes cars brand.");

        carType = CarType.MERCEDES;
        sequence = new ArrayList<>();
        sequence.add(MachineType.ASSEMBLY_ENGINE);
        sequence.add(MachineType.DRILLING);
        sequence.add(MachineType.ASSEMBLY_HOOD);
        sequence.add(MachineType.WELDING);
        sequence.add(MachineType.ASSEMBLY_ELECTRONICS);
        sequence.add(MachineType.PAINTING);
        sequence.add(MachineType.ASSEMBLY_WHEELS);
    }

    private void createBulletTimeCarLineSequence() {
        logger.log(Level.INFO, "Production line is being changed for BulletTime cars brand.");

        carType = CarType.BULLET_TIME_CAR;
        sequence = new ArrayList<>();
        sequence.add(MachineType.ASSEMBLY_WHEELS);
        sequence.add(MachineType.PAINTING);
        sequence.add(MachineType.ASSEMBLY_ENGINE);
        sequence.add(MachineType.ASSEMBLY_ELECTRONICS);
        sequence.add(MachineType.ASSEMBLY_HOOD);
        sequence.add(MachineType.WELDING);
        sequence.add(MachineType.WELDING);
        sequence.add(MachineType.DRILLING);
    }

    public CarType getCarType() {
        return carType;
    }

    public List<MachineType> getSequence() {
        return sequence;
    }
}
