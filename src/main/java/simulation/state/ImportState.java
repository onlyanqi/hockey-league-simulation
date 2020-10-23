package simulation.state;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import simulation.factory.*;
import simulation.model.*;

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
        JSONObject gameplayConfigJSONObject = (JSONObject) leagueJSON.get("gameplayConfig");
        JSONArray conferences = (JSONArray) leagueJSON.get("conferences");
        JSONArray freeAgents = (JSONArray) leagueJSON.get("freeAgents");
        JSONArray coaches = (JSONArray) leagueJSON.get("coaches");
        JSONArray managers = (JSONArray) leagueJSON.get("generalManagers");


        if(validateString(leagueName) ){
            System.out.println("Please make sure league name is valid ");
            System.exit(1);
        }

        if(validateArray(conferences) ){
            System.out.println("Please make sure atleast one conference is provided ");
            System.exit(1);
        }
        if(validateArray(freeAgents) ){
            System.out.println("Please make sure atleast one Player in Free Agent is provided ");
            System.exit(1);
        }

        GamePlayConfigConcrete gamePlayConfigConcrete = new GamePlayConfigConcrete();
        GamePlayConfig gamePlayConfig = gamePlayConfigConcrete.newGamePlayConfig();

        JSONObject agingJSONObject = (JSONObject) gameplayConfigJSONObject.get("aging");
        Aging aging = loadAgingJson(agingJSONObject);
        gamePlayConfig.setAging(aging);

        JSONObject gameResolverJSONObject = (JSONObject) gameplayConfigJSONObject.get("gameResolver");
        GameResolver gameResolver = loadGameResolverJson(gameResolverJSONObject);
        gamePlayConfig.setGameResolver(gameResolver);

        JSONObject injuriesJSONObject = (JSONObject) gameplayConfigJSONObject.get("injuries");
        Injury injury = loadInjuryJson(injuriesJSONObject);
        gamePlayConfig.setInjury(injury);

        JSONObject trainingJSONObject = (JSONObject) gameplayConfigJSONObject.get("training");
        Training training = loadTrainingJson(trainingJSONObject);
        gamePlayConfig.setTraining(training);

        JSONObject tradingJSONObject = (JSONObject) gameplayConfigJSONObject.get("trading");
        Trading trading = loadTradingJson(tradingJSONObject);
        gamePlayConfig.setTrading(trading);

        List<Conference> conferenceList = loadConferenceJSON(conferences);

        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();
        FreeAgent freeAgent = freeAgentConcrete.newFreeAgent();
        List<Player> freeAgentList = loadFreeAgentJSON(freeAgents);
        freeAgent.setPlayerList(freeAgentList);

        List<Coach> coachList = loadCoachJSON(coaches);
        List<Manager> managerList = loadManagerJSON(managers);

        league.setName(leagueName);
        league.setConferenceList(conferenceList);
        league.setFreeAgent(freeAgent);
        league.setManagerList(managerList);
        league.setCoachList(coachList);
        league.setGamePlayConfig(gamePlayConfig);

    }
    /**
     *
     * Show list of free agents
     * Player choice capability
     *
     */

    private List<Team> loadTeamJSON(JSONArray teams){
        ArrayList<Team> teamList = new ArrayList<Team>();
        for(Object teamObjectFromJSONArray : teams){
            JSONObject teamJSONObject = (JSONObject) teamObjectFromJSONArray;
            String teamName = (String) teamJSONObject.get("teamName");
            String managerName = (String) teamJSONObject.get("generalManager");
            ManagerConcrete managerConcrete = new ManagerConcrete();
            Manager manager = managerConcrete.newManagerConcrete();
            manager.setName(managerName);
            JSONObject coachJsonObject = (JSONObject) teamJSONObject.get("headCoach");
            String coachName = (String) coachJsonObject.get("name");
            Double skating = (Double) coachJsonObject.get("skating");
            Double shooting = (Double) coachJsonObject.get("shooting");
            Double checking = (Double) coachJsonObject.get("checking");
            Double saving = (Double) coachJsonObject.get("saving");
            CoachConcrete coachConcrete = new CoachConcrete();
            Coach coach = coachConcrete.newCoach();
            coach.setName(coachName);
            coach.setSkating(skating);
            coach.setShooting(shooting);
            coach.setChecking(checking);
            coach.setSaving(saving);

            if(validateString(teamName) ){
                System.out.println("Please make sure team name is valid ");
                System.exit(1);
            }


            TeamConcrete teamConcrete = new TeamConcrete();
            Team team = teamConcrete.newTeam();
            team.setName(teamName);
            team.setManager(manager);
            team.setCoach(coach);


            JSONArray players = (JSONArray) teamJSONObject.get("players");

            if(validateArray(players) ){
                System.out.println("Please make sure atleast one player is provided. ");
                System.exit(1);
            }

            List<Player> playerList = loadPlayerJSON(players);

            team.setPlayerList(playerList);
            team.setStrength();
            teamList.add(team);

        }
        return teamList;

    }
    private List<Player> loadPlayerJSON(JSONArray players){

        ArrayList<Player> playerList = new ArrayList<Player>();

        ArrayList<Boolean> captainList = new ArrayList<>();

        try {

            for (Object playerObjectFromJSONArray : players) {
                JSONObject playerJsonObject = (JSONObject) playerObjectFromJSONArray;
                String playerName = (String) playerJsonObject.get("playerName");
                String positionString = (String) playerJsonObject.get("position");
                Player.Position position = Player.Position.valueOf(positionString);

                boolean captain = (Boolean) playerJsonObject.get("captain");
                int age = (int)(long) playerJsonObject.get("age");
                int skating = (int)(long) playerJsonObject.get("skating");
                int shooting = (int)(long) playerJsonObject.get("shooting");
                int checking = (int)(long) playerJsonObject.get("checking");
                int saving = (int)(long) playerJsonObject.get("saving");

                if (validateString(playerName)) {
                    System.out.println("Please make sure player name is valid ");
                    System.exit(1);
                }

                if (validateBoolean(captain)) {
                    System.out.println("Please make sure captain is valid ");
                    System.exit(1);
                }

                if (validCaptain(playerList, captain)) {
                    System.out.println("Please make sure only one captain is provided ");
                    System.exit(1);
                }

                if (isPlayerExists(playerList, playerName)) {
                    System.out.println("Please make sure there are no duplicates in conference name");
                    System.exit(1);
                }

                captainList.add(captain);

                PlayerConcrete playerConcrete = new PlayerConcrete();
                Player player = playerConcrete.newPlayer();
                player.setName(playerName);
                player.setPosition(position);
                player.setCaptain(captain);
                player.setAge(age);
                player.setSkating(skating);
                player.setShooting(shooting);
                player.setChecking(checking);
                player.setSaving(saving);
                player.setStrength();
                playerList.add(player);

            }
        }catch(ClassCastException e){
            System.out.println("Please make sure only boolean is provided for captain field.Exiting the app!");
            System.exit(1);
        }
        return playerList;

    }
    private List<Division> loadDivisionJSON(JSONArray divisions){
        ArrayList<Division> divisionList = new ArrayList<Division>();
        for(Object divisionObjectFromJSONArray : divisions){
            JSONObject divisionJSONObject = (JSONObject) divisionObjectFromJSONArray;
            String divisionName = (String) divisionJSONObject.get("divisionName");

            if(validateString(divisionName) ){
                System.out.println("Please make sure divisionName is valid");
                System.exit(1);
            }

            if(isDivisionExists(divisionList,divisionName)){
                System.out.println("Please make sure only one division is provided");
                System.exit(1);
            }

            Division division = new Division();
            division.setName(divisionName);

            JSONArray teams = (JSONArray) divisionJSONObject.get("teams");
            if(validateArray(teams) ){
                System.out.println("Please make sure atleast one team is provided");
                System.exit(1);
            }

            List<Team> teamList = loadTeamJSON(teams);

            division.setTeamList(teamList);
            divisionList.add(division);
        }
        return divisionList;
    }
    private List<Conference> loadConferenceJSON(JSONArray conferences){
        ArrayList<Conference> conferenceList = new ArrayList<Conference>();
        for(Object conferenceObjectFromJSONArray : conferences){
            JSONObject conferenceJSONObject = (JSONObject) conferenceObjectFromJSONArray;
            String conferenceName = (String) conferenceJSONObject.get("conferenceName");

            if(validateString(conferenceName) ){
                System.out.println("Please make sure conferenceName is valid ");
                System.exit(1);
            }

            if(isConferenceExists(conferenceList,conferenceName)){
                System.out.println("Please make sure there are no duplicates in conference name");
                System.exit(1);
            }


            ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
            Conference conference = conferenceConcrete.newConference();

            conference.setName(conferenceName);

            JSONArray divisions = (JSONArray) conferenceJSONObject.get("divisions");

            if(validateArray(divisions) ){
                System.out.println("Please make sure atleast one division is provided ");
                System.exit(1);
            }

            List<Division> divisionList = loadDivisionJSON(divisions);

            conference.setDivisionList(divisionList);
            conferenceList.add(conference);
        }
        return conferenceList;
    }
    private List<Player> loadFreeAgentJSON(JSONArray freeAgents){

        ArrayList<Player> freeAgentList =  new ArrayList<>();


            for (Object freeAgentObjectFromJSONArray : freeAgents) {
                JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;

                String playerName = (String) freeAgentJsonObject.get("playerName");
                String positionString = (String) freeAgentJsonObject.get("position");
                Player.Position position = Player.Position.valueOf(positionString);
                int age = (int)(long) freeAgentJsonObject.get("age");
                int skating = (int)(long) freeAgentJsonObject.get("skating");
                int shooting = (int)(long) freeAgentJsonObject.get("shooting");
                int checking = (int)(long) freeAgentJsonObject.get("checking");
                int saving = (int)(long) freeAgentJsonObject.get("saving");

                if (validateString(playerName)) {
                    System.out.println("Please make sure player name is valid in Free Agent");
                    System.exit(1);
                }

                if (isPlayerExists(freeAgentList, playerName)) {
                    System.out.println("Please make sure only  Player is unique in freeagent");
                    System.exit(1);
                }


                PlayerConcrete playerConcrete = new PlayerConcrete();
                Player player = playerConcrete.newPlayer();
                player.setName(playerName);
                player.setPosition(position);
                player.setAge(age);
                player.setSkating(skating);
                player.setShooting(shooting);
                player.setChecking(checking);
                player.setSaving(saving);
                player.setStrength();

                if (player.validName()) {
                    freeAgentList.add(player);
                } else {
                    System.out.println("Free Agent Position is not valid. Please Correct it. Exiting the app!");
                    System.exit(1);
                }

            }
        return freeAgentList;
    }
    private List<Manager> loadManagerJSON(JSONArray managers) {
        ArrayList<Manager> managerList =  new ArrayList<>();
        int managerSize = managers.size();

        for ( int i =0; i < managerSize; i++) {

            String name = (String) managers.get(i);
            ManagerConcrete managerConcrete = new ManagerConcrete();
            Manager manager = managerConcrete.newManagerConcrete();
            manager.setName(name);
            managerList.add(manager);
        }
        return managerList;
    }

    private List<Coach> loadCoachJSON(JSONArray coaches) {
        ArrayList<Coach> coachList =  new ArrayList<>();
        for (Object coachObjectFromJSONArray : coaches) {
            JSONObject coachJsonObject = (JSONObject) coachObjectFromJSONArray;
            String name = (String) coachJsonObject.get("name");
            Double skating = (Double) coachJsonObject.get("skating");
            Double shooting = (Double) coachJsonObject.get("shooting");
            Double checking = (Double) coachJsonObject.get("checking");
            Double saving = (Double) coachJsonObject.get("saving");
            CoachConcrete coachConcrete = new CoachConcrete();
            Coach coach = coachConcrete.newCoach();
            coach.setName(name);
            coach.setSkating(skating);
            coach.setShooting(shooting);
            coach.setChecking(checking);
            coach.setSaving(saving);
            coachList.add(coach);
        }
        return coachList;
    }


    private Aging loadAgingJson(JSONObject agingJSONObject){
        int averageRetirementAge = (int)(long) agingJSONObject.get("averageRetirementAge");
        int maximumAge = (int)(long) agingJSONObject.get("maximumAge");
        AgingConcrete agingConcrete = new AgingConcrete();
        Aging aging = agingConcrete.newAging();
        aging.setAverageRetirementAge(averageRetirementAge);
        aging.setMaximumAge(maximumAge);
        return aging;
    }

    private GameResolver loadGameResolverJson (JSONObject gameResolverJSONObject){
        double randomWinChance = (Double) gameResolverJSONObject.get("randomWinChance");
        GameResolverConcrete gameResolverConcrete = new GameResolverConcrete();
        GameResolver gameResolver = gameResolverConcrete.newGameResolver();
        gameResolver.setRandomWinChance(randomWinChance);
        return gameResolver;
    }

    private Injury loadInjuryJson (JSONObject injuriesJSONObject){
        double randomInjuryChance = (Double) injuriesJSONObject.get("randomInjuryChance");
        int injuryDaysLow = (int)(long) injuriesJSONObject.get("injuryDaysLow");
        int injuryDaysHigh = (int)(long) injuriesJSONObject.get("injuryDaysHigh");
        InjuryConcrete injuryConcrete = new InjuryConcrete();
        Injury injury = injuryConcrete.newInjury();
        injury.setRandomInjuryChance(randomInjuryChance);
        injury.setInjuryDaysLow(injuryDaysLow);
        injury.setInjuryDaysHigh(injuryDaysHigh);
        return injury;
    }

    private Training loadTrainingJson (JSONObject trainingJSONObject){
        int daysUntil = (int)(long) trainingJSONObject.get("daysUntilStatIncreaseCheck");
        TrainingConcrete trainingConcrete = new TrainingConcrete();
        Training training = trainingConcrete.newTraining();
        training.setDaysUntilStatIncreaseCheck(daysUntil);
        return training;
    }

    private  Trading loadTradingJson (JSONObject tradingJSONObject){
        int lossPoint = (int)(long) tradingJSONObject.get("lossPoint");
        double randomTradeOfferChance = (Double) tradingJSONObject.get("randomTradeOfferChance");
        int maxPlayersPerTrade = (int)(long) tradingJSONObject.get("maxPlayersPerTrade");
        double randomAcceptanceChance = (Double) tradingJSONObject.get("randomAcceptanceChance");
        TradingConcrete tradingConcrete = new TradingConcrete();
        Trading trading = tradingConcrete.newTrading();
        trading.setLossPoint(lossPoint);
        trading.setRandomTradeOfferChance(randomTradeOfferChance);
        trading.setMaxPlayersPerTrade(maxPlayersPerTrade);
        trading.setRandomAcceptanceChance(randomAcceptanceChance);
        return trading;
    }

    private boolean validateString(String name) {
        if (name != null && name.length() != 0) {
            return  false;
        } else {
            return true;
        }
    }
    private boolean validateArray(JSONArray array){
        if (array != null && array.size() != 0) {
            return  false;
        } else {
            return true;
        }
    }
    private boolean validateBoolean(Boolean bool){
        return bool == null;
    }

    private boolean validCaptain(List<Player> playerList,Boolean captain){
        boolean flag = false;
        for(Player vPlayer : playerList){
            if(captain && vPlayer.isCaptain()){
                flag =  true;
            }
        }
        return flag;
    }

    private boolean isDivisionExists(ArrayList<Division> list,String name){


        boolean flag = false;
        for(ParentObj obj : list){
            if(obj.getName().equals(name)){
                flag =  true;
            }
        }
        return flag;
    }
    private boolean isConferenceExists(ArrayList<Conference> list,String name){


        boolean flag = false;
        for(ParentObj obj : list){
            if(obj.getName().equals(name)){
                flag =  true;
            }
        }
        return flag;
    }
    private boolean isTeamExists(ArrayList<Team> list,String name){


        boolean flag = false;
        for(ParentObj obj : list){
            if(obj.getName().equals(name)){
                flag =  true;
            }
        }
        return flag;
    }
    private boolean isPlayerExists(ArrayList<Player> list,String name){


        boolean flag = false;
        for(ParentObj obj : list){
            if(obj.getName().equals(name)){
                flag =  true;
            }
        }
        return flag;
    }
}

