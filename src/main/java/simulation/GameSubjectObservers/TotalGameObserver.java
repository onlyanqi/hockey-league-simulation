package simulation.GameSubjectObservers;

import simulation.model.ILeague;
import simulation.model.TeamStat;

public class TotalGameObserver implements IGameObserver {
    public void update(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setNumberOfGamesPlayed(teamStats.getNumberOfGamesPlayed()+1);
    }
}
