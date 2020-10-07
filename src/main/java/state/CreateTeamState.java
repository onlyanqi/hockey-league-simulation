package state;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateTeamState implements IHockeyState {

    private HockeyContext hockeyContext;
    private League league;
    private String conferenceName;
    private String divisionName;
    private String teamName;
    private String generalManagerName;
    private String headCoachName;


    public CreateTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getLeague();
    }

    @Override
    public void entry() {
        //Prompt Team Data
        Scanner scanner = new Scanner(System.in);

        //Get conference name, division name and team name

        System.out.println("Please enter conference name the team belongs to");
        conferenceName  = scanner.nextLine();

        List<Conference> conferenceList =  league.getConferenceList();

        for(Conference conference: conferenceList ){
            while(!(conference.getName().equals(conferenceName))){
                System.out.println("Please enter conference name from the existing ones");
                conferenceName  = scanner.nextLine();
            }
        }


        System.out.println("Please enter division name the team belongs to");
        divisionName  = scanner.nextLine();

        for(Conference conference : conferenceList){
            for(Division division : conference.getDivisionList()){
                while(!(division.getName().equals(divisionName))){
                    System.out.println("Please enter division name from the existing ones");
                    divisionName  = scanner.nextLine();
                }
            }

        }

        System.out.println("Please enter team name");
        teamName  = scanner.nextLine();

        if(teamName.isEmpty()){
            System.out.println("Please enter the team name!");
        }

        System.out.println("Please enter name of general manager");
        generalManagerName  = scanner.nextLine();

        System.out.println("Please enter name of head coach ");
        headCoachName  = scanner.nextLine();




    }

    @Override
    public void process() {
        //Instantiate Model Objects

        List<Conference> conferenceList = league.getConferenceList();
        for(Conference conference : conferenceList ){
            if(conference.getName().equals(conferenceName)){
                List<Division> divisionList  = conference.getDivisionList();
                for(Division division: divisionList){
                    if(division.getName().equals(divisionName)) {
                        Team team = new Team();
                        team.setName(teamName);
                        team.setHeadCoach(headCoachName);
                        team.setGeneralManager(generalManagerName);

                        division.getTeamList().add(team);

                    }
                }
                conference.setDivisionList(divisionList);
            }
        }

        league.setConferenceList(conferenceList);

        hockeyContext.setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        //Persist to DB and transition to next state
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");

        return playerChoiceState;
    }
}
