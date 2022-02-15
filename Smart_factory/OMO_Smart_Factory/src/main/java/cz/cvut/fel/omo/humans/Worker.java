package cz.cvut.fel.omo.humans;

public class Worker extends Human {

    /**
     * 0 = exhausted, 8 = not tired at all
     */
    private Double energy = 8.0;

    public Worker(String name) {
        super(name);
    }

    /**
     * Get exhausted by working.
     */
    public void work() {
        if (energy > 0) {
            energy--;
        }
    }

    /**
     * Rest for 16 iterations.
     */
    public void rest() {
        if (energy < 8) {
            energy = energy + 0.5;
        }
    }

    public boolean isTired() {
        return energy == 0;
    }

    public boolean isRested() {
        return energy == 8;
    }

}
