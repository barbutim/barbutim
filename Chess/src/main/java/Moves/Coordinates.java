package Moves;

/**
 * Moves.Coordinates represents method for coordinates a, b of basically everything in game.
 *
 * @author TimotejBarbus
 */
public class Coordinates {
    public int a;
    public int b;

    /**
     * Constructor for Moves.Coordinates.
     *
     * @param a represents coordinates a, horizontally
     * @param b represents coordinates b, vertically
     */
    public Coordinates(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    /**
     * Compares coordinates, used in Tests.
     *
     * @param object represents compared object
     * @return returns true if they are equal, false it they are not
     */
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if(null == object || this == null) {
            return false;
        }
        return true;
    }
}
