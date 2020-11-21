package presentation;

import simulation.model.*;

import java.util.List;

public class ConsoleOutputForTeamCreation implements IConsoleOutputForTeamCreation {

    private ConsoleOutput consoleOutput;

    public ConsoleOutputForTeamCreation() {
        consoleOutput = ConsoleOutput.getInstance();
    }

    @Override
    public void showNotEnoughMembersError() {
        consoleOutput.printMsgToConsole("There is insufficient managers or coaches or free agents, thus you will not be able to create a new team.");
    }

    @Override
    public void showLeagueAlreadyExistsError() {
        consoleOutput.printMsgToConsole("League already exists. Please enter a new one");
    }

    @Override
    public void showManagerListOnScreen(List<IManager> managerList) {
        consoleOutput.printMsgToConsole("-------------------------------------");
        consoleOutput.printMsgToConsole("Manager List");
        consoleOutput.printMsgToConsole("-------------------------------------");
        System.out.printf("%5s %20s", "ID", "Name");
        consoleOutput.printMsgToConsole("\n");
        for (int i = 0; i < managerList.size(); i++) {
            System.out.format("%5s %20s",
                    i, managerList.get(i).getName());
            consoleOutput.printMsgToConsole("\n");
        }
    }

    @Override
    public void showSuccessfulSerializationMessage() {
        consoleOutput.printMsgToConsole("Successfully created JSON output file named JsonOutput.txt which you will be able to see after program stops.");
    }

    @Override
    public void showSuccessfulManagerCreationMessage() {
        consoleOutput.printMsgToConsole("General manager added for this team");
    }

    @Override
    public void showSuccessfulCoachCreationMessage() {
        consoleOutput.printMsgToConsole("Head coach added for this team");
    }

    @Override
    public void showCoachListOnScreen(List<ICoach> coachList) {
        consoleOutput.printMsgToConsole("----------------------------------------------------------------------");
        consoleOutput.printMsgToConsole("Coach List");
        consoleOutput.printMsgToConsole("----------------------------------------------------------------------");

        System.out.printf("%5s %20s %10s %10s %10s %10s", "ID", "Name", "Skating", "Shooting", "Checking", "Saving");
        for (int i = 0; i < coachList.size(); i++) {
            ICoach currentCoach = coachList.get(i);
            printCoach(i, currentCoach);
        }
        consoleOutput.printMsgToConsole("\n");
    }

    @Override
    public void printCoach(int i, ICoach currentCoach) {
        consoleOutput.printMsgToConsole("\n");
        System.out.format("%5s %20s %10s %10s %10s %10s",
                i, currentCoach.getName(),
                currentCoach.getSkating(),
                currentCoach.getShooting(),
                currentCoach.getChecking(),
                currentCoach.getSaving());
    }

    @Override
    public void showInstructionsForTeamCreation() {
        consoleOutput.printMsgToConsole("----------------------------------------------------------------------");
        consoleOutput.printMsgToConsole("Free agent list");
        consoleOutput.printMsgToConsole("----------------------------------------------------------------------");
        consoleOutput.printMsgToConsole("Please add ids of free agents separated by new line whom you want to add to your team");
        consoleOutput.printMsgToConsole("Below you can see good players separated from below-average players!");
        consoleOutput.printMsgToConsole("You need to choose 18 skaters (forwards and defense) and 2 goalies to complete the team formation process! \n \n");
    }

    @Override
    public void showGoodFreeAgentList(List<IPlayer> freeAgentList, List<Integer> goodFreeAgentsIdList) {

        consoleOutput.printMsgToConsole("Good free agent list :) \n ________________________ ");
        System.out.printf("%5s %20s %10s %10s %10s %10s %10s %10s %10s", "ID", "Name", "Position", "Strength", "Age", "Skating", "Shooting", "Checking", "Saving");
        consoleOutput.printMsgToConsole("\n");
        for (int i = 0; i < freeAgentList.size(); i++) {
            IPlayer player = freeAgentList.get(i);
            if (goodFreeAgentsIdList.contains(i)) {
                printPlayer(i, player);
            }
        }
    }

    @Override
    public void playerIdAlreadyChosenMessage(List<Integer> chosenPlayersIdList) {
        consoleOutput.printMsgToConsole("Make sure you do not enter the previously chosen id.");
        consoleOutput.printMsgToConsole("Previously chosen ids: " + chosenPlayersIdList);
    }

    @Override
    public void showCountOfNeededPlayers(int numberOfGoalie, int numberOfForward, int numberOfDefense) {
        consoleOutput.printMsgToConsole("Team needs " + numberOfGoalie + " more goalies, " + numberOfForward + " more forwards and " + numberOfDefense + " more defenses.");
    }

    @Override
    public void showTeamCreationWaitMessage() {
        consoleOutput.printMsgToConsole("\n\nPlease wait your team is getting created...");
    }

    @Override
    public void showBelowAverageFreeAgentList(List<IPlayer> freeAgentList, List<Integer> goodFreeAgentsIdList) {
        consoleOutput.printMsgToConsole("Below-average free agent list :| \n __________________ ");
        consoleOutput.printMsgToConsole("\n");
        System.out.printf("%5s %20s %10s %10s %10s %10s %10s %10s %10s", "ID", "Name", "Position", "Strength", "Age", "Skating", "Shooting", "Checking", "Saving");
        consoleOutput.printMsgToConsole("\n");
        for (int i = 0; i < freeAgentList.size(); i++) {
            IPlayer player = freeAgentList.get(i);
            if (goodFreeAgentsIdList.contains(i)) {
                continue;
            } else {
                printPlayer(i, player);
            }
        }
    }

    @Override
    public void printPlayer(int i, IPlayer player) {
        System.out.format("%5s %20s %10s %10s %10s %10s %10s %10s %10s", i, player.getName(), player.getPosition(), player.getStrength(), player.getAge(), player.getSkating(), player.getShooting(), player.getChecking(), player.getSaving());
        consoleOutput.printMsgToConsole("\n");
    }
}
