package simulation.model;


import db.data.ILeagueFactory;
import db.data.ITeamScoreFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.mock.TeamScoreMock;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TeamStandingTest {

    private static ITeamScoreFactory iTeamScoreFactory;
    private static ILeagueFactory iLeagueFactory;
    @BeforeClass
    public static void setFactoryObj() {
        iTeamScoreFactory = new TeamScoreMock();
        iLeagueFactory = new LeagueMock();
    }

    @Test
    public void defaultConstructorTest() {
        TeamStanding teamStanding = new TeamStanding();
        assertNotNull(teamStanding.getTeamsScoreList());
    }

    @Test
    public void initializeTeamStandingsTest() throws Exception {

        TeamScore teamScore = new TeamScore(1, iTeamScoreFactory);
        assertEquals(teamScore.getId(), 1);

        TeamStanding teamStanding = new TeamStanding();
        List<String> teamList = new ArrayList<>();
        teamList.add("Team1");
        teamStanding.initializeTeamStandings(teamList);
        assertTrue(teamStanding.getTeamsScoreList().size()!=0);
    }

}
