package simulation.model;

import db.data.ILeagueFactory;
import db.data.IUserFactory;
import java.util.List;

public class User extends ParentObj{

    String name;

    public User(){}

    public User(int id){
        setId(id);
    }

    public User(int id, IUserFactory factory) throws Exception {
        setId(id);
        factory.loadUserById(id, this);
    }

    public User(String name, IUserFactory factory) throws Exception {
        factory.loadUserByName(name, this);
    }

    private String password;

    private List<League> leagueList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void addUser(IUserFactory addUserFactory) throws Exception {
        addUserFactory.addUser(this);
    }

    private League league;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void loadLeagueByUserId(ILeagueFactory loadLeagueFactory) throws Exception {
        this.leagueList = loadLeagueFactory.loadLeagueListByUserId(getId());
    }
}
