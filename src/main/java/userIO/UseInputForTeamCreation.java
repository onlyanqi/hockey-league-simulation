package userIO;

import db.data.ITeamFactory;
import simulation.factory.TeamConcrete;
import simulation.model.Coach;
import simulation.model.Manager;
import simulation.model.Team;

import java.util.List;

public class UseInputForTeamCreation {

    public String getSeasonName(){
        String seasonName=GetInput.getUserInput("Please enter season name");
        while((seasonName ==null || seasonName.isEmpty() )){
            seasonName = GetInput.getUserInput("Please enter season name!");
        }
        return seasonName;
    }

    public String getConferenceName(List<String> conferenceNameList){
        String conferenceName  = GetInput.getUserInput("Please enter conference name the team belongs to");
        while(!conferenceNameList.contains(conferenceName.toLowerCase())){
            conferenceName  = GetInput.getUserInput("Please enter conference name from the existing ones");
        }
        return conferenceName;
    }

    public String getDivisionName(List<String> divisionNameList){
        String divisionName = GetInput.getUserInput("Please enter division name the team belongs to");
        while(!divisionNameList.contains(divisionName.toLowerCase())){
            divisionName  = GetInput.getUserInput("Please enter division name from the existing ones");
        }
        return divisionName;
    }

    public String getTeamName(List<String> teamNameList){
        String teamName = GetInput.getUserInput("Please enter a team name to create a team ");

        while(teamNameList.contains(teamName.toLowerCase()) || teamName.isEmpty() || teamName ==null || isTeamPresent(teamName)){

            if(teamNameList.contains(teamName.toLowerCase())){
                teamName  = GetInput.getUserInput("Provided team name  already exists. Please enter a new one!");
            }
            else if(teamName.isEmpty() || teamName ==null || isTeamPresent(teamName)){
                teamName = GetInput.getUserInput("Please enter valid team name! Make sure there is no existing team with provided name");
            }
        }
        return teamName;
    }

    public int getPlayerId(int upperBound){
        int playerId = Integer.parseInt(GetInput.getUserInput("Please enter id between 0 to "+upperBound+". (boundaries inclusive)"));
        return playerId;
    }

    public int getGeneralManagerId(List<Manager> managerList){
        int generalManagerId = Integer.parseInt(GetInput.getUserInput("Please enter id of general manager"));
        while(generalManagerId<0 || (generalManagerId >managerList.size()-1)){
            generalManagerId = Integer.parseInt(GetInput.getUserInput("Please enter GeneralManager id between 0 to "+ (managerList.size()-1)+". (boundaries inclusive)"));
        }
        return generalManagerId;
    }

    public int getHeadCoachId(List<Coach> coachList){
        int headCoachId = Integer.parseInt(GetInput.getUserInput("Please enter the id of head coach"));
        while(headCoachId<0 || (headCoachId >coachList.size()-1)){
            headCoachId = Integer.parseInt(GetInput.getUserInput("Please enter HeadCoach id between 0 to "+(coachList.size()-1)+". (boundaries inclusive)"));
        }
        return headCoachId;
    }

    private boolean isTeamPresent(String teamName)  {
        boolean isTeamPresent = false;
        TeamConcrete teamConcrete = new TeamConcrete();
        ITeamFactory factory = teamConcrete.newLoadTeamFactory();
        Team team = null;
        try {
            team = teamConcrete.newTeamByName(teamName, factory);
        }catch (Exception e) {
            System.out.println("Unable to load team, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if(team!=null && team.getId() > 0) {
            isTeamPresent = true;
        }
        return isTeamPresent;
    }

}
