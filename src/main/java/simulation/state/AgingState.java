package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.Games;
import simulation.model.League;
import simulation.model.TeamStanding;

public class AgingState implements ISimulateState {

    private HockeyContext hockeyContext;
    private League league;

    public AgingState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        System.out.println("Aging players!");
        return exit();
    }

    private ISimulateState exit() {
        if(stanleyCupWinnerDetermined()){
            return new AdvanceNextSeasonState(hockeyContext);
        }else{
            return new PersistState(hockeyContext);
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
