package simulation.model.mock;

import db.data.IDivisionFactory;
import db.data.ITeamFactory;
import simulation.model.Division;
import simulation.model.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DivisionMock implements IDivisionFactory {

    public List formTeamList() throws Exception {
        List<Team> teamList = new ArrayList<>();

        ITeamFactory teamFactory = new TeamMock();
        Team team = new Team(1, teamFactory);
        teamList.add(team);

        team = new Team(2, teamFactory);
        teamList.add(team);

        return teamList;
    }

    @Override
    public int addDivision(Division division) throws Exception {
        division = new Division(1);
        return division.getId();
    }

    @Override
    public void loadDivisionById(int id, Division division) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                //all correct data
                division.setName("Division1");
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;

            case 2:
                //name null
                division.setName(null);
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;

            case 3:
                //end date less than start date
                division.setName("Invalid Date");
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;
        }

    }

    @Override
    public Division loadDivisionByName(String divisionName) throws Exception {
        Division division = new Division();
        division.setName("Division1");
        division.setConferenceId(1);
        division.setTeamList(formTeamList());
        return division;
    }

    @Override
    public List<Division> loadDivisionListByConferenceId(int conferenceId) throws Exception {
        ConferenceMock loadConferenceMock = new ConferenceMock();
        return loadConferenceMock.formDivisionList();
    }

}
