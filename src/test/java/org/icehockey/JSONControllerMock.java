package org.icehockey;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import scala.util.parsing.json.JSON;

import java.io.File;

public class JSONControllerMock{

    public static JSONObject getJSON(int id) {

        return createJSON();
    }

    private static JSONObject createJSON(){
        JSONObject league = new JSONObject();
        league.put("leagueName","Dalhousie Hockey League");

        JSONArray freeAgents = new JSONArray();
        JSONArray players = new JSONArray();
        JSONArray conferences = new JSONArray();
        JSONArray divisions = new JSONArray();
        JSONArray teams = new JSONArray();

        JSONObject freeAgentplayer1 = new JSONObject();
        freeAgentplayer1.put("playerName","Agent One");
        freeAgentplayer1.put("playerName","forward");
        freeAgentplayer1.put("playerName","false");

        JSONObject freeAgentPlayer2 = new JSONObject();
        freeAgentPlayer2.put("playerName","Agent Two");
        freeAgentPlayer2.put("playerName","forward");
        freeAgentPlayer2.put("playerName","false");

        JSONObject freeAgentPlayer3 = new JSONObject();
        freeAgentPlayer3.put("playerName","Agent Three");
        freeAgentPlayer3.put("playerName","forward");
        freeAgentPlayer3.put("playerName","false");

        freeAgents.add(freeAgentplayer1);
        freeAgents.add(freeAgentPlayer2);
        freeAgents.add(freeAgentPlayer3);

        JSONObject player1 = new JSONObject();
        player1.put("playerName","Agent One");
        player1.put("playerName","forward");
        player1.put("playerName","false");

        JSONObject player2 = new JSONObject();
        freeAgentPlayer2.put("playerName","Agent Two");
        freeAgentPlayer2.put("playerName","forward");
        freeAgentPlayer2.put("playerName","false");

        JSONObject player3 = new JSONObject();
        freeAgentPlayer3.put("playerName","Agent Three");
        freeAgentPlayer3.put("playerName","forward");
        freeAgentPlayer3.put("playerName","false");

        players.add(player1);
        players.add(player2);
        players.add(player3);

        JSONObject team = new JSONObject();
        team.put("players",players);
        team.put("teamName","Boston");
        team.put("generalManager","Mister Feed");
        team.put("headCoach","Mary Smith");

        teams.add(team);

        JSONObject division = new JSONObject();
        division.put("divisionName","Atlantic");
        division.put("teams",teams);

        divisions.add(division);

        JSONObject conference = new JSONObject();
        conference.put("conferenceName","Eastern Conference");
        conference.put("divisions",divisions);

        league.put("leagueName","Dalhousie Hockey League");
        league.put("conferences",conferences);
        league.put("freeAgents",freeAgents);


        return  league;
    }


}
