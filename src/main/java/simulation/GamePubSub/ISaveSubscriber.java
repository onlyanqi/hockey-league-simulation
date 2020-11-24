package simulation.GamePubSub;

import simulation.model.ILeague;

public interface ISaveSubscriber {
    void updateSave(ILeague league,String team,Integer count);
}
