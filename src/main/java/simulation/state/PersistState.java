package simulation.state;

import simulation.model.*;

import java.util.List;

public class PersistState implements ISimulateState{

    private HockeyContext hockeyContext;
    private League league;

    public PersistState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        System.out.println("Saving league to DB");
        return exit();
    }

    private ISimulateState exit() {
        if(stanleyCupWinnerDetermined()){
            List<TeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
            for(TeamScore teamscore : teamScoreList){
                if(teamscore.getNumberOfWins() ==1 ){
                    System.out.println(teamscore.getTeamName() +" won the stanley cup!");
                }
            }

            return null;
        }else{
            return new AdvanceTimeState(hockeyContext);
        }
    }
    public Boolean stanleyCupWinnerDetermined(){
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        Games games = league.getGames();
        TeamStanding teamStanding = league.getActiveTeamStanding();
        if(nhlEvents.checkRegularSeasonPassed(league.getCurrentDate()) && games.doGamesDoesNotExistAfterDate(league.getCurrentDate()) && teamStanding.getTeamsScoreList().size() == 2 ){
            return true;
        }
        return false;
    }
}
