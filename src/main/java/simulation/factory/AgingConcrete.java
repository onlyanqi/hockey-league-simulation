package simulation.factory;

import simulation.model.Aging;

public class AgingConcrete implements IAgingFactory {

    public Aging newAging() {
        return new Aging();
    }

}