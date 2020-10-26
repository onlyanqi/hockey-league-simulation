package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.League;

public class AdvanceNextSeasonState implements ISimulateState {

    private League league;
    private HockeyContext hockeyContext;

    public AdvanceNextSeasonState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {

        NHLEvents nhlEvents = league.getNhlRegularSeasonEvents();

        while(league.getCurrentDate() != nhlEvents.nextSeasonDate){
            //Perform aging
        }
        return exit();
    }

    private ISimulateState exit() {
        return new PersistState(hockeyContext);
    }
}
