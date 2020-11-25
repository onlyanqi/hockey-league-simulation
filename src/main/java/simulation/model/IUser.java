package simulation.model;

import simulation.dao.ILeagueDao;
import simulation.dao.IUserDao;

import java.util.List;

public interface IUser {

    List<ILeague> getLeagueList();

    void setLeagueList(List<ILeague> leagueList);

    String getPassword();

    void setPassword(String password);

    void addUser(IUserDao addUserFactory) throws Exception;

    ILeague getLeague();

    void setLeague(ILeague league);

    void loadLeagueByUserId(ILeagueDao loadLeagueFactory) throws Exception;

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

}
