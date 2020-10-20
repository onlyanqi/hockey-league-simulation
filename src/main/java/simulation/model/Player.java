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

    private int age;

    private String hometown;

    private String position;

    private int teamId;

    private int freeAgentId;

    private boolean isCaptain;

    private int seasonId;

    private float skating;

    private float shooting;

    private float checking;

    private float saving;

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
    public float getSkating() {
        return skating;
    }

    public void setSkating(float skating) {
        this.skating = skating;
    }

    public float getShooting() {
        return shooting;
    }

    public void setShooting(float shooting) {
        this.shooting = shooting;
    }

    public float getChecking() {
        return checking;
    }

    public void setChecking(float checking) {
        this.checking = checking;
    }

    public float getSaving() {
        return saving;
    }

    public void setSaving(float saving) {
        this.saving = saving;
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
