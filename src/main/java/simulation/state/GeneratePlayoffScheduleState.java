package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.*;

import java.util.ArrayList;

public class GeneratePlayoffScheduleState implements  ISimulateState{

    private HockeyContext hockeyContext;
    private League league;
    private TeamStanding teamStanding;

    public GeneratePlayoffScheduleState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {

        ArrayList<String> winnerList = new ArrayList<>();
        NHLEvents nhlEvents = new NHLEvents();

        if(nhlEvents.isEndOfRegularSeason(league.getCurrentDate())){
            generatePlayOffFirstRoundSchedule();
        }
        if(nhlEvents.isRegularSeasonPassed(league.getCurrentDate()) && league.getGames().getGameList().size() == 0 && winnerList.size()!=2){
            generatePlayOffOtherRoundSchedule();
        }
        if(nhlEvents.isRegularSeasonPassed(league.getCurrentDate()) && league.getGames().getGameList().size() == 0 && winnerList.size()==2){
            generateStanleyCupSchedule();
        }
        System.out.println("Generating Play off Schedule");

        return exit();
    }

    private void generatePlayOffFirstRoundSchedule() {
        teamStanding = new TeamStanding(league.getRegularSeasonScoreBoard());

    }

//    private boolean mani(){
//        for(Conference conference: league.getConferenceList()){
//            for(Division division: conference.getDivisionList()){
//                teamStanding.getTeamsRankAcrossDivision(league,division.getName());
//            }
//        }
//    }

    private void generatePlayOffOtherRoundSchedule() {

    }

    private void generateStanleyCupSchedule() {

    }



    

    private ISimulateState exit() {
        return new TrainingState(hockeyContext);
    }
}
