package factory;

import dao.AddFreeAgentDao;
import dao.LoadFreeAgentDao;
import simulation.data.IAddFreeAgentFactory;
import simulation.data.ILoadFreeAgentFactory;
import simulation.model.FreeAgent;

public class FreeAgentConcrete {

    public FreeAgent newFreeAgent(){
        return new FreeAgent();
    }

    public ILoadFreeAgentFactory newLoadFreeAgentFactory(){
        return new LoadFreeAgentDao();
    }

    public IAddFreeAgentFactory newAddFreeAgentFactory(){
        return new AddFreeAgentDao();
    }

}
