package state;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import scala.util.parsing.json.JSON;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportState implements IHockeyState {

    private HockeyContext hockeyContext;
    private String filePath;
    private JSONObject jsonFromInput;
    private League league;


    public ImportState(HockeyContext hockeyContext,String filePath){
        this.filePath = filePath;
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getLeague();
    }




    @Override
    public void entry() {
        //Parse JSON

        if(filePath!=null){
            jsonFromInput = readJSON(filePath);
        }
    }

    @Override
    public void process() {
        //Instantiate and configure LOM
        parseJSONAndInstantiateLeague(jsonFromInput);
        hockeyContext.setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        //Persist to DB and move to next state
        //System.out.println("Import State -> Exit ");
        //LoadTeamState loadTeamState = new LoadTeamState(hockeyContext);
        return null;
    }

    private JSONObject readJSON(String filePath){

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath))
        {

            JSONObject leagueJSON = (JSONObject)jsonParser.parse(reader);

            return leagueJSON;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseJSONAndInstantiateLeague(JSONObject leagueJSON){
        String leagueName = (String) leagueJSON.get("leagueName");
        JSONArray conferences = (JSONArray) leagueJSON.get("conferences");
        ArrayList<Conference> conferenceList = new ArrayList<Conference>();
        for(Object conferenceObjectFromJSONArray : conferences){
            JSONObject conferenceJSONObject = (JSONObject) conferenceObjectFromJSONArray;
            String conferenceName = (String) conferenceJSONObject.get("conferenceName");

            Conference conference = new Conference();
            conference.setName(conferenceName);

            JSONArray divisions = (JSONArray) conferenceJSONObject.get("divisions");

            ArrayList<Division> divisionList = new ArrayList<Division>();
            for(Object divisionObjectFromJSONArray : divisions){
                JSONObject divisionJSONObject = (JSONObject) divisionObjectFromJSONArray;
                String divisionName = (String) divisionJSONObject.get("divisionName");

                Division division = new Division();
                division.setName(divisionName);

                JSONArray teams = (JSONArray) divisionJSONObject.get("teams");


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
                        playerList.add(player);
                    }

                    team.setPlayerList(playerList);
                    teamList.add(team);

                }

                division.setTeamList(teamList);
                divisionList.add(division);
            }
            conference.setDivisionList(divisionList);
            conferenceList.add(conference);
        }

        JSONArray freeAgents = (JSONArray) leagueJSON.get("freeAgents");


        ArrayList<FreeAgent> freeAgentList = new ArrayList<FreeAgent>();
        for(Object freeAgentObjectFromJSONArray : freeAgents) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;
            String playerName = (String) freeAgentJsonObject.get("playerName");
            String position = (String) freeAgentJsonObject.get("position");
            boolean captain = (Boolean) freeAgentJsonObject.get("captain");

            FreeAgent freeAgent = new FreeAgent();
            freeAgent.setName(playerName);
            freeAgentList.add(freeAgent);
        }

        league.setName(leagueName);
        league.setConferenceList(conferenceList);

    }
}
