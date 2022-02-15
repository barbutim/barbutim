package cz.cvut.fel.omo.visitors;

import cz.cvut.fel.omo.factory.FactoryController;
import cz.cvut.fel.omo.factory.ProductionLine;
import cz.cvut.fel.omo.machines.Machine;

public abstract class Visitor {

    public abstract void visitMachine(Machine machine);
    public abstract void visitProductionLine(ProductionLine productionLine);
    public abstract void visitFactory(FactoryController factory);
}
