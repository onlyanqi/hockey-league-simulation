package userIO;

import simulation.model.Coach;
import simulation.model.Manager;
import simulation.model.Player;

import java.util.IdentityHashMap;
import java.util.List;

public class ConsoleOutputForTeamCreation implements IConsoleOutputForTeamCreation {

    @Override
    public void showLeagueAlreadyExistsError() {
        ConsoleOutput.printToConsole("League already exists. Please enter a new one");
    }

    @Override
    public void showManagerListOnScreen(List<Manager> managerList) {
        ConsoleOutput.printToConsole("-------------------------------------");
        ConsoleOutput.printToConsole("Manager List");
        ConsoleOutput.printToConsole("-------------------------------------");
        System.out.printf("%5s %20s","ID","Name");
        ConsoleOutput.printToConsole("\n");
        for (int i = 0; i < managerList.size(); i++) {
            System.out.format("%5s %20s",
                    i,managerList.get(i).getName());
            ConsoleOutput.printToConsole("\n");

        }
    }

    @Override
    public void showSuccessfulSerializationMessage() {
        ConsoleOutput.printToConsole("Successfully created JSON output file named JsonOutput.txt which you will be able to see after program stops.");
    }

    @Override
    public void showSuccessfulManagerCreationMessage() {
        ConsoleOutput.printToConsole("General manager added for this team");
    }

    @Override
    public void showSuccessfulCoachCreationMessage() {
        ConsoleOutput.printToConsole("Head coach added for this team");
    }

    @Override
    public void showCoachListOnScreen(List<Coach> coachList) {
        ConsoleOutput.printToConsole("----------------------------------------------------------------------");
        ConsoleOutput.printToConsole("Coach List");
        ConsoleOutput.printToConsole("----------------------------------------------------------------------");

        System.out.printf("%5s %20s %10s %10s %10s %10s","ID","Name","Skating","Shooting","Checking","Saving");
        for (int i = 0; i < coachList.size(); i++) {
            Coach currentCoach = coachList.get(i);
            printCoach(i, currentCoach);
        }
        ConsoleOutput.printToConsole("\n");
    }

    @Override
    public void printCoach(int i, Coach currentCoach) {
        ConsoleOutput.printToConsole("\n");
        System.out.format("%5s %20s %10s %10s %10s %10s",
                i,currentCoach.getName(),
                currentCoach.getSkating(),
                currentCoach.getShooting(),
                currentCoach.getChecking(),
                currentCoach.getSaving());
    }

    @Override
    public void showInstructionsForTeamCreation() {
        ConsoleOutput.printToConsole("----------------------------------------------------------------------");
        ConsoleOutput.printToConsole("Free agent list");
        ConsoleOutput.printToConsole("----------------------------------------------------------------------");
        ConsoleOutput.printToConsole("Please add ids of free agents separated by new line whom you want to add to your team");
        ConsoleOutput.printToConsole("Below you can see good players separated from below-average players!");
        ConsoleOutput.printToConsole("You need to choose 18 skaters (forwards and defense) and 2 goalies to complete the team formation process! \n \n");
    }

    @Override
    public void showGoodFreeAgentList(List<Player> freeAgentList, List<Integer> goodFreeAgentsIdList) {

        ConsoleOutput.printToConsole("Good free agent list :) \n ________________________ ");
        System.out.printf("%5s %20s %10s %10s %10s %10s %10s %10s %10s","ID","Name","Position","Strength","Age","Skating","Shooting","Checking","Saving");
        ConsoleOutput.printToConsole("\n");
        for (int i = 0; i < freeAgentList.size(); i++) {
            Player player = freeAgentList.get(i);
            if (goodFreeAgentsIdList.contains(i)) {
                printPlayer(i, player);
            }
        }
    }

    @Override
    public void playerIdAlreadyChosenMessage(List<Integer> chosenPlayersIdList) {
        ConsoleOutput.printToConsole("Make sure you do not enter the previously chosen id.");
        ConsoleOutput.printToConsole("Previously chosen ids: " + chosenPlayersIdList);
    }

    @Override
    public void showCountOfNeededPlayers(int numberOfGoalies, int numberOfSkaters) {
        ConsoleOutput.printToConsole("Team needs more " + numberOfGoalies + " goalies and " + numberOfSkaters + " skaters");
    }

    @Override
    public void showTeamCreationWaitMessage() {
        ConsoleOutput.printToConsole("\n\nPlease wait your team is getting created...");
    }

    @Override
    public void showBelowAverageFreeAgentList(List<Player> freeAgentList, List<Integer> goodFreeAgentsIdList) {
        ConsoleOutput.printToConsole("Below-average free agent list :| \n __________________ ");
        ConsoleOutput.printToConsole("\n");
        System.out.printf("%5s %20s %10s %10s %10s %10s %10s %10s %10s","ID","Name","Position","Strength","Age","Skating","Shooting","Checking","Saving");
        ConsoleOutput.printToConsole("\n");
        for (int i = 0; i < freeAgentList.size(); i++) {
            Player player = freeAgentList.get(i);
            if (goodFreeAgentsIdList.contains(i)) {
                continue;
            }else{
                printPlayer(i, player);
            }
        }
    }

    @Override
    public void printPlayer(int i, Player player) {
        System.out.format("%5s %20s %10s %10s %10s %10s %10s %10s %10s",i,player.getName(),player.getPosition(),player.getStrength(),player.getAge(),player.getSkating(),player.getShooting(),player.getChecking(),player.getSaving());
        ConsoleOutput.printToConsole("\n");
    }
}
