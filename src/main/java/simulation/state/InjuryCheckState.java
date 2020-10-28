package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.Game;
import simulation.model.Games;
import simulation.model.League;

import java.util.List;

public class InjuryCheckState implements ISimulateState {

    private HockeyContext hockeyContext;
    private League league;

    public InjuryCheckState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {

        //Mani, do the injury check here. Pass in the game if required.
        System.out.println("Injury Check!");
        return exit();
    }

    private ISimulateState exit() {
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();

        //Mani, delete the game from the gamesFromCurrentDay List.
        Games games = league.getGames();
        List<Game> gamesOnCurrentDay = games.getUnplayedGamesOnDate(league.getCurrentDate());
        if(gamesOnCurrentDay.size()!=0){
            return new SimulateGameState(hockeyContext);
        }else{
            if(nhlEvents.isTradeDeadlinePassed(league.getCurrentDate())){
                return new AgingState(hockeyContext);
            }else{
                return new ExecuteTradeState(hockeyContext);
            }
        }
    }
}
