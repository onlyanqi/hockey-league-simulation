package state;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportState implements IHockeyState {

    private HockeyContext hockeyContext;
    private String filePath;
    private JSONObject jsonFromInput;
    private League league;



    public ImportState(HockeyContext hockeyContext,JSONObject jsonFromInput){
        this.jsonFromInput = jsonFromInput;
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getLeague();
    }



    @Override
    public void entry() {
        //empty for now

    }

    @Override
    public void process() {
        parseJSONAndInstantiateLeague(jsonFromInput);
        hockeyContext.setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        return null;
    }

    private void parseJSONAndInstantiateLeague(JSONObject leagueJSON){
        String leagueName = (String) leagueJSON.get("leagueName");
        JSONArray conferences = (JSONArray) leagueJSON.get("conferences");
        JSONArray freeAgents = (JSONArray) leagueJSON.get("freeAgents");

        ArrayList<Conference> conferenceList = loadConferenceJSON(conferences);


        FreeAgent freeAgent = new FreeAgent();
        ArrayList<Player> freeAgentList = loadFreeAgentJSON(freeAgents);
        freeAgent.setPlayerList(freeAgentList);

        league.setName(leagueName);
        league.setConferenceList(conferenceList);
        league.setFreeAgent(freeAgent);
    }

    private ArrayList<Team> loadTeamJSON(JSONArray teams){
        ArrayList<Team> teamList = new ArrayList<Team>();
        for(Object teamObjectFromJSONArray : teams){
            JSONObject teamJSONObject = (JSONObject) teamObjectFromJSONArray;
            String teamName = (String) teamJSONObject.get("teamName");
            String generalManager = (String) teamJSONObject.get("generalManager");
            String headCoach = (String) teamJSONObject.get("headCoach");
            Team team = new Team();
            team.setName(teamName);
            team.setGeneralManager(generalManager);
            team.setHeadCoach(headCoach);

            JSONArray players = (JSONArray) teamJSONObject.get("players");

            ArrayList<Player> playerList = loadPlayerJSON(players);

            team.setPlayerList(playerList);
            teamList.add(team);

        }
        return teamList;

    }
    private ArrayList<Player> loadPlayerJSON(JSONArray players){

        ArrayList<Player> playerList = new ArrayList<Player>();

        for(Object playerObjectFromJSONArray : players) {
            JSONObject playerJsonObject = (JSONObject) playerObjectFromJSONArray;
            String playerName = (String) playerJsonObject.get("playerName");
            String position = (String) playerJsonObject.get("position");
            boolean captain = (Boolean) playerJsonObject.get("captain");


            Player player = new Player();
            player.setName(playerName);
            player.setPosition(position);
            player.setCaptain(captain);

            if(player.validPosition() && player.validName()){
                playerList.add(player);
            }else{
                //Exception Handling
            }


        }
        return playerList;

    }
    private ArrayList<Division> loadDivisionJSON(JSONArray divisions){
        ArrayList<Division> divisionList = new ArrayList<Division>();
        for(Object divisionObjectFromJSONArray : divisions){
            JSONObject divisionJSONObject = (JSONObject) divisionObjectFromJSONArray;
            String divisionName = (String) divisionJSONObject.get("divisionName");

            Division division = new Division();
            division.setName(divisionName);

            JSONArray teams = (JSONArray) divisionJSONObject.get("teams");


            ArrayList<Team> teamList = loadTeamJSON(teams);

            division.setTeamList(teamList);
            divisionList.add(division);
        }
        return divisionList;
    }
    private ArrayList<Conference> loadConferenceJSON(JSONArray conferences){
        ArrayList<Conference> conferenceList = new ArrayList<Conference>();
        for(Object conferenceObjectFromJSONArray : conferences){
            JSONObject conferenceJSONObject = (JSONObject) conferenceObjectFromJSONArray;
            String conferenceName = (String) conferenceJSONObject.get("conferenceName");

            Conference conference = new Conference();
            conference.setName(conferenceName);

            JSONArray divisions = (JSONArray) conferenceJSONObject.get("divisions");

            ArrayList<Division> divisionList = loadDivisionJSON(divisions);

            conference.setDivisionList(divisionList);
            conferenceList.add(conference);
        }
        return conferenceList;
    }
    private ArrayList<Player> loadFreeAgentJSON(JSONArray freeAgents){

        ArrayList<Player> freeAgentList =  new ArrayList<>();
        for(Object freeAgentObjectFromJSONArray : freeAgents) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;
            String playerName = (String) freeAgentJsonObject.get("playerName");
            String position = (String) freeAgentJsonObject.get("position");
            boolean captain = (Boolean) freeAgentJsonObject.get("captain");

            Player player = new Player();
            player.setName(playerName);
            player.setPosition(position);
            player.setCaptain(captain);

            if(player.validPosition() && player.validName()){
                freeAgentList.add(player);
            }else{
                System.out.println("Free Agent Position is not valid. Please Correct it. Exiting the app!");
                System.exit(1);
            }

        }
        return freeAgentList;
    }
}

