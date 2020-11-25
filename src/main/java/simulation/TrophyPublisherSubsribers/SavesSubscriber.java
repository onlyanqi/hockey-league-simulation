package simulation.TrophyPublisherSubsribers;

import simulation.model.Player;

public class SavesSubscriber implements ITrophyEventListeners{

    @Override
    public void update(Object object, Integer count) {
        Player player = (Player) object;
        player.setSaves(player.getSaves()+count);
    }
}
