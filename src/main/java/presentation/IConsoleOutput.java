package presentation;

import simulation.model.ITeamStanding;

import java.util.Map;

public interface IConsoleOutput {

    void printTradeDetailsToUser(Map<String, Object> tradeDetails);
    void printMsgToConsole(String outString);
    void printGameStatsToUser(float goalAvg, float penaltyAvg, float shotAvg, float saveAvg);
    void printTeamGameScore(ITeamStanding teamStanding);
}
