package simulation.mock;

import simulation.dao.ITeamScoreDao;
import simulation.dao.ITeamStandingDao;
import simulation.model.ITeamScore;
import simulation.model.ITeamStanding;
import simulation.model.TeamScore;
import simulation.state.HockeyContext;

import java.util.ArrayList;
import java.util.List;

public class TeamStandingMock implements ITeamStandingDao {
    @Override
    public long addTeamStanding(int leagueId, ITeamStanding teamStanding) throws Exception {
        loadTeamStandingById(1, teamStanding);
        teamStanding.setId(1);
        return teamStanding.getId();
    }

    @Override
    public void loadTeamStandingById(int id, ITeamStanding teamStanding) throws Exception {
        switch (id) {
            case 1:
                List<ITeamScore> teamScoreList = new ArrayList<>();

                ITeamScoreDao teamScoreFactory = new TeamScoreMock();

                for (int i = 0; i < 4; i++) {
                    ITeamScore teamScore = new TeamScore(i, teamScoreFactory);
                    teamScoreList.add(teamScore);
                }
                teamStanding.setTeamsScoreList(teamScoreList);
        }
    }

    @Override
    public ITeamStanding loadTeamStandingByLeagueId(int leagueId) throws Exception {
        ITeamStanding teamStanding = HockeyContext.getInstance().getModelFactory().newTeamStanding();
        switch (leagueId) {
            case 1:
                List<ITeamScore> teamScoreList = new ArrayList<>();

                ITeamScoreDao teamScoreFactory = new TeamScoreMock();

                for (int i = 0; i < 4; i++) {
                    ITeamScore teamScore = new TeamScore(i, teamScoreFactory);
                    teamScoreList.add(teamScore);
                }
                teamStanding.setTeamsScoreList(teamScoreList);
        }
        return teamStanding;
    }
}
