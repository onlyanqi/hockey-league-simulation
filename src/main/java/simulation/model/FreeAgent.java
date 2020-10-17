package simulation.model;

import db.data.IFreeAgentFactory;
import db.data.IPlayerFactory;

import java.util.List;

public class FreeAgent extends ParentObj{

    public FreeAgent(){}

    public FreeAgent(int id){
        setId(id);
    }

    public FreeAgent(int id, IFreeAgentFactory loadFreeAgentFactory) throws Exception {
        loadFreeAgentFactory.loadFreeAgentById(id, this);
    }

    private int seasonId;

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    private int leagueId;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    private List<Player> playerList;

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void addFreeAgent(IFreeAgentFactory addFreeAgentFactory) throws Exception {
        addFreeAgentFactory.addFreeAgent(this);
    }

    public void loadPlayerListByFreeAgentId(IPlayerFactory loadPlayerFactory) throws Exception {
        this.playerList = loadPlayerFactory.loadPlayerListByFreeAgentId(getId());
    }
}
