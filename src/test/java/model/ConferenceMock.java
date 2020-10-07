package model;

import data.IDivisionFactory;
import data.IConferenceFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConferenceMock implements IConferenceFactory {

    private List formDivisionList() throws Exception {
        List<Division> divisionList = new ArrayList<>();

        IDivisionFactory divisionFactory = new DivisionMock();
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
    public int addConference(Conference conference) throws Exception {
        return 0;
    }

}
