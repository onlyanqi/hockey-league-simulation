package factory;

import dao.AddFreeAgentDao;
import dao.LoadFreeAgentDao;
import data.IAddFreeAgentFactory;
import data.ILoadFreeAgentFactory;
import model.FreeAgent;
import model.HockeyContext;

public class HockeyContextConcrete {

    public HockeyContext newHockeyContext(){
        return new HockeyContext();
    }

}
