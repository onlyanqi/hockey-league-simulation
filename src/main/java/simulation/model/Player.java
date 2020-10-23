package simulation.model;

import db.data.IPlayerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends ParentObj{

    public Player(){}

    public Player(int id){
        setId(id);
    }

    public Player(int id, IPlayerFactory factory) throws Exception {
        setId(id);
        factory.loadPlayerById(id, this);
    }

    private long age;

    private String hometown;

    private String position;

    private int teamId;

    private int freeAgentId;

    private boolean isCaptain;

    private int seasonId;

    private long skating;

    private long shooting;

    private long checking;

    private long saving;

    private double strength;

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
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
    public long getSkating() {
        return skating;
    }

    public void setSkating(long skating) {
        this.skating = skating;
    }

    public long getShooting() {
        return shooting;
    }

    public void setShooting(long shooting) {
        this.shooting = shooting;
    }

    public long getChecking() {
        return checking;
    }

    public void setChecking(long checking) {
        this.checking = checking;
    }

    public long getSaving() {
        return saving;
    }

    public void setSaving(long saving) {
        this.saving = saving;
    }


    public void setStrength() {
        switch (getPosition()) {
            case "forward":
                this.strength = getSkating() + getShooting() + (getChecking() / 2);
                break;
            case "defense":
                this.strength = getSkating() + getChecking() + (getShooting() / 2);
                break;
            case "goalie":
                this.strength = getSkating() + getSaving();
                break;
        }
    }

    public double getStrength(){
        return strength;
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
        List<String> playerPositions = new ArrayList<>(Arrays.asList("goalie", "forward", "defense"));
        if(isNotNull(getPosition()) && isNotEmpty(getPosition())){
             if(playerPositions.contains(getPosition())){
                 isValid = true;
             }
        }
        return isValid;
    }

    public void addPlayer(IPlayerFactory addPlayerFactory) throws Exception {
        addPlayerFactory.addPlayer(this);
    }

}
