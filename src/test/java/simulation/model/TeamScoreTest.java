package simulation.model;

import db.data.ITeamScoreFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.TeamScoreMock;

import static org.junit.Assert.*;

public class TeamScoreTest {
    private static ITeamScoreFactory iTeamScoreFactory;

    @BeforeClass
    public static void setFactoryObj() {
        iTeamScoreFactory = new TeamScoreMock();
    }

    @Test
    public void defaultConstructorTest() {
        TeamScore teamScore = new TeamScore();
        assertNotEquals(teamScore.getId(), 0);
    }

    @Test
    public void teamScoreFactory() throws Exception {
        TeamScore teamScore = new TeamScore(1, iTeamScoreFactory);
        assertEquals(teamScore.getId(), 1);

        TeamScore teamScore2 = new TeamScore(3, iTeamScoreFactory);
        assertEquals(teamScore2.getId(), 3);
        assertNull(teamScore2.getTeamName());
    }

    @Test
    public void getTeamNameTest() throws Exception {
        TeamScore teamScore = new TeamScore(1, iTeamScoreFactory);
        assertEquals(teamScore.getTeamName(), "Team1");

        TeamScore teamScore2 = new TeamScore(3, iTeamScoreFactory);
        assertNull(teamScore2.getTeamName());
    }

    @Test
    public void getPointsTest() throws Exception {
        TeamScore teamScore = new TeamScore(1, iTeamScoreFactory);
        assertTrue(teamScore.getPoints() == 12);

        TeamScore teamScore2 = new TeamScore(3, iTeamScoreFactory);
        assertFalse(teamScore2.getPoints() > 0);
    }

    @Test
    public void getNumberOfWinsTest() throws Exception {
        TeamScore teamScore = new TeamScore(1, iTeamScoreFactory);
        assertTrue(teamScore.getNumberOfWins() == 6);

        TeamScore teamScore2 = new TeamScore(3, iTeamScoreFactory);
        assertFalse(teamScore2.getNumberOfWins() > 0);
    }

    @Test
    public void getNumberOfLossTest() throws Exception {
        TeamScore teamScore = new TeamScore(1, iTeamScoreFactory);
        assertTrue(teamScore.getNumberOfLoss() == 5);

        TeamScore teamScore2 = new TeamScore(3, iTeamScoreFactory);
        assertFalse(teamScore2.getNumberOfLoss() > 0);
    }

    @Test
    public void setTeamNameTest() {
        TeamScore teamScore = new TeamScore();
        teamScore.setTeamName("Temp Team Name");
        assertTrue(teamScore.getTeamName().equals("Temp Team Name"));
    }

    @Test
    public void setTeamPointsTest() {
        TeamScore teamScore = new TeamScore();
        teamScore.setPoints(20);
        assertTrue(teamScore.getPoints().equals(20));
    }

    @Test
    public void setTeamWinsTest() {
        TeamScore teamScore = new TeamScore();
        teamScore.setNumberOfWins(1);
        assertTrue(teamScore.getNumberOfWins().equals(1));
    }

    @Test
    public void setTeamLossTest() {
        TeamScore teamScore = new TeamScore();
        teamScore.setNumberOfLoss(3);
        assertTrue(teamScore.getNumberOfLoss().equals(3));
    }

    @Test
    public void getNumberOfTiesTest() {
        TeamScore teamScore = new TeamScore();
        teamScore.setNumberOfTies(3);
        assertTrue(teamScore.getNumberOfTies().equals(3));
    }
}
