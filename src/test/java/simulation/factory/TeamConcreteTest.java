package simulation.factory;

import db.dao.TeamDao;
import db.data.ITeamFactory;
import org.junit.Before;
import org.junit.Test;
import simulation.mock.TeamMock;
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
        ITeamFactory loadTeamFactory = new TeamMock();
        Team team = teamConcrete.newTeamByName(name, loadTeamFactory);
        assertTrue(team instanceof Team);
    }

    @Test
    public void newLoadTeamFactoryTest() {
        assertTrue(teamConcrete.newTeamFactory() instanceof TeamDao);
    }

    @Test
    public void newAddTeamFactoryTest() {
        assertTrue(teamConcrete.newTeamFactory() instanceof TeamDao);
    }

}
