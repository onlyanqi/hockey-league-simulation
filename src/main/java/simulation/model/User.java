package simulation.model;

import db.data.ILeagueFactory;
import db.data.IUserFactory;

import java.util.List;

public class User extends SharedAttributes {

    public User() {
    }

    public User(int id) {
        setId(id);
    }

    public User(int id, IUserFactory factory) throws Exception {
        setId(id);
        factory.loadUserById(id, this);
    }

    public User(String name, IUserFactory factory) throws Exception {
        if (factory == null){
            return;
        }
        factory.loadUserByName(name, this);
    }

    private String password;
    private List<League> leagueList;
    private League league;

    public List<League> getLeagueList() {
        return leagueList;
    }

    public void setLeagueList(List<League> leagueList) {
        if (leagueList == null){
            return;
        }
        this.leagueList = leagueList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (isNotEmpty(password)){
            this.password = password;
        }
    }

    public void addUser(IUserFactory addUserFactory) throws Exception {
        if (addUserFactory == null){
            return;
        }
        addUserFactory.addUser(this);
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        if (league == null){
            return;
        }
        this.league = league;
    }

    public void loadLeagueByUserId(ILeagueFactory loadLeagueFactory) throws Exception {
        if (loadLeagueFactory == null){
            return;
        }
        this.leagueList = loadLeagueFactory.loadLeagueListByUserId(getId());
    }

}
