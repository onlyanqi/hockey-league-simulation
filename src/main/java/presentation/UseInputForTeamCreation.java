package presentation;

import simulation.model.Coach;
import simulation.model.Manager;
import java.util.List;

public class UseInputForTeamCreation implements IUserInputForTeamCreation {

    private ConsoleOutput consoleOutput;
    private ReadUserInput readUserInput;

    public UseInputForTeamCreation(){
        consoleOutput = ConsoleOutput.getInstance();
        readUserInput = ReadUserInput.getInstance();
    }

    @Override
    public String getConferenceName(List<String> conferenceNameList) {
        String conferenceName = readUserInput.getInput("Please enter conference name the team belongs to");
        while (!conferenceNameList.contains(conferenceName.toLowerCase())) {
            conferenceName = readUserInput.getInput("Please enter conference name from the existing ones");
        }
        return conferenceName;
    }

    @Override
    public String getDivisionName(List<String> divisionNameList) {
        String divisionName = readUserInput.getInput("Please enter division name the team belongs to");
        while (!divisionNameList.contains(divisionName.toLowerCase())) {
            divisionName = readUserInput.getInput("Please enter division name from the existing ones");
        }
        return divisionName;
    }

    @Override
    public String getUserChoiceForSerialization() {
        String userChoice = readUserInput.getInput("Do you want to serialize league object to JSON?");
        return userChoice;
    }

    @Override
    public String getTeamName(List<String> teamNameList) {
        String teamName = readUserInput.getInput("Please enter a team name to create a team ");

        while (teamNameList.contains(teamName.toLowerCase()) || teamName.isEmpty() || teamName == null) {

            if (teamNameList.contains(teamName.toLowerCase())) {
                teamName = readUserInput.getInput("Provided team name  already exists. Please enter a new one!");
            } else if (teamName.isEmpty() || teamName == null || teamNameList.contains(teamName.toLowerCase())) {
                teamName = readUserInput.getInput("Please enter valid team name! Make sure there is no existing team with provided name");
            }
        }
        return teamName;
    }

    @Override
    public int getPlayerId(int upperBound) {
        String playerIdStr = readUserInput.getInput("Please enter id between 0 to " + upperBound + ". (boundaries inclusive)");
        int playerId=-1;
        while(playerId==-1){
            if(isDigit(playerIdStr)){
                playerId = Integer.parseInt(playerIdStr);
            }else{
                consoleOutput.printMsgToConsole("Please enter valid digits.");
                playerIdStr = readUserInput.getInput("Please enter id between 0 to " + upperBound + ". (boundaries inclusive)");
            }
        }
        return playerId;
    }

    public boolean isDigit(String str){
        if(str.matches("[0-9]+") && str.length() > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getGeneralManagerId(List<Manager> managerList) {
        String generalManagerIdStr = readUserInput.getInput("Please enter id of general manager");
        int generalManagerId = -1;

        while(generalManagerId==-1){
            if(isDigit(generalManagerIdStr)){
                generalManagerId = Integer.parseInt(generalManagerIdStr);
                if(generalManagerId < 0 || (generalManagerId > managerList.size() - 1)){
                    generalManagerId = -1;
                    consoleOutput.printMsgToConsole("Please enter valid digits.");
                    generalManagerIdStr = readUserInput.getInput("Please enter GeneralManager id between 0 to " + (managerList.size() - 1) + ". (boundaries inclusive)");
                }
            }else{
                consoleOutput.printMsgToConsole("Please enter valid digits.");
                generalManagerIdStr = readUserInput.getInput("Please enter GeneralManager id between 0 to " + (managerList.size() - 1) + ". (boundaries inclusive)");
            }
        }
        return generalManagerId;
    }

    @Override
    public int getHeadCoachId(List<Coach> coachList) {
        String headCoachIdStr = readUserInput.getInput("Please enter the id of head coach");
        int headCoachId = -1;

        while(headCoachId==-1){
            if(isDigit(headCoachIdStr)){
                headCoachId = Integer.parseInt(headCoachIdStr);
                if(headCoachId < 0 || (headCoachId > coachList.size() - 1)){
                    headCoachId = -1;
                    consoleOutput.printMsgToConsole("Please enter valid digits.");
                    headCoachIdStr = readUserInput.getInput("Please enter HeadCoach id between 0 to " + (coachList.size() - 1) + ". (boundaries inclusive)");
                }
            }else{
                consoleOutput.printMsgToConsole("Please enter valid digits.");
                headCoachIdStr = readUserInput.getInput("Please enter HeadCoach id between 0 to " + (coachList.size() - 1) + ". (boundaries inclusive)");
            }
        }
        return headCoachId;
    }

}
