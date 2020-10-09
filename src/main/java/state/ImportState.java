package state;

import dao.LoadLeagueDao;
import factory.*;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportState implements IHockeyState {

    private HockeyContext hockeyContext;
    private String filePath;
    private JSONObject jsonFromInput;
    private League league;



    public ImportState(HockeyContext hockeyContext,JSONObject jsonFromInput){
        this.jsonFromInput = jsonFromInput;
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
        if(league == null){
            LeagueConcrete leagueConcrete = new LeagueConcrete();
            league = leagueConcrete.newLeague();
        }
    }

    @Override
    public void entry() {
        //empty for now
    }

    @Override
    public void process() {
        parseJSONAndInstantiateLeague(jsonFromInput);
        hockeyContext.getUser().setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        return null;
    }

    private void parseJSONAndInstantiateLeague(JSONObject leagueJSON){
        String leagueName = (String) leagueJSON.get("leagueName");
        JSONArray conferences = (JSONArray) leagueJSON.get("conferences");
        JSONArray freeAgents = (JSONArray) leagueJSON.get("freeAgents");

        List<Conference> conferenceList = loadConferenceJSON(conferences);

        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();
        FreeAgent freeAgent = freeAgentConcrete.newFreeAgent();
        List<Player> freeAgentList = loadFreeAgentJSON(freeAgents);
        freeAgent.setPlayerList(freeAgentList);

        league.setName(leagueName);
        league.setConferenceList(conferenceList);
        league.setFreeAgent(freeAgent);
    }

    private List<Team> loadTeamJSON(JSONArray teams){
        List<Team> teamList = new ArrayList<Team>();
        for(Object teamObjectFromJSONArray : teams){
            JSONObject teamJSONObject = (JSONObject) teamObjectFromJSONArray;
            String teamName = (String) teamJSONObject.get("teamName");
            String generalManager = (String) teamJSONObject.get("generalManager");
            String headCoach = (String) teamJSONObject.get("headCoach");
            TeamConcrete teamConcrete = new TeamConcrete();
            Team team = teamConcrete.newTeam();
            team.setName(teamName);
            team.setGeneralManager(generalManager);
            team.setHeadCoach(headCoach);

            JSONArray players = (JSONArray) teamJSONObject.get("players");

            List<Player> playerList = loadPlayerJSON(players);

            team.setPlayerList(playerList);
            teamList.add(team);

        }
        return teamList;

    }
    private List<Player> loadPlayerJSON(JSONArray players){

        List<Player> playerList = new ArrayList<Player>();

        for(Object playerObjectFromJSONArray : players) {
            JSONObject playerJsonObject = (JSONObject) playerObjectFromJSONArray;
            String playerName = (String) playerJsonObject.get("playerName");
            String position = (String) playerJsonObject.get("position");
            boolean captain = (Boolean) playerJsonObject.get("captain");

            PlayerConcrete playerConcrete = new PlayerConcrete();
            Player player = playerConcrete.newPlayer();
            player.setName(playerName);
            player.setPosition(position);
            player.setCaptain(captain);

            if(player.validPosition() && player.validName()){
                playerList.add(player);
            }

        }
        return playerList;

    }
    private List<Division> loadDivisionJSON(JSONArray divisions){
        ArrayList<Division> divisionList = new ArrayList<Division>();
        for(Object divisionObjectFromJSONArray : divisions){
            JSONObject divisionJSONObject = (JSONObject) divisionObjectFromJSONArray;
            String divisionName = (String) divisionJSONObject.get("divisionName");

            Division division = new Division();
            division.setName(divisionName);

            JSONArray teams = (JSONArray) divisionJSONObject.get("teams");

            List<Team> teamList = loadTeamJSON(teams);

            division.setTeamList(teamList);
            divisionList.add(division);
        }
        return divisionList;
    }
    private List<Conference> loadConferenceJSON(JSONArray conferences){
        List<Conference> conferenceList = new ArrayList<Conference>();
        for(Object conferenceObjectFromJSONArray : conferences){
            JSONObject conferenceJSONObject = (JSONObject) conferenceObjectFromJSONArray;
            String conferenceName = (String) conferenceJSONObject.get("conferenceName");

            ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
            Conference conference = conferenceConcrete.newConference();

            conference.setName(conferenceName);

            JSONArray divisions = (JSONArray) conferenceJSONObject.get("divisions");

            List<Division> divisionList = loadDivisionJSON(divisions);

            conference.setDivisionList(divisionList);
            conferenceList.add(conference);
        }
        return conferenceList;
    }
    private List<Player> loadFreeAgentJSON(JSONArray freeAgents){

        List<Player> freeAgentList =  new ArrayList<>();
        for(Object freeAgentObjectFromJSONArray : freeAgents) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;
            String playerName = (String) freeAgentJsonObject.get("playerName");
            String position = (String) freeAgentJsonObject.get("position");
            boolean captain = (Boolean) freeAgentJsonObject.get("captain");

            PlayerConcrete playerConcrete = new PlayerConcrete();
            Player player = playerConcrete.newPlayer();
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

