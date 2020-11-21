package simulation.factory;

import db.data.ITeamDao;
import org.junit.Before;
import org.junit.Test;
import simulation.mock.TeamMock;
import simulation.model.ITeam;
import simulation.model.Team;

import static org.junit.Assert.assertTrue;

public class TeamConcreteTest {

    private TeamConcrete teamConcrete;

    @Before
    public void init() {
        teamConcrete = new TeamConcrete();
    }

    @Test
    public void newTeamTest() {
        assertTrue(teamConcrete.newTeam() instanceof Team);
    }

    @Test
    public void newTeamByNameTest() throws Exception {
        String name = "Name";
        ITeamDao loadTeamFactory = new TeamMock();
        ITeam team = teamConcrete.newTeamByName(name, loadTeamFactory);
        assertTrue(team instanceof Team);
    }


}
