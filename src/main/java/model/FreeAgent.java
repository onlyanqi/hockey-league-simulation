package model;

import data.ILoadFreeAgentFactory;

import java.util.List;

public class FreeAgent extends ParentObj{

    public FreeAgent(){}

    public FreeAgent(int id){
        setId(id);
    }

    public FreeAgent(int leagueId, ILoadFreeAgentFactory factory) throws Exception {
        setLeagueId(leagueId);
        factory.loadFreeAgentByLeagueId(leagueId, this);
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

}
