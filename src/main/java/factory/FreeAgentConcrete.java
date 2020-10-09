package factory;

import dao.AddDivisionDao;
import dao.AddFreeAgentDao;
import dao.LoadDivisionDao;
import dao.LoadFreeAgentDao;
import data.IAddDivisionFactory;
import data.IAddFreeAgentFactory;
import data.ILoadDivisionFactory;
import data.ILoadFreeAgentFactory;
import model.Division;
import model.FreeAgent;

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
