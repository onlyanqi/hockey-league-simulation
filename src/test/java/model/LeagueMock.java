package model;

import data.IConferenceFactory;
import data.IDivisionFactory;
import data.ILeagueFactory;
import data.IPlayerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeagueMock implements ILeagueFactory {

    private List formConferenceList() throws Exception {
        List<Conference> conferenceList = new ArrayList<>();

        IConferenceFactory conferenceFactory = new ConferenceMock();
        Conference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);

        conference = new Conference(2, conferenceFactory);
        conferenceList.add(conference);

        return conferenceList;
    }

    private FreeAgent formFreeAgent() throws Exception {
        FreeAgent freeAgent = new FreeAgent();

        freeAgent.setId(1);
        List<Player> playerList = new ArrayList<>();

        IPlayerFactory playerFactory = new PlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        freeAgent.setPlayerList(playerList);

        return freeAgent;
    }

    @Override
    public int addLeague(League league) throws Exception {
        league = new League();
        league.setId(1);
        league.setName("League1");
        return league.getId();
    }

    @Override
    public void loadLeagueByName(int id, League league) throws Exception {

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                league.setName("League1");
                league.setStartDate(new Date(2000, 0, 0));
                league.setEndDate(new Date(2050, 0, 0));
                league.setCountry("Canada");
                league.setCreatedBy(1);
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                break;

            case 2:
                //name null
                league.setName(null);
                league.setStartDate(new Date(2000, 0, 0));
                league.setEndDate(new Date(2050, 0, 0));
                league.setCreatedBy(2);
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                break;

            case 3:
                //end date less than start date
                league.setName("Invalid Date");
                league.setStartDate(new Date(2010, 0, 0));
                league.setEndDate(new Date(2000, 0, 0));
                league.setCreatedBy(3);
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                break;
        }

    }

}
