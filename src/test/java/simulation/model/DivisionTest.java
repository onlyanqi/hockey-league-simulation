package simulation.model;

import simulation.dao.IDaoFactory;
import simulation.dao.IDivisionDao;
import simulation.dao.ITeamDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcreteMock;
import simulation.factory.IHockeyContextFactory;
import simulation.state.IHockeyContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DivisionTest {

    private static IDaoFactory daoFactory;
    private static IDivisionDao divisionDao;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;
    private static IModelFactory modelFactory;

    @BeforeClass
    public static void setFactoryObj() {
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        modelFactory = hockeyContext.getModelFactory();
        daoFactory = hockeyContext.getDaoFactory();
        divisionDao = daoFactory.newDivisionDao();
    }

    @Test
    public void defaultConstructorTest() {
        IDivision division = modelFactory.newDivision();
        assertNotEquals(division.getId(), 0);
    }

    @Test
    public void divisionTest() {
        IDivision division = modelFactory.newDivisionWithId(1);
        assertEquals(division.getId(), 1);
    }

    @Test
    public void divisionFactoryTest() throws Exception {
        IDivision division = modelFactory.newDivisionWithIdDao(1, divisionDao);
        assertEquals(division.getId(), 1);
        assertEquals(division.getName(), "Division1");

        division = modelFactory.newDivisionWithIdDao(2, divisionDao);
        assertNull(division.getName());
    }

    @Test
    public void getConferenceIdTest() throws Exception {
        IDivision division = modelFactory.newDivisionWithIdDao(1, divisionDao);
        assertTrue(division.getConferenceId() == (1));
    }

    @Test
    public void setConferenceIdTest() {
        IDivision division = modelFactory.newDivision();
        int conferenceId = 1;
        division.setConferenceId(conferenceId);
        assertTrue(division.getConferenceId() == conferenceId);
    }

    @Test
    public void getTeamListTest() throws Exception {
        IDivision division = modelFactory.newDivisionWithIdDao(1, divisionDao);
        List<ITeam> teamList = division.getTeamList();
        assertNotNull(teamList);
        assertEquals(teamList.get(0).getId(), (1));
        assertEquals(teamList.get(1).getId(), (3));
        assertEquals(teamList.get(0).getName(), ("Team1"));
    }

    @Test
    public void setPlayerListTest() throws Exception {
        ITeamDao teamDao = daoFactory.newTeamDao();
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = modelFactory.newTeamWithIdDao(1, teamDao);
        teamList.add(team);
        team = modelFactory.newTeamWithIdDao(2, teamDao);
        teamList.add(team);

        IDivision division = modelFactory.newDivision();
        division.setTeamList(teamList);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (2));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
        assertNull(division.getTeamList().get(1).getName());
    }

    @Test
    public void addDivisionTest() throws Exception {
        IDivision division = modelFactory.newDivision();
        division.setId(1);
        division.setName("Division1");
        division.addDivision(divisionDao);
        assertTrue(1 == division.getId());
        assertTrue("Division1".equals(division.getName()));
    }

    @Test
    public void loadTeamListByDivisionIdTest() throws Exception {
        IDivision division = modelFactory.newDivisionWithId(1);
        ITeamDao teamDao = daoFactory.newTeamDao();
        division.loadTeamListByDivisionId(teamDao);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (3));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
    }

}
