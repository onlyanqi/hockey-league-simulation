package simulation.GamePublisherSubscriber;

import simulation.model.*;

public class GameSubscriber implements IGoalSubscriber,IPenaltyObserver,ISaveSubscriber,IShotSubscriber,ITotalGameSub{


    public GameSubscriber() {
    }

    @Override
    public void updateGoal(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setGoals(teamStats.getGoals()+count);
    }

    @Override
    public void updatePenalty(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setPenalties(teamStats.getPenalties()+count);
    }

    @Override
    public void updateSave(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setSaves(teamStats.getSaves()+count);
    }

    @Override
    public void updateShot(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setShots(teamStats.getShots()+count);
    }

    @Override
    public void updateTotalGames(ILeague league, String team, Integer count) {
        TeamStat teamStats = league.getTeamStatByTeamName(team);
        teamStats.setNumberOfGamesPlayed(teamStats.getNumberOfGamesPlayed()+1);
    }
}
