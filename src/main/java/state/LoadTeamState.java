package state;

import dao.LoadConferenceDao;
import dao.LoadDivisionDao;
import dao.LoadLeagueDao;
import dao.LoadTeamDao;
import data.ILoadConferenceFactory;
import data.ILoadDivisionFactory;
import data.ILoadLeagueFactory;
import data.ILoadTeamFactory;
import model.*;
import org.icehockey.GetInput;

import java.util.Scanner;

public class LoadTeamState implements IHockeyState {

    private String input;
    private HockeyContext hockeyContext;
    private String teamName;
    private League league;


    public LoadTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        //prompt team name

        teamName  = GetInput.getUserInput("Please enter team name");

        while((teamName.isEmpty() || teamName ==null || isTeamNotPresent(teamName))){
            teamName = GetInput.getUserInput("Please enter existing team name");
        }

    }


    @Override
    public void process() throws Exception {
        //Load Team Data from DB
        System.out.println("LoadTeam State -> Process ");

        Team team = new Team();
        ILoadTeamFactory teamFactory = new LoadTeamDao();
        team = teamFactory.loadTeamByName(teamName);

        Division division = new Division();
        ILoadDivisionFactory divisionFactory = new LoadDivisionDao();

        Conference conference = new Conference();
        ILoadConferenceFactory conferenceFactory = new LoadConferenceDao();

        ILoadLeagueFactory leagueFactory = new LoadLeagueDao();

        if(team!=null){

        }else{
            System.out.println("Provided team name doesn't exist. Exiting the app...");
            System.exit(1);
        }

    }

    @Override
    public IHockeyState exit() {
        //Instantiate Model Objects and transition state
        System.out.println("LoadTeam State -> Exit ");
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        return playerChoiceState;
    }

    private boolean isTeamNotPresent(String teamName)  {
        ILoadTeamFactory factory = new LoadTeamDao();
        Team team = null;
        try {
            team = factory.loadTeamByName(teamName);
        }catch (Exception e) {
            System.out.println("Unable to load team, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if(team!=null) return false;
        else return true;

    }
}
