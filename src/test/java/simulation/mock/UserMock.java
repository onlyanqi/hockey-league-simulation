package simulation.mock;

import simulation.dao.ILeagueDao;
import simulation.dao.IUserDao;
import simulation.model.IUser;
import simulation.model.League;
import simulation.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMock implements IUserDao {

    @Override
    public long addUser(IUser user) {
        user = new User(1);
        return user.getId();
    }

    private League formLeague() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        return league;
    }

    private List formLeagueList() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        List<League> leagueList = new ArrayList<>();
        League league = new League(1, leagueFactory);
        leagueList.add(league);
        league = new League(2, leagueFactory);
        leagueList.add(league);
        return leagueList;
    }

    @Override
    public void loadUserById(int id, IUser user) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                user.setId(1);
                user.setName("User1");
                user.setPassword("Password1");
                user.setLeague(formLeague());
                user.setLeagueList(formLeagueList());
                break;

            case 2:
                user.setId(2);
                user.setName(null);
                user.setPassword("Password2");
                user.setLeague(formLeague());
                user.setLeagueList(formLeagueList());
                break;

            case 3:
                user.setId(3);
                user.setName("Player3");
                user.setPassword(null);
                user.setLeague(formLeague());
                user.setLeagueList(formLeagueList());
                break;
            case 4:
                user.setId(4);
                user.setName("Player4");
                user.setPassword("Pass");
                user.setLeague(formLeagueForGames());
                user.setLeagueList(formLeagueList());
                break;
        }

    }

    @Override
    public void loadUserByName(String userName, IUser user) {
        user = new User();
        user.setId(1);
        user.setName("User1");
        user.setPassword("Password1");
    }

    private League formLeagueForGames() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(5, leagueFactory);
        return league;
    }

}
