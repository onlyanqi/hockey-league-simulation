package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IDaoFactory;
import persistance.dao.IDivisionDao;
import persistance.dao.ITeamDao;
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
        divisionDao = daoFactory.createDivisionDao();
    }

    @Test
    public void defaultConstructorTest() {
        IDivision division = modelFactory.createDivision();
        assertNotEquals(division.getId(), 0);
    }

    @Test
    public void divisionTest() {
        IDivision division = modelFactory.createDivisionWithId(1);
        assertEquals(division.getId(), 1);
    }

    @Test
    public void divisionFactoryTest() throws Exception {
        IDivision division = modelFactory.createDivisionWithIdDao(1, divisionDao);
        assertEquals(division.getId(), 1);
        assertEquals(division.getName(), "Division1");

        division = modelFactory.createDivisionWithIdDao(2, divisionDao);
        assertNull(division.getName());
    }

    @Test
    public void getTeamListTest() throws Exception {
        IDivision division = modelFactory.createDivisionWithIdDao(1, divisionDao);
        List<ITeam> teamList = division.getTeamList();
        assertNotNull(teamList);
        assertEquals(teamList.get(0).getId(), (1));
        assertEquals(teamList.get(1).getId(), (3));
        assertEquals(teamList.get(0).getName(), ("Team1"));
    }

    @Test
    public void setTeamListTest() throws Exception {
        ITeamDao teamDao = daoFactory.createTeamDao();
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = modelFactory.createTeamWithIdDao(1, teamDao);
        teamList.add(team);
        team = modelFactory.createTeamWithIdDao(2, teamDao);
        teamList.add(team);

        IDivision division = modelFactory.createDivision();
        division.setTeamList(teamList);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (2));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
        assertNull(division.getTeamList().get(1).getName());
    }

    @Test
    public void getConferenceIdTest() throws Exception {
        IDivision division = modelFactory.createDivisionWithIdDao(1, divisionDao);
        assertTrue(division.getConferenceId() == (1));
    }

    @Test
    public void setConferenceIdTest() {
        IDivision division = modelFactory.createDivision();
        int conferenceId = 1;
        division.setConferenceId(conferenceId);
        assertTrue(division.getConferenceId() == conferenceId);
    }

    @Test
    public void addDivisionTest() throws Exception {
        IDivision division = modelFactory.createDivision();
        division.setId(1);
        division.setName("Division1");
        division.addDivision(divisionDao);
        assertTrue(1 == division.getId());
        assertTrue("Division1".equals(division.getName()));
    }

    @Test
    public void loadTeamListByDivisionIdTest() throws Exception {
        IDivision division = modelFactory.createDivisionWithId(1);
        ITeamDao teamDao = daoFactory.createTeamDao();
        division.loadTeamListByDivisionId(teamDao);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (3));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
    }

    @Test
    public void getTeamNameListTest() throws Exception {
        ITeamDao teamDao = daoFactory.createTeamDao();
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = modelFactory.createTeamWithIdDao(1, teamDao);
        teamList.add(team);
        team = modelFactory.createTeamWithIdDao(3, teamDao);
        teamList.add(team);
        IDivision division = modelFactory.createDivision();
        division.setTeamList(teamList);
        List<String> teamNameList = division.getTeamNameList();
        String indexZero = "team1";
        assertEquals(teamNameList.get(0), indexZero);
        assertNotNull(teamNameList.get(0));
    }

}
