package model;

import data.IConferenceFactory;
import data.ILeagueFactory;

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

    @Override
    public int addLeague(League league) throws Exception {
        return 0;
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
                break;

            case 2:
                //name null
                league.setName(null);
                league.setStartDate(new Date(2000, 0, 0));
                league.setEndDate(new Date(2050, 0, 0));
                league.setCreatedBy(2);
                league.setConferenceList(formConferenceList());
                break;

            case 3:
                //end date less than start date
                league.setName("Invalid Date");
                league.setStartDate(new Date(2010, 0, 0));
                league.setEndDate(new Date(2000, 0, 0));
                league.setCreatedBy(3);
                league.setConferenceList(formConferenceList());
                break;
        }

    }

}
