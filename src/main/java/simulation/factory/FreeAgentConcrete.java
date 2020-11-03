package simulation.factory;

import db.dao.FreeAgentDao;
import db.data.IFreeAgentFactory;
import simulation.model.FreeAgent;

public class FreeAgentConcrete {

    public FreeAgent newFreeAgent() {
        return new FreeAgent();
    }

    public IFreeAgentFactory newLoadFreeAgentFactory() {
        return new FreeAgentDao();
    }

    public IFreeAgentFactory newAddFreeAgentFactory() {
        return new FreeAgentDao();
    }

}
