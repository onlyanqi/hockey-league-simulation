package userIO;

import simulation.model.Coach;
import simulation.model.Manager;
import simulation.model.Player;

import java.util.List;

public class ConsoleOutputForTeamCreation {

    public void showLeagueAlreadyExistsError(){
        ConsoleOutput.printToConsole("League already exists. Please enter a new one");
    }

    public void showManagerListOnScreen(List<Manager> managerList){
        ConsoleOutput.printToConsole("Manager List \n ___________ ");
        for(int i=0;i<managerList.size();i++){
            ConsoleOutput.printToConsole("Manager id: "+i);
            ConsoleOutput.printToConsole("Name of Manager: "+managerList.get(i).getName());
        }
    }

    public void showSuccessfulManagerCreationMessage(){
        ConsoleOutput.printToConsole("General manager added for this team");
    }

    public void showSuccessfulCoachCreationMessage(){
        ConsoleOutput.printToConsole("Head coach added for this team");
    }

    public void showCoachListOnScreen(List<Coach> coachList){
        ConsoleOutput.printToConsole("Coach List \n ___________ ");
        for(int i=0;i<coachList.size();i++){
            Coach currentCoach = coachList.get(i);
            printCoach(i, currentCoach);
        }
    }

    public void printCoach(int i, Coach currentCoach){
        ConsoleOutput.printToConsole("Coach id: "+(i));
        ConsoleOutput.printToConsole("\t Coach name: "+currentCoach.getName());
        ConsoleOutput.printToConsole("\t Skating strength: "+currentCoach.getSkating());
        ConsoleOutput.printToConsole("\t Shooting strength: "+currentCoach.getShooting());
        ConsoleOutput.printToConsole("\t Checking strength: "+currentCoach.getChecking());
        ConsoleOutput.printToConsole("\t Saving strength: "+currentCoach.getSaving());
        ConsoleOutput.printToConsole("\n");
    }

    public void showInstructionsForTeamCreation(){
        ConsoleOutput.printToConsole("Free agent list \n ___________ \n Please add ids of free agents separated by new line whom you want to add to your team");
        ConsoleOutput.printToConsole("Below you can see good players separated from below-average players!");
        ConsoleOutput.printToConsole("You need to choose 18 skaters (forwards and defense) and 2 goalies to complete the team formation process! \n \n");
    }

    public void showGoodFreeAgentList(List<Player> freeAgentList, List<Integer> goodFreeAgentsIdList) {
        ConsoleOutput.printToConsole("Good free agent list :) \n ___________________ ");
        for(int i=0;i<freeAgentList.size();i++){
            Player player=freeAgentList.get(i);
            if(goodFreeAgentsIdList.contains(i)){
                printPlayer(i, player);
            }
        }
    }

    public void playerIdAlreadyChosenMessage(List<Integer> chosenPlayersIdList){
        ConsoleOutput.printToConsole("Make sure you do not enter the previously chosen id.");
        ConsoleOutput.printToConsole("Previously chosen ids: "+ chosenPlayersIdList);
    }

    public void showCountOfNeededPlayers(int numberOfGoalies,int numberOfSkaters){
        ConsoleOutput.printToConsole("Team needs more "+ numberOfGoalies +" goalies and "+ numberOfSkaters +" skaters");
    }

    public void showTeamCreationWaitMessage(){
        ConsoleOutput.printToConsole("\n\nPlease wait your team is getting created...");
    }

    public void showBelowAverageFreeAgentList(List<Player> freeAgentList, List<Integer> goodFreeAgentsIdList){
        ConsoleOutput.printToConsole("Below-average free agent list :| \n __________________ ");
        for(int i=0;i<freeAgentList.size();i++){
            Player player=freeAgentList.get(i);
            if(!goodFreeAgentsIdList.contains(i)){
                printPlayer(i, player);
            }
        }
    }

    public void printPlayer(int i,Player player){
        ConsoleOutput.printToConsole("Player Id: "+i+"\tPlayer Name: "+player.getName()+ "\tPosition: "+player.getPosition()+"\tStrength: "+player.getStrength());
        ConsoleOutput.printToConsole("age: "+player.getAge()+"\tSkating: "+player.getSkating()+"\tShooting: "+player.getShooting()+"\tChecking: "+player.getChecking()+"\tSaving: "+player.getSaving());
        ConsoleOutput.printToConsole("\n");
    }
}
