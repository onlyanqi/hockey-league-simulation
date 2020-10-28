package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.Games;
import simulation.model.League;
import simulation.model.TeamStanding;

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
            return null;
        }else{
            return new AdvanceTimeState(hockeyContext);
        }
    }
    public Boolean stanleyCupWinnerDetermined(){
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        Games games = league.getGames();
        TeamStanding teamStanding = league.getActiveTeamStanding();
        if(nhlEvents.isRegularSeasonPassed(league.getCurrentDate()) && games.doGamesDoesNotExistAfterDate(league.getCurrentDate()) && teamStanding.getTeamsScoreList().size() == 2 ){
            return true;
        }
        return false;
    }
}
