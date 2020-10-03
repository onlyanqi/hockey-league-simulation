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

    private String role;

    private long teamId;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public boolean validRole(){
        boolean isValid = false;

        if(isNotNull(getRole()) && isNotEmpty(getRole())){
             if(Constants.playerRoles.contains(getRole())){
                 isValid = true;
             }
        }

        return isValid;
    }

}
