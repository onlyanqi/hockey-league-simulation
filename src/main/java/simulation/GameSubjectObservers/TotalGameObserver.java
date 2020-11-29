package simulation.GameSubjectObservers;

import org.apache.log4j.Logger;
import simulation.model.ILeague;
import simulation.model.TeamStat;

public class TotalGameObserver implements IGameObserver {

    static Logger log = Logger.getLogger(TotalGameObserver.class);

    public void update(ILeague league, String team, Integer count) {
        if(league == null || team == null){
            log.error("league or team are null. Please correct them");
            throw new IllegalArgumentException("league or team are null. Please correct them");
        }
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setNumberOfGamesPlayed(teamStats.getNumberOfGamesPlayed()+1);
    }
}
