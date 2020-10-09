package model;


import data.IAddTeamFactory;
import data.ILoadPlayerFactory;
import data.ILoadTeamFactory;

import java.util.List;

public class Team extends ParentObj{

    public Team(){}

    public Team(int id){
        setId(id);
    }

    public Team(int id, ILoadTeamFactory factory) throws Exception {
        setId(id);
        factory.loadTeamById(id, this);
    }

    public Team(String name, ILoadTeamFactory factory) throws Exception {
        factory.loadTeamByName(name, this);
    }

    private String hometown;

    private String mascot;

    private int divisionId;

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

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
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

    public void addTeam(IAddTeamFactory addTeamFactory) throws Exception {
        addTeamFactory.addTeam(this);
    }

    public void loadPlayerListByTeamId(ILoadPlayerFactory loadPlayerFactory) throws Exception {
        this.playerList = loadPlayerFactory.loadPlayerListByTeamId(getId());
    }
}
