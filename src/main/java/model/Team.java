package model;

import data.ITeamFactory;

import java.util.List;

public class Team extends ParentObj{

    public Team(){}

    public Team(long id){
        setId(id);
    }

    public Team(long id, ITeamFactory factory){
        setId(id);
        factory.loadTeam(id, this);
    }

    private String hometown;

    private String mascot;

    private long divisionId;

    private String generalManager;

    private String headCoach;

    private List<Player> playerList;

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public String getGeneralManager() {
        return generalManager;
    }

    public void setGeneralManager(String generalManager) {
        this.generalManager = generalManager;
    }

    public String getHeadCoach() {
        return headCoach;
    }

    public void setHeadCoach(String headCoach) {
        this.headCoach = headCoach;
    }
}
