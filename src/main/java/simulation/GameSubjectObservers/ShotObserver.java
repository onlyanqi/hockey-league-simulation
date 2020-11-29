package simulation.GameSubjectObservers;

import org.apache.log4j.Logger;
import simulation.model.ILeague;
import simulation.model.TeamStat;
import simulation.state.gamestatemachine.ShootingState;

public class ShotObserver implements IGameObserver{
    static Logger log = Logger.getLogger(ShootingState.class);

    public void update(ILeague league, String team, Integer count) {
        if(league == null || team == null){
            log.error("league or team are null. Please correct them");
            throw new IllegalArgumentException("league or team are null. Please correct them");
        }
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setShots(teamStats.getShots()+count);
    }
}
