package state;

import model.HockeyContext;
import model.League;
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
    }

    public ImportState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }


    @Override
    public void entry() {
        //Parse JSON
        System.out.println("Import State -> Entry  ");
        if(filePath.length() > 0){
            jsonFromInput = readJSON(filePath);
        }

    }

    @Override
    public void process() {
        //Instantiate and configure LOM
        System.out.println("Import State -> Process ");
        parseJSONAndInstantiateLeague(jsonFromInput);
    }

    @Override
    public IHockeyState exit() {
        //Persist to DB and move to next state
        System.out.println("Import State -> Exit ");
        LoadTeamState loadTeamState = new LoadTeamState(hockeyContext);
        return loadTeamState;
    }

    private JSONObject readJSON(String filePath){

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            JSONObject leagueJSON = (JSONObject)jsonParser.parse(reader);

            return leagueJSON;

            //Iterate over employee array
            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

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
        for(Object conference : conferences){
            JSONObject conferenceJSONObject = (JSONObject) conference;
            String conferenceName = (String) conferenceJSONObject.get("conferenceName");
            JSONArray divisions = (JSONArray) conferenceJSONObject.get("divisions");
            for(Object division : divisions){
                JSONObject divisionJSONObject = (JSONObject) division;
                String divisionName = (String) divisionJSONObject.get("divisionName");
                JSONArray teams = (JSONArray) divisionJSONObject.get("teams");
                for(Object team : teams){
                    JSONObject teamJSONObject = (JSONObject) team;
                    String teamName = (String) teamJSONObject.get("teamName");
                    String generalManager = (String) teamJSONObject.get("generalManager");
                    String headCoach = (String) teamJSONObject.get("headCoach");
                    JSONArray players = (JSONArray) teamJSONObject.get("players");
                    for(Object player : players) {

                        JSONObject playerJsonObject = (JSONObject) player;
                        String playerName = (String) playerJsonObject.get("playerName");
                        String position = (String) playerJsonObject.get("position");
                        boolean captain = (Boolean) playerJsonObject.get("captain");

                    }
                }
            }

        }
        JSONArray freeAgents = (JSONArray) leagueJSON.get("freeAgents");
        for(Object freeAgent : freeAgents) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgent;
            String playerName = (String) freeAgentJsonObject.get("playerName");
            String position = (String) freeAgentJsonObject.get("position");
            boolean captain = (Boolean) freeAgentJsonObject.get("captain");
        }
    }
}
