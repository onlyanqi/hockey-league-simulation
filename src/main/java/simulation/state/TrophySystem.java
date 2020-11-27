package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

public class TrophySystem implements ISimulateState{
    private IHockeyContext hockeyContext;
    private ILeague league;
    private ConsoleOutput consoleOutput;
    private ITrophy trophy;

    TrophySystem(IHockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
        consoleOutput = ConsoleOutput.getInstance();
    }

    TrophySystem(IHockeyContext hockeyContext,ITrophy trophy){
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
        consoleOutput = ConsoleOutput.getInstance();
        this.trophy = trophy;
    }


    private List<ITeamScore> getSortedTeamScores(){
        List<ITeam> teamList = league.createTeamList();
        ITeamStanding teamStanding= league.getRegularSeasonStanding();
        List<ITeamScore> teamScores = teamStanding.getTeamsRankAcrossLeague(league);
        return teamScores;
    }

    private String calculateJackAdamsAward(List<ICoach> coachList){
        ICoach winnerCoach = coachList.get(0);
        for (ICoach coach : coachList) {
            if(coach.getCoachingEffectiveness()>winnerCoach.getCoachingEffectiveness()){
                winnerCoach=coach;
            }
        }
        return winnerCoach.getName();
    }

    private void showHistoricalTrophyList(List<ITrophy> trophyList){
        consoleOutput.printMsgToConsole("Historical Awards List (Most recent to oldest)");
        consoleOutput.printMsgToConsole("------------------------------------------------");
        consoleOutput.printMsgToConsole("------------------------------------------------\n");

        for(int i=trophyList.size()-1 ; i>=0 ; i--){
            consoleOutput.printMsgToConsole("Awards");
            consoleOutput.printMsgToConsole("----------------------");
            consoleOutput.printMsgToConsole("Presidents Trophy: "+trophyList.get(i).getPresidentsTrophy());
            consoleOutput.printMsgToConsole("Calder Memorial Trophy: "+trophyList.get(i).getCalderMemorialTrophy());
            consoleOutput.printMsgToConsole("Vezina Trophy: "+trophyList.get(i).getVezinaTrophy());
            consoleOutput.printMsgToConsole("Jack Adams Award: "+trophyList.get(i).getJackAdamsAward());
            consoleOutput.printMsgToConsole("Maurice Richard Trophy: "+trophyList.get(i).getMauriceRichardTrophy());
            consoleOutput.printMsgToConsole("Rob Hawkey Memorial Cup: "+trophyList.get(i).getRobHawkeyMemorialCup());
            consoleOutput.printMsgToConsole("Participation Award: "+trophyList.get(i).getParticipationAward());
            consoleOutput.printMsgToConsole("\n----------------------");
        }
    }

    private ISimulateState exit() {
        return new AdvanceNextSeasonState(hockeyContext,league.getCurrentDate());
    }

    @Override
    public ISimulateState action() throws Exception {
        setPresidentsAndParticipationAwards();

        calculateAndSetAwardOfPlayers();

        List<ICoach> coachList = league.createCoachList();
        trophy.setJackAdamsAward(calculateJackAdamsAward(coachList));

        league.setTrophy(trophy);
        List<ITrophy> trophyList = league.getHistoricalTrophyList();
        trophyList.add(trophy);

        showHistoricalTrophyList(trophyList);
        return exit();
    }

    private void calculateAndSetAwardOfPlayers() {
        List<IPlayer> playerList = league.createPlayerList();
        IPlayer mauriceRichardTrophyWinnerPlayer = playerList.get(0);
        IPlayer robHawkeyMemoriaCupWinnerPlayer = playerList.get(0);
        IPlayer vezinaTrophyWinnerPlayer = playerList.get(0);
        for(IPlayer player : playerList){
            if(player.getSaves()>vezinaTrophyWinnerPlayer.getSaves()){
                vezinaTrophyWinnerPlayer = player;
            }
            if(player.getGoalScore()>mauriceRichardTrophyWinnerPlayer.getGoalScore()){
                mauriceRichardTrophyWinnerPlayer = player;
            }
            if(player.getPenaltyCount()>mauriceRichardTrophyWinnerPlayer.getPenaltyCount()){
                robHawkeyMemoriaCupWinnerPlayer = player;
            }
        }
        trophy.setVezinaTrophy(vezinaTrophyWinnerPlayer.getName());
        trophy.setMauriceRichardTrophy(mauriceRichardTrophyWinnerPlayer.getName());
        trophy.setRobHawkeyMemorialCup(robHawkeyMemoriaCupWinnerPlayer.getName());
    }

    private void setPresidentsAndParticipationAwards() {
        List<ITeamScore> teamScores= getSortedTeamScores();
        trophy = hockeyContext.getModelFactory().newTrophy();
        trophy.setPresidentsTrophy(teamScores.get(teamScores.size()-1).getTeamName());
        trophy.setParticipationAward(teamScores.get(0).getTeamName());
    }
}
