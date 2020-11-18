package simulation.model;

import db.data.IDivisionFactory;
import db.data.ITeamFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.DivisionMock;
import simulation.mock.TeamMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DivisionTest {

    private static IDivisionFactory loadDivisionFactory;

    @BeforeClass
    public static void setFactoryObj() {
        loadDivisionFactory = new DivisionMock();
    }

    @Test
    public void defaultConstructorTest() {
        Division division = new Division();
        assertNotEquals(division.getId(), 0);
    }

    @Test
    public void divisionTest() {
        Division division = new Division(1);
        assertEquals(division.getId(), 1);
    }

    @Test
    public void divisionFactoryTest() throws Exception {
        Division division = new Division(1, loadDivisionFactory);
        assertEquals(division.getId(), 1);
        assertEquals(division.getName(), "Division1");

        division = new Division(2, loadDivisionFactory);
        assertNull(division.getName());
    }

    @Test
    public void getConferenceIdTest() throws Exception {
        Division division = new Division(1, loadDivisionFactory);
        assertTrue(division.getConferenceId() == (1));
    }

    @Test
    public void setConferenceIdTest() {
        Division division = new Division();
        int conferenceId = 1;
        division.setConferenceId(conferenceId);
        assertTrue(division.getConferenceId() == conferenceId);
    }

    @Test
    public void getTeamListTest() throws Exception {
        Division division = new Division(1, loadDivisionFactory);
        List<Team> teamList = division.getTeamList();
        assertNotNull(teamList);
        assertEquals(teamList.get(0).getId(), (1));
        assertEquals(teamList.get(1).getId(), (3));
        assertEquals(teamList.get(0).getName(), ("Team1"));
    }

    @Test
    public void setPlayerListTest() throws Exception {
        ITeamFactory teamFactory = new TeamMock();
        List<Team> teamList = new ArrayList<>();
        Team team = new Team(1, teamFactory);
        teamList.add(team);
        team = new Team(2, teamFactory);
        teamList.add(team);

        Division division = new Division();
        division.setTeamList(teamList);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (2));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
        assertNull(division.getTeamList().get(1).getName());
    }

    @Test
    public void addDivisionTest() throws Exception {
        Division division = new Division();
        division.setId(1);
        division.setName("Division1");
        division.addDivision(loadDivisionFactory);
        assertTrue(1 == division.getId());
        assertTrue("Division1".equals(division.getName()));
    }

    @Test
    public void loadTeamListByDivisionIdTest() throws Exception {
        Division division = new Division(1);
        ITeamFactory loadTeamFactory = new TeamMock();
        division.loadTeamListByDivisionId(loadTeamFactory);

        assertTrue(division.getTeamList().get(0).getId() == (1));
        assertTrue(division.getTeamList().get(1).getId() == (3));
        assertTrue(division.getTeamList().get(0).getName().equals("Team1"));
    }

}
