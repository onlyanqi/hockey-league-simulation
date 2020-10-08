package model;

import data.ILoadUserFactory;

import java.util.List;

public class User extends ParentObj{

    public User(){}

    public User(int id){
        setId(id);
    }

    public User(int id, ILoadUserFactory factory) throws Exception {
        setId(id);
        factory.loadUserById(id, this);
    }

    private String password;

    private List<League> leagueList;

    public List<League> getLeagueList() {
        return leagueList;
    }

    public void setLeagueList(List<League> leagueList) {
        this.leagueList = leagueList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
