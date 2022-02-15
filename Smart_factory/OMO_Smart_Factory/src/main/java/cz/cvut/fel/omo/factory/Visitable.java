package cz.cvut.fel.omo.factory;

import cz.cvut.fel.omo.visitors.Visitor;

/**
 * Visitable to be visited by visitors.
 */
public abstract class Visitable {

    public abstract void accept(Visitor visitor);

}
