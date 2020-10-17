/*
package model;

import simulation.data.ILoadDivisionFactory;
import simulation.data.ILoadConferenceFactory;
import simulation.model.Conference;
import simulation.model.Division;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadConferenceMock implements ILoadConferenceFactory {

    public List formDivisionList() throws Exception {
        List<Division> divisionList = new ArrayList<>();

        ILoadDivisionFactory divisionFactory = new LoadDivisionMock();
        Division division = new Division(1, divisionFactory);
        divisionList.add(division);

        division = new Division(2, divisionFactory);
        divisionList.add(division);

        return divisionList;
    }

    @Override
    public void loadConferenceByName(int id, Conference conference) throws Exception {

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                conference.setName("Conference1");
                conference.setStartDate(new Date(2000, 0, 0));
                conference.setEndDate(new Date(2050, 0, 0));
                conference.setLeagueId(1);
                conference.setDivisionList(formDivisionList());
                break;

            case 2:
                //name null
                conference.setName(null);
                conference.setStartDate(new Date(2000, 0, 0));
                conference.setEndDate(new Date(2050, 0, 0));
                conference.setLeagueId(1);
                conference.setDivisionList(formDivisionList());
                break;

            case 3:
                //end date less than start date
                conference.setName("Invalid Date");
                conference.setStartDate(new Date(2010, 0, 0));
                conference.setEndDate(new Date(2000, 0, 0));
                conference.setLeagueId(1);
                conference.setDivisionList(formDivisionList());
                break;
        }

    }

    @Override
    public Conference loadConferenceByName(String conferenceName) throws Exception {
        Conference conference = new Conference();
        conference.setName("Conference1");
        conference.setStartDate(new Date(2000, 0, 0));
        conference.setEndDate(new Date(2050, 0, 0));
        conference.setLeagueId(1);
        conference.setDivisionList(formDivisionList());
        return conference;
    }

    @Override
    public List<Conference> loadConferenceListByLeagueId(int leagueId) throws Exception {
        LoadLeagueMock loadLeagueMock = new LoadLeagueMock();
        return loadLeagueMock.formConferenceList();
    }

}
*/
