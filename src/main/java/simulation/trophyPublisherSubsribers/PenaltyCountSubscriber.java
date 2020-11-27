package simulation.trophyPublisherSubsribers;

import simulation.model.Player;

public class PenaltyCountSubscriber implements ITrophyEventListeners {

    @Override
    public void update(Object object, Integer count) {
        Player player = (Player) object;
        player.setPenaltyCount(player.getPenaltyCount()+count);
    }
}
