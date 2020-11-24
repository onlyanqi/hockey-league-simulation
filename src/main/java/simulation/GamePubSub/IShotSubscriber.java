package simulation.GamePubSub;

import simulation.model.ILeague;

public interface IShotSubscriber {
    void updateShot(ILeague league,String team,Integer count);
}
