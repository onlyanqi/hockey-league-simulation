package simulation.state;

import simulation.model.ILeague;

import java.time.LocalDate;
import java.time.Month;

public class SeasonSimulationState implements IHockeyState {

    private IHockeyContext hockeyContext;
    private ILeague league;
    private IHockeyState hockeyState;
    private int numberOfSeasons;

    public SeasonSimulationState(IHockeyContext hockeyContext, int numberOfSeasons) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.numberOfSeasons = numberOfSeasons;
    }

    @Override
    public void entry() throws Exception {
        hockeyState = new InternalState(hockeyContext);
    }

    @Override
    public void process() throws Exception {
        league.setCurrentDate(LocalDate.of(LocalDate.now().getYear(), Month.SEPTEMBER, 29));
        while (numberOfSeasons > 0) {
            IHockeyState hockeyState = new InternalState(hockeyContext);
            hockeyState.entry();
            hockeyState.process();
            numberOfSeasons--;
        }
    }

    @Override
    public IHockeyState exit() {
        return null;
    }
}
