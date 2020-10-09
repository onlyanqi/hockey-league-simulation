package factory;

import dao.AddTeamDao;
import dao.LoadTeamDao;
import data.IAddTeamFactory;
import data.ILoadTeamFactory;
import model.Team;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TeamConcreteTest {

    private TeamConcrete teamConcrete;

    @Before
    public void init(){
        teamConcrete = new TeamConcrete();
    }

    @Test
    public void newTeamTest(){
        assertTrue(teamConcrete.newTeam() instanceof Team);
    }

    @Test
    public void newTeamByNameTest() throws Exception {
        String name = "Name";
        ILoadTeamFactory loadTeamFactory = teamConcrete.newLoadTeamFactory();
        Team team = teamConcrete.newTeamByName(name, loadTeamFactory);
        assertTrue(team instanceof Team);
    }

    @Test
    public void newLoadTeamFactoryTest(){
        assertTrue(teamConcrete.newLoadTeamFactory() instanceof LoadTeamDao);
    }

    @Test
    public void newAddTeamFactoryTest(){
        assertTrue(teamConcrete.newAddTeamFactory() instanceof AddTeamDao);
    }

}
