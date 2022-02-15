package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.enums.MachineType;
import cz.cvut.fel.omo.events.BrokeDownEvent;
import cz.cvut.fel.omo.events.ConsumptionEvent;
import cz.cvut.fel.omo.factory.Visitable;
import cz.cvut.fel.omo.factoryexceptions.MaterialMissing;
import cz.cvut.fel.omo.enums.MachineState;
import cz.cvut.fel.omo.storages.MaterialStorage;
import cz.cvut.fel.omo.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Machine that is a part of production line.
 */
public abstract class Machine extends Visitable {

    private final Integer id;
    private MachineState state;
    private Integer health = 100;
    private final Integer consumption;
    private final List<ConsumptionEvent> consumptionEvents = new ArrayList<>();
    private final List<BrokeDownEvent> brokeDownEvents = new ArrayList<>();
    /**
     * random number generator for health wear off
     */
    private final Random rand = new Random();
    protected MachineType type;
    protected MaterialStorage storage;
    protected Logger logger = Logger.getLogger("Machine logger");

    public Machine (Integer id, Integer consumption) {
        this.id = id;
        this.state = MachineState.FUNCTIONAL;
        this.consumption = consumption;
    }

    /**
     * Decorator - instead of making the method processRequest more complex, we used this method to create more
     * functions like note consumption and reduce health.
     * @param car - car to assemble
     * @param tick - current tick
     * @throws MaterialMissing - needed material is not present
     */
    public void use(Car car, Integer tick) throws MaterialMissing {
        processRequest(car);
        noteConsumption(tick);
        reduceHealth(tick);
    }

    /**
     * Add a certain part to the car.
     * @param car - car to asseble
     * @throws MaterialMissing - needed material is not present
     */
    protected abstract void processRequest(Car car) throws MaterialMissing;

    /**
     * Create a consumption event and store it.
     * @param tick - current tick
     */
    private void noteConsumption(Integer tick) {
        consumptionEvents.add(new ConsumptionEvent(tick, consumption, this));
    }

    /**
     * Randomly reduce health. If health is reduced to zero, change the state to broken and create BrokeDown event.
     * @param tick - current tick
     */
    private void reduceHealth(Integer tick) {
        int randomNumber = rand.nextInt(20);    // 1 to 20
        if (randomNumber <= 3) {            // for 1-3 (15% chance)
            health -= 25+rand.nextInt(5);   // reduce by 25-30
        } else if (randomNumber <= 7) {     // for 4-7 (20% chance)
            health -= 10+rand.nextInt(5);   // reduce by 10-15
        } else {                            // for 8-20 (65% chance)
            health -= 1+rand.nextInt(9);    // reduce by 1-10
        }

        if (health <= 0) {
            health = 0;
            state = MachineState.BROKEN;
            logger.log(Level.WARNING, this + " broke down.");
            brokeDownEvents.add(new BrokeDownEvent(tick, this));
        }
    }

    /**
     * Repair this machine
     * @return - machine was fully repaired
     */
    public boolean repair() {
        if (state.equals(MachineState.FUNCTIONAL)) {
            return true;
        }
        health += 34;
        if (health >= 100) {
            health = 100;
            state = MachineState.FUNCTIONAL;
            logger.log(Level.INFO, this + " was repaired.");
            return true;
        }
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitMachine(this);
    }

    public void setStorage(MaterialStorage storage) {
        this.storage = storage;
    }

    public Integer getHealth() {
        return health;
    }

    public MachineType getType() {
        return type;
    }

    public MachineState getState() {
        return state;
    }

    public boolean isBroken() {
        return state == MachineState.BROKEN;
    }

    public List<ConsumptionEvent> getConsumptionEvents() {
        return consumptionEvents;
    }

    public List<BrokeDownEvent> getBrokeDownEvents() {
        return brokeDownEvents;
    }

    @Override
    public String toString() {
        return "ID " + id;
    }

}
