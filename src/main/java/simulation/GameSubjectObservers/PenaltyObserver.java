package simulation.GameSubjectObservers;

import simulation.model.ILeague;
import simulation.model.TeamStat;

public class PenaltyObserver implements IGameObserver{
    public void update(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setPenalties(teamStats.getPenalties()+count);
    }
}
