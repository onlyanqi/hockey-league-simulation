package simulation.factory;

import simulation.model.FreeAgent;
import simulation.model.IFreeAgent;

public class FreeAgentConcrete implements IFreeAgentFactory{

    public IFreeAgent newFreeAgent() {
        return new FreeAgent();
    }

}
