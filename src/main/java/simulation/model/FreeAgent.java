package simulation.model;

import db.data.IFreeAgentFactory;
import db.data.IPlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class FreeAgent extends SharedAttributes {

    private int seasonId;
    private int leagueId;
    private List<Player> playerList;

    public FreeAgent() {
    }

    public FreeAgent(int id) {
        setId(id);
    }

    public FreeAgent(int id, IFreeAgentFactory loadFreeAgentFactory) throws Exception {
        if(loadFreeAgentFactory == null){
            return;
        }
        loadFreeAgentFactory.loadFreeAgentById(id, this);
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

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        if(playerList == null){
            return;
        }
        this.playerList = playerList;
    }

    public void addFreeAgent(IFreeAgentFactory addFreeAgentFactory) throws Exception {
        if(addFreeAgentFactory == null){
            return;
        }
        addFreeAgentFactory.addFreeAgent(this);
    }

    public void loadPlayerListByFreeAgentId(IPlayerFactory loadPlayerFactory) throws Exception {
        if(loadPlayerFactory == null){
            return;
        }
        this.playerList = loadPlayerFactory.loadPlayerListByFreeAgentId(getId());
    }

    public List<Integer> getGoodFreeAgentsList(List<Double> strengthList) {
        if(strengthList == null){
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
        if(strengthList == null){
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
