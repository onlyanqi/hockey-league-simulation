package state;

import dao.*;
import data.*;
import model.*;
import org.icehockey.GetInput;

import java.util.ArrayList;
import java.util.Scanner;

import static common.Constants.loadFreeAgentByLeagueId;

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

        while((teamName.isEmpty() || teamName ==null )){
            teamName = GetInput.getUserInput("Please enter valid and existing team name");
        }

    }


    @Override
    public void process() throws Exception {
        //Load Team Data from DB
        System.out.println("LoadTeam State -> Process ");

        //Load League from userid
        ILoadLeagueFactory iLoadLeagueFactory = new LoadLeagueDao();
        league = iLoadLeagueFactory.loadLeagueListByUserId(hockeyContext.getUser().getId()).get(0);

        ILoadConferenceFactory iLoadConferenceFactory = new LoadConferenceDao();
        ArrayList<Conference> conferenceArrayList = (ArrayList<Conference>) iLoadConferenceFactory.loadConferenceListByLeagueId(league.getId());

        for(Conference conference: conferenceArrayList){
            ILoadDivisionFactory iLoadDivisionFactory = new LoadDivisionDao();
            ArrayList<Division> divisionArrayList = (ArrayList<Division>) iLoadDivisionFactory.loadDivisionListByConferenceId(conference.getId());
            for(Division division: divisionArrayList){
                ILoadTeamFactory iLoadTeamFactory = new LoadTeamDao();
                ArrayList<Team> teamArrayList = (ArrayList<Team>) iLoadTeamFactory.loadTeamListByDivisionId(division.getId());
                for(Team team: teamArrayList){
                    ILoadPlayerFactory iLoadPlayerFactory = new LoadPlayerDao();
                    ArrayList<Player> playerArrayList = (ArrayList<Player>) iLoadPlayerFactory.loadPlayerListByTeamId(team.getId());
                    team.setPlayerList(playerArrayList);
                    teamArrayList.add(team);
                }
                division.setTeamList(teamArrayList);
                divisionArrayList.add(division);
            }
            conference.setDivisionList(divisionArrayList);
            conferenceArrayList.add(conference);
        }
        league.setConferenceList(conferenceArrayList);

        FreeAgent  freeAgent = new FreeAgent();

        ILoadFreeAgentFactory iLoadFreeAgentFactory = new LoadFreeAgentDao();
        iLoadFreeAgentFactory.loadFreeAgentByLeagueId(league.getId(),freeAgent);

        ILoadPlayerFactory iLoadPlayerFactory = new LoadPlayerDao();
        ArrayList<Player> playerArrayList = (ArrayList<Player>) iLoadPlayerFactory.loadPlayerListByFreeAgentId(freeAgent.getId());
        freeAgent.setPlayerList(playerArrayList);
        league.setFreeAgent(freeAgent);



        Division division = new Division();
        ILoadDivisionFactory divisionFactory = new LoadDivisionDao();

        Conference conference = new Conference();
        ILoadConferenceFactory conferenceFactory = new LoadConferenceDao();
        ILoadLeagueFactory leagueFactory = new LoadLeagueDao();

    }

    @Override
    public IHockeyState exit() {
        //Instantiate Model Objects and transition state


        System.out.println("LoadTeam State -> Exit ");
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        return playerChoiceState;
    }


}
