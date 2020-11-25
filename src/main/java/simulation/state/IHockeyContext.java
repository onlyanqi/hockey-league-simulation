package simulation.state;

import org.json.simple.JSONObject;
import simulation.dao.IDaoFactory;
import simulation.factory.*;
import simulation.model.IModelFactory;
import simulation.model.IUser;


public interface IHockeyContext {

    IUser getUser();

    void setUser(IUser user);

    void startAction(JSONObject jsonFromInput) throws Exception;

    IHockeyState getHockeyState();

    void setModelFactory(IModelFactory modelFactory);

    IModelFactory getModelFactory();

    IDaoFactory getDaoFactory();

    void setDaoFactory(IDaoFactory daoFactory);

}
