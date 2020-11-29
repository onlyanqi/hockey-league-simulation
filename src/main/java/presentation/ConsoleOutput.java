package presentation;

import org.apache.log4j.Logger;
import simulation.model.IPlayer;
import simulation.model.ITeam;
import simulation.model.ITeamScore;
import simulation.model.ITeamStanding;
import simulation.state.ExecuteTradeState;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ConsoleOutput {

    private ConsoleOutput(){}

    private static ConsoleOutput consoleOutput;
    private static Logger log = Logger.getLogger(ExecuteTradeState.class);

    public static ConsoleOutput getInstance() {
        if (null == consoleOutput) {
            consoleOutput = new ConsoleOutput();
        }
        return consoleOutput;
    }

    public void printMsgToConsole(String outString) {
        System.out.println(outString);
        log.info(outString);
    }

    public void printTradeDetailsToUser(Map<String, Object> tradeDetails) {
        String FROMPLAYERLIST = "fromPlayerListAfterTrade";
        String TOPLAYERLIST = "toPlayerList";
        String FROMTEAM = "fromTeam";
        String TOTEAM = "toTeam";
        String TRADEDDRAFTPICKROUNDNUMBER = "tradedRoundNumber";
        String TYPE = "type";
        String STRONG = "strong";
        String WEAK = "weak";
        List<IPlayer> fromPlayerList = (List<IPlayer>) tradeDetails.get(FROMPLAYERLIST);
        List<IPlayer> toPlayerList = (List<IPlayer>) tradeDetails.get(TOPLAYERLIST);
        ITeam fromTeam = (ITeam) tradeDetails.get(FROMTEAM);
        ITeam toTeam = (ITeam) tradeDetails.get(TOTEAM);
        int tradedDraftPickRoundNumber;
        Object nullCheck = tradeDetails.get(TRADEDDRAFTPICKROUNDNUMBER);
        if(nullCheck == null){
            tradedDraftPickRoundNumber = 0;
        } else {
            tradedDraftPickRoundNumber = (int) tradeDetails.get(TRADEDDRAFTPICKROUNDNUMBER);
        }

        printMsgToConsole("--------------------------------------------------------------------");
        printMsgToConsole("\tFrom team: " + fromTeam.getName());
        printMsgToConsole("\t\tTraded players:");
        for(IPlayer player : fromPlayerList){
            printMsgToConsole("\t\t\tPlayer name: " + player.getName());
            printMsgToConsole("\t\t\tPlayer strength: " + player.getStrength());
            printMsgToConsole("\t\t\tPlayer position: "+ player.getPosition());
            printMsgToConsole("\t\t\tPlayer age: "+player.getAge());
            printMsgToConsole("");
        }
        if(STRONG.equalsIgnoreCase((String)tradeDetails.get(TYPE)) && tradedDraftPickRoundNumber == 0){
            printMsgToConsole("\t\t Trade does not include draft picks.");
        } else if (STRONG.equalsIgnoreCase((String)tradeDetails.get(TYPE))){
            printMsgToConsole("\t\t Trade includes draft pick round number: " + tradedDraftPickRoundNumber);
        }
        printMsgToConsole("");
        printMsgToConsole("\tTo Team name: " + toTeam.getName());
        printMsgToConsole("\t\tTraded players:");
        for(IPlayer player : toPlayerList){
            printMsgToConsole("\t\t\tPlayer name: " + player.getName());
            printMsgToConsole("\t\t\tPlayer strength: " + player.getStrength());
            printMsgToConsole("\t\t\tPlayer position: "+player.getPosition());
            printMsgToConsole("\t\t\tPlayer age: "+player.getAge());
            printMsgToConsole("");
        }
        if(WEAK.equalsIgnoreCase((String)tradeDetails.get(TYPE)) && tradedDraftPickRoundNumber == 0){
            printMsgToConsole("\t\t Trade does not include draft picks.");
        } else if (WEAK.equalsIgnoreCase((String)tradeDetails.get(TYPE))){
            printMsgToConsole("\t\t Trade includes draft pick round number: " + tradedDraftPickRoundNumber);
        }
        printMsgToConsole("--------------------------------------------------------------------");
    }

    public static void printTradedDraftInfo(ITeam originalTeam, String tradedToTeamName, IPlayer player) {
        ConsoleOutput.getInstance().printMsgToConsole("Draft "+ player.getName() + "already traded from " + originalTeam.getName() + " to " + tradedToTeamName);
    }

    public void printGameStatsToUser(float goalAvg,float penaltyAvg,float shotAvg,float saveAvg){
        DecimalFormat df = new DecimalFormat("0.00");
        printMsgToConsole("---------------------------------------");
        printMsgToConsole("\n" + "Game Averages" + "\n");
        printMsgToConsole("Game Goal Average for a Team : " + df.format(goalAvg));
        printMsgToConsole("Game Penalty Average for a Team : " + df.format(penaltyAvg));
        printMsgToConsole("Game Shot Average for a Team : " + df.format(shotAvg));
        printMsgToConsole("Game Save Average for a Team : " + df.format(saveAvg));
        printMsgToConsole("---------------------------------------\n");
    }

    public void printTeamGameScore(ITeamStanding teamStanding){
        printMsgToConsole("----------------------------------------------");
        printMsgToConsole("Team Game Stats for Regular Season");
        for(ITeamScore teamScore : teamStanding.getTeamsScoreList()){
            printMsgToConsole(teamScore.getTeam().getName() + " has won " + teamScore.getNumberOfWins() + " games and lost "+ teamScore.getNumberOfLoss() + " games" );
        }
        printMsgToConsole("----------------------------------------------");
    }

}
