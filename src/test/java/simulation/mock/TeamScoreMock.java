package simulation.mock;

import simulation.dao.ITeamDao;
import simulation.dao.ITeamScoreDao;
import simulation.model.ITeamScore;
import simulation.model.Team;
import simulation.model.TeamScore;

import java.util.List;

public class TeamScoreMock implements ITeamScoreDao {



    @Override
    public long addTeamScore(int leagueId, int regularSeason, ITeamScore teamScore) throws Exception {
        ITeamDao teamFactory = new TeamMock();
        Team team = new Team(1, teamFactory);
        teamScore.setId(0);
        teamScore.setPoints(10);
        teamScore.setNumberOfWins(5);
        teamScore.setNumberOfLoss(10);
        teamScore.setNumberOfTies(0);
        teamScore.setTeam(team);
        return teamScore.getId();
    }

    @Override
    public void loadTeamScoreById(int id, ITeamScore teamScore) throws Exception {
        switch (id) {
            case 0:
                Team team0 = new Team();
                team0.setName("Team0");
                teamScore.setId(id);
                teamScore.setTeamName(team0.getName());
                teamScore.setTeam(team0);
                teamScore.setNumberOfTies(1);
                teamScore.setNumberOfLoss(5);
                teamScore.setNumberOfWins(6);
                teamScore.setPoints(14);
                break;
            case 1:
                Team team1 = new Team();
                team1.setName("Team1");
                teamScore.setId(id);
                teamScore.setTeamName(team1.getName());
                teamScore.setTeam(team1);
                teamScore.setNumberOfTies(0);
                teamScore.setNumberOfLoss(5);
                teamScore.setNumberOfWins(6);
                teamScore.setPoints(12);
                break;
            case 2:
                Team team2 = new Team();
                team2.setName("Team2");
                teamScore.setId(id);
                teamScore.setTeamName(team2.getName());
                teamScore.setTeam(team2);
                teamScore.setNumberOfTies(0);
                teamScore.setNumberOfLoss(0);
                teamScore.setNumberOfWins(2);
                teamScore.setPoints(4);
                break;
            case 3:
                teamScore.setId(id);
                Team team = new Team();
                teamScore.setTeam(team);
                teamScore.setNumberOfTies(0);
                teamScore.setNumberOfLoss(0);
                teamScore.setNumberOfWins(0);
                teamScore.setPoints(0);
                break;
        }
    }

    @Override
    public List<ITeamScore> loadRegularTeamScoreListByLeagueId(int leagueId) throws Exception {
        List<ITeamScore> teamScoreList = null;
        switch (leagueId) {
            case 0:
                TeamScore teamScore = new TeamScore();
                Team team3 = new Team();
                team3.setName("Team3");
                teamScore.setTeam(team3);
                teamScore.setId(4);
                teamScore.setNumberOfTies(0);
                teamScore.setNumberOfLoss(5);
                teamScore.setNumberOfWins(6);
                teamScore.setPoints(12);

                TeamScore teamScore2 = new TeamScore();
                Team team4 = new Team();
                team4.setName("Team4");
                teamScore2.setTeam(team4);
                teamScore2.setId(5);
                teamScore2.setNumberOfTies(0);
                teamScore2.setNumberOfLoss(5);
                teamScore2.setNumberOfWins(7);
                teamScore2.setPoints(14);

                TeamScore teamScore3 = new TeamScore();
                Team team5 = new Team();
                team5.setName("Team5");
                teamScore3.setTeam(team5);
                teamScore3.setId(6);
                teamScore3.setNumberOfTies(0);
                teamScore3.setNumberOfLoss(6);
                teamScore3.setNumberOfWins(8);
                teamScore3.setPoints(16);

                teamScoreList.add(teamScore);
                teamScoreList.add(teamScore2);
                teamScoreList.add(teamScore3);
                break;
        }
        return teamScoreList;
    }

    @Override
    public List<ITeamScore> loadPlayoffTeamScoreListByLeagueId(int leagueId) throws Exception {
        List<ITeamScore> teamScoreList = null;
        switch (leagueId) {
            case 0:
                TeamScore teamScore = new TeamScore();
                teamScore.setId(4);
                Team team6 = new Team();
                team6.setName("Team6");
                teamScore.setTeam(team6);
                teamScore.setNumberOfTies(0);
                teamScore.setNumberOfLoss(5);
                teamScore.setNumberOfWins(6);
                teamScore.setPoints(12);

                TeamScore teamScore2 = new TeamScore();
                teamScore2.setId(5);
                Team team7 = new Team();
                team7.setName("Team7");
                teamScore2.setTeam(team7);
                teamScore2.setNumberOfTies(0);
                teamScore2.setNumberOfLoss(5);
                teamScore2.setNumberOfWins(7);
                teamScore2.setPoints(14);

                TeamScore teamScore3 = new TeamScore();
                teamScore3.setId(6);
                Team team8 = new Team();
                team8.setName("Team8");
                teamScore3.setTeam(team8);
                teamScore3.setNumberOfTies(0);
                teamScore3.setNumberOfLoss(6);
                teamScore3.setNumberOfWins(8);
                teamScore3.setPoints(16);

                teamScoreList.add(teamScore);
                teamScoreList.add(teamScore2);
                teamScoreList.add(teamScore3);
                break;
        }
        return teamScoreList;
    }

    @Override
    public void updateTeamScoreById(ITeamScore teamScore) {
        teamScore.setId(teamScore.getId());
        teamScore.setPoints(teamScore.getPoints());
        teamScore.setNumberOfWins(teamScore.getNumberOfWins());
        teamScore.setNumberOfLoss(teamScore.getNumberOfLoss());
        teamScore.setNumberOfTies(teamScore.getNumberOfTies());
        teamScore.setTeam(teamScore.getTeam());
    }
}
