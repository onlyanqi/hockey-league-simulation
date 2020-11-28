package simulation.GameSubjectObservers;

import simulation.model.ILeague;
import simulation.model.TeamStat;

public class ShotObserver implements IGameObserver{
    public void update(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setShots(teamStats.getShots()+count);
    }
}
