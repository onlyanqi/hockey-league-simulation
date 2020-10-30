package presentation;

import simulation.model.Player;
import simulation.model.Team;

import java.util.Map;

public class ConsoleOutput {

    private static ConsoleOutput consoleOutput;

    public static ConsoleOutput getInstance(){
        if(null == consoleOutput){
            consoleOutput = new ConsoleOutput();
        }
        return consoleOutput;
    }

    public static void printToConsole(String outString) {
        System.out.println(outString);
    }

    public void printMsgToConsole(String outString) {
        System.out.println(outString);
    }

    public void printAITradeDetailsToUser(Map<String, Object> tradeDetails){
        Player fromPlayer = (Player) tradeDetails.get("fromPlayer");
        Player toPlayer = (Player) tradeDetails.get("toPlayer");
        Team fromTeam = (Team) tradeDetails.get("fromTeam");
        Team toTeam = (Team) tradeDetails.get("toTeam");

        printMsgToConsole("----------------------------------");
        printMsgToConsole("\tFrom team: "+fromTeam.getName());
        printMsgToConsole("\t\tPlayer name: "+fromPlayer.getName());
        printMsgToConsole("\t\tPlayer strength: "+fromPlayer.getStrength());
        printMsgToConsole("\tTo Team name: "+toTeam.getName());
        printMsgToConsole("\t\tPlayer name: "+toPlayer.getName());
        printMsgToConsole("\t\tPlayer strength: "+toPlayer.getStrength());
        printMsgToConsole("----------------------------------");
    }

    public void printUserTradeDetailsToUser(Map<String, Object> tradeDetails){
        Player fromPlayer = (Player) tradeDetails.get("fromPlayer");
        Player toPlayer = (Player) tradeDetails.get("toPlayer");
        Team fromTeam = (Team) tradeDetails.get("fromTeam");
        Team toTeam = (Team) tradeDetails.get("toTeam");

        printMsgToConsole("Below Trade Offer is received:");
        printMsgToConsole("----------------------------------");
        printMsgToConsole("\tFrom team: "+fromTeam.getName());
        printMsgToConsole("\t\tPlayer name: "+fromPlayer.getName());
        printMsgToConsole("\t\tPlayer strength: "+fromPlayer.getStrength());
        printMsgToConsole("\tTo Team name: "+toTeam.getName());
        printMsgToConsole("\t\tPlayer name: "+toPlayer.getName());
        printMsgToConsole("\t\tPlayer strength: "+toPlayer.getStrength());
        printMsgToConsole("----------------------------------");
    }

}
