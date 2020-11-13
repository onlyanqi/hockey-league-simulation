package simulation.mock;

import db.data.IGameFactory;
import db.data.ITeamScoreFactory;
import db.data.ITeamStandingFactory;
import simulation.model.Game;
import simulation.model.GameSchedule;
import simulation.model.TeamScore;
import simulation.model.TeamStanding;

import java.util.ArrayList;
import java.util.List;

public class TeamStandingMock implements ITeamStandingFactory {
    @Override
    public long addTeamStanding(int leagueId, TeamStanding teamStanding) throws Exception {
        loadTeamStandingById(1,teamStanding);
        teamStanding.setId(1);
        return teamStanding.getId();
    }

    @Override
    public void loadTeamStandingById(int id, TeamStanding teamStanding) throws Exception {
        switch (id){
            case 1:
                List<TeamScore> teamScoreList = new ArrayList<>();

                ITeamScoreFactory teamScoreFactory = new TeamScoreMock();

                for (int i = 0; i < 4; i++) {
                    TeamScore teamScore = new TeamScore(i, teamScoreFactory);
                    teamScoreList.add(teamScore);
                }
                teamStanding.setTeamsScoreList(teamScoreList);
        }
    }

    @Override
    public TeamStanding loadTeamStandingByLeagueId(int leagueId) throws Exception {
        TeamStanding teamStanding = new TeamStanding();
        switch (leagueId){
            case 1:
                List<TeamScore> teamScoreList = new ArrayList<>();

                ITeamScoreFactory teamScoreFactory = new TeamScoreMock();

                for (int i = 0; i < 4; i++) {
                    TeamScore teamScore = new TeamScore(i, teamScoreFactory);
                    teamScoreList.add(teamScore);
                }
                teamStanding.setTeamsScoreList(teamScoreList);
        }
        return teamStanding;
    }
}
