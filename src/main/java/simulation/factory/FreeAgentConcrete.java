package simulation.factory;

import db.data.IFreeAgentFactory;
import simulation.model.FreeAgent;

public class FreeAgentConcrete {

    public FreeAgent newFreeAgent() {
        return new FreeAgent();
    }

}
