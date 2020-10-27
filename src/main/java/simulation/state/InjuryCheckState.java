package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.*;

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

    private void playerInjuryCheck(League league) {
        List<Conference> conferenceList=league.getConferenceList();
        List<Player> freeAgentList=league.getFreeAgent().getPlayerList();
        for(Conference conference : conferenceList){
            List<Division> divisionList = conference.getDivisionList();
            for(Division division : divisionList){
                List<Team> teamList = division.getTeamList();
                for(Team team : teamList){
                    List<Player> playerList = team.getPlayerList();
                    for(Player teamPlayer : playerList){
                        teamPlayer.injuryCheck(league);
                    }
                }
            }
        }
        for(Player freeAgentPlayer : freeAgentList){
            freeAgentPlayer.injuryCheck(league);
        }
    }

    private ISimulateState exit() {
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();

        //Mani, delete the game from the gamesFromCurrentDay List.
        Games games = league.getGames();
        List<Game> gamesOnCurrentDay = games.getGamesOnDate(league.getCurrentDate());
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
