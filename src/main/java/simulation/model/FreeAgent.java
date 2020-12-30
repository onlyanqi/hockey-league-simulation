package simulation.model;

import persistance.dao.IFreeAgentDao;
import persistance.dao.IPlayerDao;
import persistance.serializers.ModelsForDeserialization.model.Player;
import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

import java.util.ArrayList;
import java.util.List;

public class FreeAgent extends SharedAttributes implements IFreeAgent {

    private int seasonId;
    private int leagueId;
    private List<IPlayer> playerList = new ArrayList<>();

    public FreeAgent() {
        setId(System.identityHashCode(this));
    }

    public FreeAgent(int id) {
        setId(id);
    }

    public FreeAgent(int id, IFreeAgentDao loadFreeAgentFactory) throws Exception {
        loadFreeAgentFactory.loadFreeAgentById(id, this);
    }

    public FreeAgent(persistance.serializers.ModelsForDeserialization.model.FreeAgent freeAgent) {
        IHockeyContext hockeyContextFactory = HockeyContext.getInstance();
        IModelFactory modelFactory = hockeyContextFactory.getModelFactory();
        this.leagueId = freeAgent.leagueId;
        for (Player player : freeAgent.playerList) {
            this.playerList.add(modelFactory.createPlayerFromSerialization(player));
        }
        this.seasonId = freeAgent.seasonId;
        this.setName(freeAgent.name);
        this.setId(freeAgent.id);
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public List<IPlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<IPlayer> playerList) {
        this.playerList = playerList;
    }

    public void addFreeAgent(IFreeAgentDao addFreeAgentFactory) throws Exception {
        addFreeAgentFactory.addFreeAgent(this);
    }

    public void loadPlayerListByFreeAgentId(IPlayerDao loadPlayerFactory) throws Exception {
        this.playerList = loadPlayerFactory.loadPlayerListByFreeAgentId(getId());
    }

    public List<Integer> getGoodFreeAgentsList(List<Double> strengthList) {
        Double thresholdPointForGoodPlayer = calculateStrengthAverage(strengthList);
        List<Integer> goodFreeAgentsIdList = new ArrayList<>();
        for (int i = 0; i < strengthList.size(); i++) {
            if (strengthList.get(i) >= thresholdPointForGoodPlayer) {
                goodFreeAgentsIdList.add(i);
            }
        }
        return goodFreeAgentsIdList;
    }

    public Double calculateStrengthAverage(List<Double> strengthList) {
        Double average = 0.0;
        for (int i = 0; i < strengthList.size(); i++) {
            average = average + strengthList.get(i);
        }
        average = average / strengthList.size();
        return average;
    }

}
