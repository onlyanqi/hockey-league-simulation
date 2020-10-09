package model;

import data.IAddConferenceFactory;
import data.ILoadConferenceFactory;
import data.ILoadDivisionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddConferenceMock implements IAddConferenceFactory {

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
    public int addConference(Conference conference) throws Exception {
        conference = new Conference(1);
        return conference.getId();
    }
}
