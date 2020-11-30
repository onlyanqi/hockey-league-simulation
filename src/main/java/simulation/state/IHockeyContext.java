package simulation.state;

import org.json.simple.JSONObject;
import persistance.dao.IDaoFactory;
import simulation.model.IModelFactory;
import simulation.model.IUser;


public interface IHockeyContext {

    IUser getUser();

    void setUser(IUser user);

    void startAction(JSONObject jsonFromInput) throws Exception;

    IHockeyState getHockeyState();

    IModelFactory getModelFactory();

    void setModelFactory(IModelFactory modelFactory);

    IDaoFactory getDaoFactory();

    void setDaoFactory(IDaoFactory daoFactory);

}
