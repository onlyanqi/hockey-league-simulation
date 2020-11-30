package simulation.mock;

import simulation.dao.DaoFactoryMock;
import simulation.dao.IDaoFactory;
import simulation.dao.IFreeAgentDao;
import simulation.dao.IPlayerDao;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

public class FreeAgentMock implements IFreeAgentDao {

    private IDaoFactory daoFactory;
    private IModelFactory modelFactory;

    public FreeAgentMock() {
        daoFactory = DaoFactoryMock.getInstance();
        modelFactory = ModelFactory.getInstance();
    }

    public List formPlayerList() throws Exception {
        List<IPlayer> playerList = new ArrayList<>();

        IPlayerDao playerFactory = daoFactory.createPlayerDao();
        for (int i = 1; i < 32; i++) {
            IPlayer player = modelFactory.createPlayerWithIdDao(i, playerFactory);
            playerList.add(player);
        }

        return playerList;
    }

    public List formFreeAgentListForCreateTeam() throws Exception {
        List<IPlayer> playerList = new ArrayList<>();
        IPlayerDao playerFactory = daoFactory.createPlayerDao();
        for (int i = 1; i < 32; i++) {
            IPlayer player = modelFactory.createPlayerWithIdDao(1, playerFactory);
            playerList.add(player);
        }
        playerList.get(1).setPosition(Position.GOALIE);
        playerList.get(1).setSaving(10);
        playerList.get(2).setPosition(Position.GOALIE);
        playerList.get(2).setSaving(10);
        return playerList;
    }

    @Override
    public int addFreeAgent(IFreeAgent freeAgent) throws Exception {
        freeAgent = modelFactory.createFreeAgentWithId(1);
        return freeAgent.getId();
    }

    @Override
    public void loadFreeAgentById(int id, IFreeAgent freeAgent) throws Exception {

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

    private List<IPlayer> formInsufficientPlayerList() throws Exception {
        List<IPlayer> playerList = new ArrayList<>();
        IPlayerDao playerFactory = daoFactory.createPlayerDao();
        for (int i = 1; i < 5; i++) {
            IPlayer player = modelFactory.createPlayerWithIdDao(i, playerFactory);
            playerList.add(player);
        }

        return playerList;
    }

    @Override
    public IFreeAgent loadFreeAgentByLeagueId(int id) throws Exception {
        IFreeAgent freeAgent = modelFactory.createFreeAgent();
        freeAgent.setLeagueId(id);
        freeAgent.setName("FreeAgent1");
        freeAgent.setPlayerList(formPlayerList());
        return freeAgent;
    }

}
