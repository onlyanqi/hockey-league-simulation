package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;
import java.util.HashMap;
import java.util.List;

public class AgingState implements ISimulateState {

    public static final String AGING_DAY = "Aging all players by one day!";
    private IHockeyContext hockeyContext;
    private ILeague league;
    private IAging aging;


    public AgingState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.aging = league.getGamePlayConfig().getAging();
    }

    @Override
    public ISimulateState action() {
        ConsoleOutput.getInstance().printMsgToConsole(AGING_DAY);
        aging.agingPlayerDay(league);
        aging.agingPlayerRetirement(league);
        return exit();
    }



    private ISimulateState exit() {
        if (stanleyCupWinnerDetermined()) {
            updateTeamScoreList();
            return new DraftState(hockeyContext, league.getCurrentDate());
        } else {
            return new PersistState(hockeyContext);
        }
    }

    private void updateTeamScoreList() {
        HashMap<String, Integer> stanleyCupTeamStanding = league.getStanleyCupFinalsTeamScores();
        List<ITeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
        for (ITeamScore teamScore : teamScoreList) {
            stanleyCupTeamStanding.put(teamScore.getTeamName(), stanleyCupTeamStanding.get(teamScore.getTeamName()) + teamScore.getPoints());
        }
    }

    public Boolean stanleyCupWinnerDetermined() {
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        IGameSchedule games = league.getGames();
        ITeamStanding teamStanding = league.getActiveTeamStanding();
        if (nhlEvents.checkRegularSeasonPassed(league.getCurrentDate())
                && games.doGamesDoesNotExistAfterDate(league.getCurrentDate()) && teamStanding.getTeamsScoreList().size() == 2) {
            return true;
        }
        return false;
    }
}
