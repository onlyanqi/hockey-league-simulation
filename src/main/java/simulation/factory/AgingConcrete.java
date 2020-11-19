package simulation.factory;

import simulation.model.Aging;
import simulation.model.IAging;

public class AgingConcrete implements IAgingFactory {

    public Aging newAging() {
        return new Aging();
    }

}