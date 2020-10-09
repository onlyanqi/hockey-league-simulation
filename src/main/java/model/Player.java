package model;

import common.Constants;
import data.IAddFreeAgentFactory;
import data.IAddPlayerFactory;
import data.ILoadPlayerFactory;

public class Player extends ParentObj{

    public Player(){}

    public Player(int id){
        setId(id);
    }

    public Player(int id, ILoadPlayerFactory factory) throws Exception {
        setId(id);
        factory.loadPlayerById(id, this);
    }

    private int age;

    private String hometown;

    private String position;

    private int teamId;

    private int freeAgentId;

    private boolean isCaptain;

    private int seasonId;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getFreeAgentId() {
        return freeAgentId;
    }

    public void setFreeAgentId(int freeAgentId) {
        this.freeAgentId = freeAgentId;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }

    public boolean validPosition(){
        boolean isValid = false;

        if(isNotNull(getPosition()) && isNotEmpty(getPosition())){
             if(Constants.playerPositions.contains(getPosition())){
                 isValid = true;
             }
        }

        return isValid;
    }

    public void addPlayer(IAddPlayerFactory addPlayerFactory) throws Exception {
        addPlayerFactory.addPlayer(this);
    }

}
