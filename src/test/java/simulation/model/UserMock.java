package simulation.model;

import db.data.ILeagueFactory;
import db.data.IUserFactory;

import java.util.ArrayList;
import java.util.List;

public class UserMock implements IUserFactory {

    @Override
    public long addUser(User user) throws Exception {
        user = new User(1);
        return user.getId();
    }

    private List formLeagueList() throws Exception {
        List<League> leagueList = new ArrayList<>();

        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        leagueList.add(league);

        league = new League(2, leagueFactory);
        leagueList.add(league);

        return leagueList;
    }

    @Override
    public void loadUserById(int id, User user) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                //all correct data
                user.setId(1);
                user.setName("User1");
                user.setPassword("Password1");
                user.setLeagueList(formLeagueList());
                break;

            case 2:
                //name null
                user.setId(2);
                user.setName(null);
                user.setPassword("Password2");
                user.setLeagueList(formLeagueList());
                break;

            case 3:
                //password null
                user.setId(3);
                user.setName("Player3");
                user.setPassword(null);
                user.setLeagueList(formLeagueList());
                break;
        }

    }

    @Override
    public void loadUserByName(String userName, User user) throws Exception {
        user = new User();
        user.setId(1);
        user.setName("User1");
        user.setPassword("Password1");
    }

}
