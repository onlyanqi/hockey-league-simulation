package simulation.state;

import org.json.simple.JSONObject;
import simulation.factory.IAgingFactory;
import simulation.model.User;

public interface IHockeyContext {

    User getUser();

    void setUser(User user);

    void startAction(JSONObject jsonFromInput) throws Exception;

    IHockeyState getHockeyState();

    void setAgingFactory(IAgingFactory agingFactory);

    IAgingFactory getAgingFactory();
}
