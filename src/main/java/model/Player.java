package model;

import common.Constants;
import data.IPlayerFactory;

public class Player extends ParentObj{

    public Player(){}

    public Player(long id){
        setId(id);
    }

    public Player(long id, IPlayerFactory factory){
        setId(id);
        factory.loadPlayer(id, this);
    }

    private int age;

    private String hometown;

    private String position;

    private long teamId;

    private boolean isCaptain;

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

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
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

}
