package cz.cvut.fel.omo.factoryexceptions;

/**
 * Materials needed for car assembly are not present.
 */
public class MaterialMissing extends Exception {

    public MaterialMissing(String message) {
        super(message);
    }
}
