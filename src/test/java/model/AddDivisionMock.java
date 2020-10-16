package model;

import simulation.data.IAddDivisionFactory;
import simulation.data.ILoadTeamFactory;
import simulation.model.Division;
import simulation.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AddDivisionMock implements IAddDivisionFactory {

    public List formTeamList() throws Exception {
        List<Team> teamList = new ArrayList<>();

        ILoadTeamFactory teamFactory = new LoadTeamMock();
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
}
