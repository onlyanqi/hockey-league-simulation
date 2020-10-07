package state;

import dao.LeagueDao;
import model.*;
import org.icehockey.GetInput;

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

        conferenceName  = GetInput.getUserInput("Please enter conference name the team belongs to");

        List<Conference> conferenceList =  league.getConferenceList();

        for(Conference conference: conferenceList ){
            while(!(conference.getName().equals(conferenceName))){
                conferenceName  = GetInput.getUserInput("Please enter conference name from the existing ones");
            }
        }
        divisionName  = GetInput.getUserInput("Please enter division name the team belongs to");

        for(Conference conference : conferenceList){
            for(Division division : conference.getDivisionList()){
                while(!(division.getName().equals(divisionName))){
                    divisionName  = GetInput.getUserInput("Please enter division name from the existing ones");
                }
            }
        }
        teamName  = GetInput.getUserInput("Please enter team name");

        if(teamName.isEmpty()){
            System.out.println("Please enter the team name!");
        }
        generalManagerName  = GetInput.getUserInput("Please enter name of general manager");

        headCoachName  = GetInput.getUserInput("Please enter name of head coach ");
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
        league.setCreatedBy(3);
        LeagueDao leagueDao = new LeagueDao();
        try {
            leagueDao.addLeague(hockeyContext.getLeague());
        } catch (Exception e) {
            System.out.println("Unable to save the league! Please try again");
            System.exit(1);
            e.printStackTrace();
        }

        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        return playerChoiceState;
    }
}
