package simulation.state;

import persistance.serializers.LeagueDataSerializerDeSerializer;
import presentation.ConsoleOutput;
import simulation.model.IGameSchedule;
import simulation.model.ILeague;
import simulation.model.INHLEvents;
import simulation.model.ITeamStanding;

public class PersistState implements ISimulateState {

    private final IHockeyContext hockeyContext;
    private final ILeague league;
    private final INHLEvents nhlEvents;

    public PersistState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.nhlEvents = league.getNHLRegularSeasonEvents();
    }

    @Override
    public ISimulateState action() throws Exception {
        ConsoleOutput.getInstance().printMsgToConsole("Saving... Please wait");
        saveToPersistence();
        return exit();
    }

    private void saveToPersistence() {
        LeagueDataSerializerDeSerializer serializerDeSerializer = new LeagueDataSerializerDeSerializer();
        serializerDeSerializer.serialize(league);
    }

    private ISimulateState exit() {
        if (stanleyCupWinnerDetermined()) {
            return null;
        } else {
            return new AdvanceTimeState(hockeyContext);
        }
    }

    public Boolean stanleyCupWinnerDetermined() {
        IGameSchedule games = league.getGames();
        ITeamStanding teamStanding = league.getActiveTeamStanding();
        return nhlEvents.checkRegularSeasonPassed(league.getCurrentDate())
                && games.doGamesDoesNotExistAfterDate(league.getCurrentDate())
                && teamStanding.getTeamsScoreList().size() == 2;
    }
}
