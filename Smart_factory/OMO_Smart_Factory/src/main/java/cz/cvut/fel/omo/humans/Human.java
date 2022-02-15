package cz.cvut.fel.omo.humans;

/**
 * Human with a name.
 */
public abstract class Human {
    private final String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
