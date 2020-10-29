package simulation.mock;

import db.data.IFreeAgentFactory;
import db.data.IPlayerFactory;
import simulation.model.FreeAgent;
import simulation.model.Player;

import java.util.ArrayList;
import java.util.List;

public class FreeAgentMock implements IFreeAgentFactory {

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        IPlayerFactory playerFactory = new PlayerMock();
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

    @Override
    public void loadFreeAgentById(int id, FreeAgent freeAgent) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                //all correct data
                freeAgent.setName("freeAgent1");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 2:
                //name null
                freeAgent.setName(null);
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 3:
                //end date less than start date
                freeAgent.setName("Invalid Date");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 4:
                //invalid position
                freeAgent.setName("Invalid Postion");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;
        }
    }

    @Override
    public FreeAgent loadFreeAgentByLeagueId(int id) throws Exception {
        FreeAgent freeAgent = new FreeAgent();
        freeAgent.setLeagueId(id);
        freeAgent.setName("FreeAgent1");
        freeAgent.setPlayerList(formPlayerList());
        return freeAgent;
    }

}
