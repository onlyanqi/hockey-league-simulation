package model;

import simulation.data.IAddFreeAgentFactory;
import simulation.data.ILoadPlayerFactory;
import simulation.model.FreeAgent;
import simulation.model.Player;

import java.util.ArrayList;
import java.util.List;

public class AddFreeAgentMock implements IAddFreeAgentFactory {

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        ILoadPlayerFactory playerFactory = new LoadPlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        return playerList;
    }

    @Override
    public int addFreeAgent(FreeAgent freeAgent) throws Exception {
        freeAgent = new FreeAgent(1);
        return freeAgent.getId();
    }
}
