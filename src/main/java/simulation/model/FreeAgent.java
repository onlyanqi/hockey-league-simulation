package simulation.model;

import simulation.dao.IFreeAgentDao;
import simulation.dao.IPlayerDao;
import simulation.serializers.ModelsForDeserialization.model.Player;

import java.util.ArrayList;
import java.util.List;

public class FreeAgent extends SharedAttributes implements IFreeAgent{

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
        if (loadFreeAgentFactory == null) {
            return;
        }
        loadFreeAgentFactory.loadFreeAgentById(id, this);
    }

    public FreeAgent(simulation.serializers.ModelsForDeserialization.model.FreeAgent freeAgent){
        this.leagueId = freeAgent.leagueId;
        for(Player player : freeAgent.playerList){
            this.playerList.add(new simulation.model.Player(player));
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
        if (playerList == null) {
            return;
        }
        this.playerList = playerList;
    }

    public void addFreeAgent(IFreeAgentDao addFreeAgentFactory) throws Exception {
        if (addFreeAgentFactory == null) {
            return;
        }
        addFreeAgentFactory.addFreeAgent(this);
    }

    public void loadPlayerListByFreeAgentId(IPlayerDao loadPlayerFactory) throws Exception {
        if (loadPlayerFactory == null) {
            return;
        }
        this.playerList = loadPlayerFactory.loadPlayerListByFreeAgentId(getId());
    }

    public List<Integer> getGoodFreeAgentsList(List<Double> strengthList) {
        if (strengthList == null) {
            return null;
        }
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
        if (strengthList == null) {
            return null;
        }
        Double average = 0.0;
        for (int i = 0; i < strengthList.size(); i++) {
            average = average + strengthList.get(i);
        }
        average = average / strengthList.size();
        return average;
    }

}
