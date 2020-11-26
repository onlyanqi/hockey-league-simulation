package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

public class TrophySystem implements IHockeyState{
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

    @Override
    public void entry() throws Exception {
        List<ITeamScore> teamScores= getSortedTeamScores();
        trophy = hockeyContext.getModelFactory().newTrophy();
        trophy.setPresidentsTrophy(teamScores.get(teamScores.size()-1).getName());
        trophy.setParticipationAward(teamScores.get(0).getName());
        List<IPlayer> playerList = league.createPlayerList();
        List<ICoach> coachList = league.createCoachList();
        trophy.setJackAdamsAward(calculateJackAdamsAward(coachList));
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

    @Override
    public void process() throws Exception {
        league.setTrophy(trophy);
        List<ITrophy> trophyList = league.getHistoricalTrophyList();
        trophyList.add(trophy);

        showHistoricalTrophyList(trophyList);
    }

    private void showHistoricalTrophyList(List<ITrophy> trophyList){
        consoleOutput.printMsgToConsole("Historical Awards List (Most recent to oldest)");
        consoleOutput.printMsgToConsole("------------------------------------------------");
        consoleOutput.printMsgToConsole("------------------------------------------------\n");

        for(int i=trophyList.size()-1 ; i>=0 ; i--){
            consoleOutput.printMsgToConsole("Awards");
            consoleOutput.printMsgToConsole("----------------------");
            consoleOutput.printMsgToConsole("Presidents Trophy"+trophyList.get(i).getPresidentsTrophy());
            consoleOutput.printMsgToConsole("Calder Memorial Trophy"+trophyList.get(i).getCalderMemorialTrophy());
            consoleOutput.printMsgToConsole("Vezina Trophy"+trophyList.get(i).getVezinaTrophy());
            consoleOutput.printMsgToConsole("Jack Adams Award"+trophyList.get(i).getJackAdamsAward());
            consoleOutput.printMsgToConsole("Maurice Richard Trophy"+trophyList.get(i).getMauriceRichardTrophy());
            consoleOutput.printMsgToConsole("Rob Hawkey Memorial Cup"+trophyList.get(i).getRobHawkeyMemorialCup());
            consoleOutput.printMsgToConsole("Participation Award"+trophyList.get(i).getParticipationAward());
            consoleOutput.printMsgToConsole("\n----------------------");
        }
    }

    @Override
    public IHockeyState exit() {
        return null;
    }
}
