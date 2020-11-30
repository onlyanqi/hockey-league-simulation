package simulation.model;


import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.ILeagueDao;
import simulation.dao.ITeamScoreDao;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.TeamScoreMock;
import simulation.mock.UserMock;
import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TeamStandingTest {

    private static ITeamScoreDao iTeamScoreDao;
    private static ILeagueDao iLeagueDao;

    private static IHockeyContext hockeyContext;
    private static IUserDao userFactory;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
    }

    @BeforeClass
    public static void setFactoryObj() {
        iTeamScoreDao = new TeamScoreMock();
        iLeagueDao = new LeagueMock();
    }

    @Test
    public void defaultConstructorTest() {
        ITeamStanding teamStanding = HockeyContext.getInstance().getModelFactory().newTeamStanding();
        assertNotNull(teamStanding.getTeamsScoreList());
    }

    @Test
    public void initializeTeamStandingsTest() throws Exception {

        TeamScore teamScore = new TeamScore(1, iTeamScoreDao);
        assertEquals(teamScore.getId(), 1);

        ITeamStanding teamStanding = HockeyContext.getInstance().getModelFactory().newTeamStanding();
        List<ITeam> teamList = new ArrayList<>();
        Team team = new Team();
        teamList.add(team);
        teamStanding.initializeTeamStandings(teamList);
        assertTrue(teamStanding.getTeamsScoreList().size() != 0);
    }

    @Test
    public void setTeamPointsTest() throws Exception {
        ITeamStanding teamStanding = HockeyContext.getInstance().getModelFactory().newTeamStanding();
        teamStanding.setTeamPoints("Team0");
        assertNotNull(teamStanding.getTeamsScoreList());
    }

    @Test
    public void setTeamLossTest() throws Exception {
        ITeamStanding teamStanding = HockeyContext.getInstance().getModelFactory().newTeamStanding();
        teamStanding.setTeamLoss("Team0");
        assertNotNull(teamStanding.getTeamsScoreList());
    }

    @Test
    public void setTeamWinsTest() throws Exception {
        ITeamStanding teamStanding = HockeyContext.getInstance().getModelFactory().newTeamStanding();
        teamStanding.setTeamWins("Team0");
        assertNotNull(teamStanding.getTeamsScoreList());
    }

    @Test
    public void setTeamTiesTest() throws Exception {
        ITeamStanding teamStanding = HockeyContext.getInstance().getModelFactory().newTeamStanding();
        teamStanding.setTeamTies("Team0");
        assertNotNull(teamStanding.getTeamsScoreList());
    }

}
