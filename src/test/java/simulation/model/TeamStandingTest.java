package simulation.model;


import db.data.ILeagueDao;
import db.data.ITeamScoreDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.mock.TeamScoreMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TeamStandingTest {

    private static ITeamScoreDao iTeamScoreDao;
    private static ILeagueDao iLeagueDao;

    @BeforeClass
    public static void setFactoryObj() {
        iTeamScoreDao = new TeamScoreMock();
        iLeagueDao = new LeagueMock();
    }

    @Test
    public void defaultConstructorTest() {
        TeamStanding teamStanding = new TeamStanding();
        assertNotNull(teamStanding.getTeamsScoreList());
    }

    @Test
    public void initializeTeamStandingsTest() throws Exception {

        TeamScore teamScore = new TeamScore(1, iTeamScoreDao);
        assertEquals(teamScore.getId(), 1);

        TeamStanding teamStanding = new TeamStanding();
        List<String> teamList = new ArrayList<>();
        teamList.add("Team1");
        teamStanding.initializeTeamStandings(teamList);
        assertTrue(teamStanding.getTeamsScoreList().size() != 0);
    }

}
