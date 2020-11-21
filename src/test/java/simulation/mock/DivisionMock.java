package simulation.mock;

import db.data.IDivisionDao;
import db.data.ITeamDao;
import simulation.model.Division;
import simulation.model.IDivision;
import simulation.model.ITeam;
import simulation.model.Team;

import java.util.ArrayList;
import java.util.List;

public class DivisionMock implements IDivisionDao {

    public List<ITeam> formTeamList() throws Exception {
        List<ITeam> teamList = new ArrayList<>();

        ITeamDao teamFactory = new TeamMock();
        Team team = new Team(1, teamFactory);
        teamList.add(team);

        team = new Team(3, teamFactory);
        teamList.add(team);

        return teamList;
    }

    public List<ITeam> formCreateTeamTeamList() throws Exception {
        List<ITeam> teamList = new ArrayList<>();

        ITeamDao teamFactory = new TeamMock();
        Team team = new Team(1, teamFactory);
        teamList.add(team);
        return teamList;

    }

    @Override
    public int addDivision(IDivision division) throws Exception {
        division = new Division(1);
        return division.getId();
    }

    @Override
    public void loadDivisionById(int id, IDivision division) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                division.setName("Division1");
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;

            case 2:
                division.setName(null);
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;

            case 3:
                division.setName("Invalid Date");
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;
            case 4:
                division.setName("Division4");
                division.setConferenceId(1);
                division.setTeamList(formCreateTeamTeamList());
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
    public List<IDivision> loadDivisionListByConferenceId(int conferenceId) throws Exception {
        ConferenceMock loadConferenceMock = new ConferenceMock();
        return loadConferenceMock.formDivisionList();
    }

}
