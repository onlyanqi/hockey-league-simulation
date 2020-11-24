package simulation.model;

import db.data.IDivisionDao;
import db.data.ITeamDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcreteMock;
import simulation.factory.IDivisionFactory;
import simulation.factory.IHockeyContextFactory;
import simulation.factory.ITeamFactory;
import simulation.mock.DivisionMock;
import simulation.mock.TeamMock;
import simulation.state.IHockeyContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DivisionTest {

    private static IDivisionDao divisionDao;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;
    private static IDivisionFactory divisionFactory;

    @BeforeClass
    public static void setFactoryObj() {
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        divisionFactory = hockeyContext.getDivisionFactory();
        divisionDao = divisionFactory.newDivisionDao();
    }

    @Test
    public void defaultConstructorTest() {
        IDivision division = divisionFactory.newDivision();
        assertNotEquals(division.getId(), 0);
    }

    @Test
    public void divisionTest() {
        IDivision division = divisionFactory.newDivisionWithId(1);
        assertEquals(division.getId(), 1);
    }

    @Test
    public void divisionFactoryTest() throws Exception {
        IDivision division = divisionFactory.newDivisionWithIdDao(1, divisionDao);
        assertEquals(division.getId(), 1);
        assertEquals(division.getName(), "Division1");

        division = divisionFactory.newDivisionWithIdDao(2, divisionDao);
        assertNull(division.getName());
    }

    @Test
    public void getConferenceIdTest() throws Exception {
        IDivision division = divisionFactory.newDivisionWithIdDao(1, divisionDao);
        assertTrue(division.getConferenceId() == (1));
    }

    @Test
    public void setConferenceIdTest() {
        IDivision division = divisionFactory.newDivision();
        int conferenceId = 1;
        division.setConferenceId(conferenceId);
        assertTrue(division.getConferenceId() == conferenceId);
    }

    @Test
    public void getTeamListTest() throws Exception {
        IDivision division = divisionFactory.newDivisionWithIdDao(1, divisionDao);
        List<ITeam> teamList = division.getTeamList();
        assertNotNull(teamList);
        assertEquals(teamList.get(0).getId(), (1));
        assertEquals(teamList.get(1).getId(), (3));
        assertEquals(teamList.get(0).getName(), ("Team1"));
    }

    @Test
    public void setPlayerListTest() throws Exception {
        ITeamFactory teamFactory = hockeyContext.getTeamFactory();
        ITeamDao teamDao = teamFactory.newTeamDao();
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = teamFactory.newTeamWithIdDao(1, teamDao);
        teamList.add(team);
        team = teamFactory.newTeamWithIdDao(2, teamDao);
        teamList.add(team);

        IDivision division = divisionFactory.newDivision();
        division.setTeamList(teamList);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (2));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
        assertNull(division.getTeamList().get(1).getName());
    }

    @Test
    public void addDivisionTest() throws Exception {
        IDivision division = divisionFactory.newDivision();
        division.setId(1);
        division.setName("Division1");
        division.addDivision(divisionDao);
        assertTrue(1 == division.getId());
        assertTrue("Division1".equals(division.getName()));
    }

    @Test
    public void loadTeamListByDivisionIdTest() throws Exception {
        IDivision division = divisionFactory.newDivisionWithId(1);
        ITeamFactory teamFactory = hockeyContext.getTeamFactory();
        ITeamDao teamDao = teamFactory.newTeamDao();
        division.loadTeamListByDivisionId(teamDao);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (3));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
    }

}
