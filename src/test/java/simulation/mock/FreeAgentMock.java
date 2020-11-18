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
        for (int i = 1; i < 32; i++) {
            Player player = new Player(i, playerFactory);
            playerList.add(player);
        }

        return playerList;
    }

    public List formFreeAgentListForCreateTeam() throws Exception {
        List<Player> playerList = new ArrayList<>();
        IPlayerFactory playerFactory = new PlayerMock();
        for (int i = 1; i < 32; i++) {
            Player player = new Player(1, playerFactory);
            playerList.add(player);
        }
        playerList.get(1).setPosition(Player.Position.GOALIE);
        playerList.get(1).setSaving(10);
        playerList.get(2).setPosition(Player.Position.GOALIE);
        playerList.get(2).setSaving(10);
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
                freeAgent.setName("freeAgent1");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 2:
                freeAgent.setName(null);
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 3:
                freeAgent.setName("Invalid Date");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 4:
                freeAgent.setName("Invalid Postion");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 5:
                freeAgent.setName("NotEnoughPlayers");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formInsufficientPlayerList());

            case 6:
                freeAgent.setName("EnoughPlayersForCreateTeam");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setPlayerList(formFreeAgentListForCreateTeam());
        }
    }

    private List<Player> formInsufficientPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();
        IPlayerFactory playerFactory = new PlayerMock();
        for (int i = 1; i < 5; i++) {
            Player player = new Player(i, playerFactory);
            playerList.add(player);
        }

        return playerList;
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
