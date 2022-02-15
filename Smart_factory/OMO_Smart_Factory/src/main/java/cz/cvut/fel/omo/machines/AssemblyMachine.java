package cz.cvut.fel.omo.machines;

import cz.cvut.fel.omo.auxiliary.ConstantValues;

public abstract class AssemblyMachine extends Machine {

    protected AssemblyMachine(Integer id) {
        super(id, ConstantValues.ASSEMBLY_MACHINE_CONSUMPTION);
    }

    @Override
    public String toString() {  // auxiliary
        return super.toString();
    }
}
