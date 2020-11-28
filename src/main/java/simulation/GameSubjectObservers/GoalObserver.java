package simulation.GameSubjectObservers;

import simulation.model.ILeague;
import simulation.model.TeamStat;

public class GoalObserver implements IGameObserver {

    public void update(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setGoals(teamStats.getGoals()+count);
    }
}
