package simulation.model;

import persistance.dao.ILeagueDao;
import persistance.dao.IUserDao;

import java.util.List;

public class User extends SharedAttributes implements IUser {

    private String password;
    private List<ILeague> leagueList;
    private ILeague league;

    public User() {
        setId(System.identityHashCode(this));
    }

    public User(int id) {
        setId(id);
    }

    public User(int id, IUserDao factory) throws Exception {
        setId(id);
        factory.loadUserById(id, this);
    }

    public User(String name, IUserDao factory) throws Exception {
        if (factory == null) {
            return;
        }
        factory.loadUserByName(name, this);
    }

    public List<ILeague> getLeagueList() {
        return leagueList;
    }

    public void setLeagueList(List<ILeague> leagueList) {
        if (leagueList == null) {
            return;
        }
        this.leagueList = leagueList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            return;
        } else {
            this.password = password;
        }
    }

    public void addUser(IUserDao addUserFactory) throws Exception {
        if (addUserFactory == null) {
            return;
        }
        addUserFactory.addUser(this);
    }

    public ILeague getLeague() {
        return league;
    }

    public void setLeague(ILeague league) {
        if (league == null) {
            return;
        }
        this.league = league;
    }

    public void loadLeagueByUserId(ILeagueDao loadLeagueFactory) throws Exception {
        if (loadLeagueFactory == null) {
            return;
        }
        this.leagueList = loadLeagueFactory.loadLeagueListByUserId(getId());
    }

}
