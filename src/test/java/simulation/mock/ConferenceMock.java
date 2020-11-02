package simulation.mock;

import db.data.IConferenceFactory;
import db.data.IDivisionFactory;
import simulation.model.Conference;
import simulation.model.Division;

import java.util.ArrayList;
import java.util.List;

public class ConferenceMock implements IConferenceFactory {

    public List formDivisionList() throws Exception {
        List<Division> divisionList = new ArrayList<>();

        IDivisionFactory divisionFactory = new DivisionMock();
        Division division = new Division(1, divisionFactory);
        divisionList.add(division);

        division = new Division(2, divisionFactory);
        divisionList.add(division);

        return divisionList;
    }

    public List formCreateTeamDivisionList() throws Exception {
        List<Division> divisionList = new ArrayList<>();

        IDivisionFactory divisionFactory = new DivisionMock();
        Division division = new Division(1, divisionFactory);
        divisionList.add(division);

        division = new Division(4, divisionFactory);
        divisionList.add(division);

        return divisionList;
    }

    @Override
    public int addConference(Conference conference) throws Exception {
        conference = new Conference(1);
        return conference.getId();
    }

    @Override
    public void loadConferenceById(int id, Conference conference) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                conference.setName("Conference1");
                conference.setLeagueId(1);
                conference.setDivisionList(formDivisionList());
                break;

            case 2:
                conference.setName(null);
                conference.setLeagueId(1);
                conference.setDivisionList(formDivisionList());
                break;

            case 3:
                conference.setName("Invalid Date");
                conference.setLeagueId(1);
                conference.setDivisionList(formDivisionList());
                break;

            case 4:
                conference.setName("Conference4");
                conference.setLeagueId(1);
                conference.setDivisionList(formCreateTeamDivisionList());
                break;
        }

    }

    @Override
    public Conference loadConferenceByName(String conferenceName) throws Exception {
        Conference conference = new Conference();
        conference.setName("Conference1");
        conference.setLeagueId(1);
        conference.setDivisionList(formDivisionList());
        return conference;
    }

    @Override
    public List<Conference> loadConferenceListByLeagueId(int leagueId) throws Exception {
        LeagueMock loadLeagueMock = new LeagueMock();
        return loadLeagueMock.formConferenceList();
    }
}
