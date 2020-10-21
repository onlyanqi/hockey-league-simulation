package simulation;

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
        JSONObject gameplayConfig = new JSONObject();
        JSONArray freeAgents = new JSONArray();
        JSONArray players = new JSONArray();
        JSONArray conferences = new JSONArray();
        JSONArray divisions = new JSONArray();
        JSONArray teams = new JSONArray();
        JSONArray coaches = new JSONArray();
        JSONArray managers = new JSONArray();

        JSONObject aging = new JSONObject();
        aging.put("averageRetirementAge",35);
        aging.put("maximumAge",50);

        JSONObject gameResolver = new JSONObject();
        gameResolver.put("randomWinChance",0.1);

        JSONObject injuries = new JSONObject();
        injuries.put("randomInjuryChance",0.05);
        injuries.put("injuryDaysLow",1);
        injuries.put("injuryDaysHigh",260);

        JSONObject training = new JSONObject();
        training.put("daysUntilStatIncreaseCheck",100);

        JSONObject trading = new JSONObject();
        trading.put("lossPoint",8);
        trading.put("randomTradeOfferChance",0.05);
        trading.put("maxPlayersPerTrade",2);
        trading.put("randomAcceptanceChance",0.05);

        gameplayConfig.put("aging",aging);
        gameplayConfig.put("gameResolver",gameResolver);
        gameplayConfig.put("injuries",injuries);
        gameplayConfig.put("training",training);
        gameplayConfig.put("trading",trading);

        JSONObject freeAgentplayer1 = new JSONObject();
        freeAgentplayer1.put("playerName","Agent One");
        freeAgentplayer1.put("position","forward");
        freeAgentplayer1.put("age",25);
        freeAgentplayer1.put("skating",10);
        freeAgentplayer1.put("shooting",10);
        freeAgentplayer1.put("checking",10);
        freeAgentplayer1.put("saving",0);

        JSONObject freeAgentPlayer2 = new JSONObject();
        freeAgentPlayer2.put("playerName","Agent Two");
        freeAgentPlayer2.put("position","defense");
        freeAgentPlayer2.put("age",25);
        freeAgentPlayer2.put("skating",10);
        freeAgentPlayer2.put("shooting",10);
        freeAgentPlayer2.put("checking",10);
        freeAgentPlayer2.put("saving",0);

        JSONObject freeAgentPlayer3 = new JSONObject();
        freeAgentPlayer3.put("playerName","Agent Three");
        freeAgentPlayer3.put("position","goalie");
        freeAgentPlayer3.put("age",25);
        freeAgentPlayer3.put("skating",10);
        freeAgentPlayer3.put("shooting",5);
        freeAgentPlayer3.put("checking",5);
        freeAgentPlayer3.put("saving",10);

        freeAgents.add(freeAgentplayer1);
        freeAgents.add(freeAgentPlayer2);
        freeAgents.add(freeAgentPlayer3);

        JSONObject player1 = new JSONObject();
        player1.put("playerName","Agent One");
        player1.put("position","forward");
        player1.put("captain",false);
        player1.put("age",27);
        player1.put("skating",15);
        player1.put("shooting",18);
        player1.put("checking",12);
        player1.put("saving",0);


        JSONObject player2 = new JSONObject();
        player2.put("playerName","Agent Two");
        player2.put("position","defense");
        player2.put("captain",false);
        player2.put("age",20);
        player2.put("skating",10);
        player2.put("shooting",10);
        player2.put("checking",10);
        player2.put("saving",0);

        JSONObject player3 = new JSONObject();
        player3.put("playerName","Agent Three");
        player3.put("position","goalie");
        player3.put("captain",true);
        player3.put("age",33);
        player3.put("skating",10);
        player3.put("shooting",4);
        player3.put("checking",9);
        player3.put("saving",8);

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

        conferences.add(conference);

        JSONObject coach1 = new JSONObject();
        coach1.put("name","Joe Smith");
        coach1.put("skating",0.5);
        coach1.put("shooting",0.8);
        coach1.put("checking",0.3);
        coach1.put("saving",1.0);
        coaches.add(coach1);

        JSONObject coach2 = new JSONObject();
        coach2.put("name","Frank Smith");
        coach2.put("skating",0.5);
        coach2.put("shooting",0.8);
        coach2.put("checking",0.3);
        coach2.put("saving",0.5);
        coaches.add(coach2);

        JSONObject coach3 = new JSONObject();
        coach3.put("name","Samantha Smith");
        coach3.put("skating",1.0);
        coach3.put("shooting",0.5);
        coach3.put("checking",0.5);
        coach3.put("saving",0.0);
        coaches.add(coach3);

        JSONObject manager1 = new JSONObject();
        manager1.put(0,"Karen Potam");

        JSONObject manager2 = new JSONObject();
        manager2.put(1,"Joseph Squidly");

        JSONObject manager3 = new JSONObject();
        manager1.put(2,"Tom Spaghetti");

        managers.add(manager1);
        managers.add(manager2);
        managers.add(manager3);

        league.put("leagueName","Dalhousie Hockey League");
        league.put("gameplayConfig",gameplayConfig);
        league.put("conferences",conferences);
        league.put("freeAgents",freeAgents);
        league.put("coaches",coaches);
        league.put("generalManagers",managers);
        league.put("daysUntilStatIncreaseCheck",100);


        return  league;
    }


}
