package simulation.model;

import simulation.data.IAddUserFactory;
import simulation.data.ILoadLeagueFactory;
import simulation.data.ILoadUserFactory;

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

    public User(String name, ILoadUserFactory factory) throws Exception {
        factory.loadUserByName(name, this);
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

    public void addUser(IAddUserFactory addUserFactory) throws Exception {
        addUserFactory.addUser(this);
    }

    private League league;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void loadLeagueByUserId(ILoadLeagueFactory loadLeagueFactory) throws Exception {
        this.leagueList = loadLeagueFactory.loadLeagueListByUserId(getId());
    }
}
