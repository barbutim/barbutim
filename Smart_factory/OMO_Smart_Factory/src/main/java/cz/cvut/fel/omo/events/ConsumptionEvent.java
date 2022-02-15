package cz.cvut.fel.omo.events;

import cz.cvut.fel.omo.auxiliary.ConstantValues;
import cz.cvut.fel.omo.machines.Machine;

import java.text.DecimalFormat;

/**
 * Event of a machine consuming electricity.
 */
public class ConsumptionEvent extends MachineEvent {

    private final Integer tick;
    private Integer consumption;

    public ConsumptionEvent(Integer tick, Integer consumption, Machine machine) {
        super(machine);
        this.consumption = consumption;
        this.tick = tick;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#");
        if (tick == null) {
            return super.toString() + " consumed " + consumption + "kWh (" + df.format(consumption * ConstantValues.PRICE_PER_KWH) + " Kč)";
        } else {
            return "On " + tick + " tick " + super.toString() + " consumed " + consumption + "kWh (" + df.format(consumption * ConstantValues.PRICE_PER_KWH) + " Kč)";
        }
    }
}
