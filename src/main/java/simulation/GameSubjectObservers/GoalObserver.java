package simulation.GameSubjectObservers;

import org.apache.log4j.Logger;
import simulation.model.ILeague;
import simulation.model.Shift;
import simulation.model.TeamStat;

public class GoalObserver implements IGameObserver {

    static Logger log = Logger.getLogger(GoalObserver.class);

    public void update(ILeague league, String team, Integer count) {
        if(league == null || team == null){
            log.error("league or team are null. Please correct them");
            throw new IllegalArgumentException("league or team are null. Please correct them");
        }
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setGoals(teamStats.getGoals()+count);
    }
}
