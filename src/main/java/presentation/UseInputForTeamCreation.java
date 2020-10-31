package presentation;

import simulation.model.Coach;
import simulation.model.Manager;
import java.util.List;

public class UseInputForTeamCreation implements IUserInputForTeamCreation {

    @Override
    public String getConferenceName(List<String> conferenceNameList) {
        String conferenceName = ReadUserInput.getUserInput("Please enter conference name the team belongs to");
        while (!conferenceNameList.contains(conferenceName.toLowerCase())) {
            conferenceName = ReadUserInput.getUserInput("Please enter conference name from the existing ones");
        }
        return conferenceName;
    }

    @Override
    public String getDivisionName(List<String> divisionNameList) {
        String divisionName = ReadUserInput.getUserInput("Please enter division name the team belongs to");
        while (!divisionNameList.contains(divisionName.toLowerCase())) {
            divisionName = ReadUserInput.getUserInput("Please enter division name from the existing ones");
        }
        return divisionName;
    }

    @Override
    public String getUserChoiceForSerialization() {
        String userChoice = ReadUserInput.getUserInput("Do you want to serialize league object to JSON?");
        return userChoice;
    }

    @Override
    public String getTeamName(List<String> teamNameList) {
        String teamName = ReadUserInput.getUserInput("Please enter a team name to create a team ");

        while (teamNameList.contains(teamName.toLowerCase()) || teamName.isEmpty() || teamName == null) {

            if (teamNameList.contains(teamName.toLowerCase())) {
                teamName = ReadUserInput.getUserInput("Provided team name  already exists. Please enter a new one!");
            } else if (teamName.isEmpty() || teamName == null || teamNameList.contains(teamName.toLowerCase())) {
                teamName = ReadUserInput.getUserInput("Please enter valid team name! Make sure there is no existing team with provided name");
            }
        }
        return teamName;
    }

    @Override
    public int getPlayerId(int upperBound) {
        String playerIdStr = ReadUserInput.getUserInput("Please enter id between 0 to " + upperBound + ". (boundaries inclusive)");
        int playerId=-1;
        while(playerId==-1){
            if(isDigit(playerIdStr)){
                playerId = Integer.parseInt(playerIdStr);
            }else{
                ConsoleOutput.printToConsole("Please enter valid digits.");
                playerIdStr = ReadUserInput.getUserInput("Please enter id between 0 to " + upperBound + ". (boundaries inclusive)");
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
        String generalManagerIdStr = ReadUserInput.getUserInput("Please enter id of general manager");
        int generalManagerId = -1;

        while(generalManagerId==-1){
            if(isDigit(generalManagerIdStr)){
                generalManagerId = Integer.parseInt(generalManagerIdStr);
                if(generalManagerId < 0 || (generalManagerId > managerList.size() - 1)){
                    generalManagerId = -1;
                    ConsoleOutput.printToConsole("Please enter valid digits.");
                    generalManagerIdStr = ReadUserInput.getUserInput("Please enter GeneralManager id between 0 to " + (managerList.size() - 1) + ". (boundaries inclusive)");
                }
            }else{
                ConsoleOutput.printToConsole("Please enter valid digits.");
                generalManagerIdStr = ReadUserInput.getUserInput("Please enter GeneralManager id between 0 to " + (managerList.size() - 1) + ". (boundaries inclusive)");
            }
        }
        return generalManagerId;
    }

    @Override
    public int getHeadCoachId(List<Coach> coachList) {
        String headCoachIdStr = ReadUserInput.getUserInput("Please enter the id of head coach");
        int headCoachId = -1;

        while(headCoachId==-1){
            if(isDigit(headCoachIdStr)){
                headCoachId = Integer.parseInt(headCoachIdStr);
                if(headCoachId < 0 || (headCoachId > coachList.size() - 1)){
                    headCoachId = -1;
                    ConsoleOutput.printToConsole("Please enter valid digits.");
                    headCoachIdStr = ReadUserInput.getUserInput("Please enter HeadCoach id between 0 to " + (coachList.size() - 1) + ". (boundaries inclusive)");
                }
            }else{
                ConsoleOutput.printToConsole("Please enter valid digits.");
                headCoachIdStr = ReadUserInput.getUserInput("Please enter HeadCoach id between 0 to " + (coachList.size() - 1) + ". (boundaries inclusive)");
            }
        }
        return headCoachId;
    }

}
