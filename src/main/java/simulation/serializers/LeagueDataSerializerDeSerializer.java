package simulation.serializers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import db.data.IAgingDao;
import db.data.ILeagueDao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import presentation.ConsoleOutput;
import simulation.factory.*;
import simulation.model.*;
import simulation.state.IHockeyContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class LeagueDataSerializerDeSerializer {

    public static String FILENAME = "JsonOutput.txt";
    public static final String JSONCREATIONERROR = "Json could not be created";
    public static final String DESERIALIZATIONERROR = "Could not deserialize";
    private ConsoleOutput consoleOutput = null;
    private static final String LEAGUE_NAME = "leagueName";
    private static final String CONFERENCE_NAME = "conferenceName";
    private static final String DIVISION_NAME = "divisionName";
    private static final String TEAM_NAME = "teamName";
    private static final String CONFERENCES = "conferenceList";
    private static final String DIVISIONS = "divisionList";
    private static final String TEAMS = "teamList";
    private static final String MANAGER = "manager";
    private static final String HEAD_COACH = "coach";
    private static final String NAME = "name";
    private static final String SKATING = "skating";
    private static final String SHOOTING = "shooting";
    private static final String CHECKING = "checking";
    private static final String SAVING = "saving";
    private static final String PLAYERS = "playerList";
    private static final String PLAYER_NAME = "name";
    private static final String CAPTAIN = "isCaptain";
    private static final String POSITION = "position";
    private static final String AGE = "age";
    private static final String FREE_AGENTS = "freeAgent";
    private static final String MANAGERS = "managerList";
    private static final String COACHES = "coachList";
    private static final String GAMEPLAY_CONFIG = "gamePlayConfig";
    private static final String AGING = "aging";
    private static final String GAME_RESOLVER = "gameResolver";
    private static final String INJURIES = "injury";
    private static final String TRAINING = "training";
    private static final String TRADING = "trading";
    private static final String AVERAGE_RETIREMENT_AGE = "averageRetirementAge";
    private static final String MAXIMUM_AGE = "maximumAge";
    private static final String RANDOM_WIN_CHANCE = "randomWinChance";
    private static final String RANDOM_INJURY_CHANCE = "randomInjuryChance";
    private static final String INJURY_DAYS_LOW = "injuryDaysLow";
    private static final String INJURY_DAYS_HIGH = "injuryDaysHigh";
    private static final String DAYS_UNTIL_STAT_INCREASE_CHECK = "daysUntilStatIncreaseCheck";
    private static final String LOSS_POINT = "lossPoint";
    private static final String RANDOM_TRADE_OFFER_CHANCE = "randomTradeOfferChance";
    private static final String MAX_PLAYERS_PER_TRADE = "maxPlayersPerTrade";
    private static final String RANDOM_ACCEPTANCE_CHANCE = "randomAcceptanceChance";
    private static final String GM_TABLE = "gmTable";
    public static final String INITIALIZE_INFO = "Validating JSON input and initializing the league model object...";
    private static final String PERSONALITY = "personality";
    private IHockeyContext hockeyContext;
    private int leagueId;
    private ILeague league;
    private int teamId;

    public void serialize(ILeague league) {
        if (consoleOutput == null) {
            consoleOutput = ConsoleOutput.getInstance();
        }

        if (league == null) {
            return;
        }

        //Source for creating folder through java program: https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java

        File jsonDir = new File("/JsonFiles");
        if (!jsonDir.exists()){
            jsonDir.mkdirs();
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();

        FileWriter fileWriter = null;
        try {
            FILENAME = "JsonFiles"+"/"+league.getUserCreatedTeamName();
            fileWriter = new FileWriter(FILENAME);
            gson.toJson(league, fileWriter);

        } catch (Exception e) {
            consoleOutput.printMsgToConsole(JSONCREATIONERROR);
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                consoleOutput.printMsgToConsole(JSONCREATIONERROR);
            }

        }
    }

    public ILeague deSerialize(String filename, IHockeyContext hockeyContext) throws IOException {
        System.out.println(filename);
        if (consoleOutput == null) {
            consoleOutput = ConsoleOutput.getInstance();
        }
        FileReader fileReader = null;
        JSONParser jsonParser = new JSONParser();
        this.hockeyContext = hockeyContext;

        try {
            fileReader = new FileReader(filename);
            Gson gson = new Gson();

            /*GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeHierarchyAdapter(IAging.class, new AgingCreator());
            Gson gson = gsonBuilder.create();*/

            //league = gson.fromJson(jsonParser.parse(fileReader).toString(), League.class);

            ILeagueFactory leagueFactory = hockeyContext.getLeagueFactory();
            league = leagueFactory.newLeague();
            leagueId = league.getId();

            JSONObject leagueJSON = (JSONObject) jsonParser.parse(fileReader);

            JSONObject gameplayConfigJSONObject = (JSONObject) leagueJSON.get(GAMEPLAY_CONFIG);

            JSONArray conferences = (JSONArray) leagueJSON.get(CONFERENCES);

            JSONObject freeAgents = (JSONObject) leagueJSON.get(FREE_AGENTS);

            JSONArray coaches = (JSONArray) leagueJSON.get(COACHES);

            JSONArray managers = (JSONArray) leagueJSON.get(MANAGERS);

            IGamePlayConfig gamePlayConfig = loadGamePlayConfigJSON(gameplayConfigJSONObject);

            List<IConference> conferenceList = loadConferenceJSON(conferences);

            IFreeAgentFactory freeAgentConcrete = hockeyContext.getFreeAgentFactory();
            IFreeAgent freeAgent = freeAgentConcrete.newFreeAgent();
            JSONArray freeAgentPlayerList = (JSONArray) freeAgents.get("playerList");
            List<IPlayer> freeAgentList = loadFreeAgentJSON(freeAgentPlayerList);
            freeAgent.setPlayerList(freeAgentList);
            freeAgent.setLeagueId((int) (long) freeAgents.get("leagueId"));
            freeAgent.setSeasonId((int) (long) freeAgents.get("seasonId"));

            List<ICoach> coachList = loadCoachJSON(coaches);

            List<IManager> managerList = loadManagerJSON(managers);

            List<IPlayer> retiredPlayerList = new ArrayList<>();

            setLeagueVariables(gamePlayConfig, conferenceList, freeAgent, coachList, managerList, retiredPlayerList);


        } catch (FileNotFoundException e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        } catch (ParseException e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        } catch (IOException e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        } catch (Exception e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        } finally {
            fileReader.close();
        }
        return league;
    }

    private List<IManager> loadManagerJSON(JSONArray managers) {
        List<IManager> managerList = new ArrayList<>();
        int managerSize = managers.size();

        for (int i = 0; i < managerSize; i++) {
            JSONObject generalManager = (JSONObject) managers.get(i);

            String name = (String) generalManager.get(NAME);
            String personality = (String) generalManager.get(PERSONALITY);

            IManagerFactory managerConcrete = hockeyContext.getManagerFactory();
            IManager manager = managerConcrete.newManagerConcrete();
            manager.setName(name);
            manager.setPersonality(personality);
            managerList.add(manager);
        }
        return managerList;
    }


    private void setLeagueVariables(IGamePlayConfig gamePlayConfig,
                                    List<IConference> conferenceList, IFreeAgent freeAgent,
                                    List<ICoach> coachList, List<IManager> managerList, List<IPlayer> retiredPlayerList) {
        league.setConferenceList(conferenceList);
        league.setFreeAgent(freeAgent);
        league.setManagerList(managerList);
        league.setCoachList(coachList);
        league.setGamePlayConfig(gamePlayConfig);
        league.setRetiredPlayerList(retiredPlayerList);
    }

    private List<ICoach> loadCoachJSON(JSONArray coaches) {
        List<ICoach> coachList = new ArrayList<>();
        for (Object coachObjectFromJSONArray : coaches) {
            JSONObject coachJsonObject = (JSONObject) coachObjectFromJSONArray;
            String name = (String) coachJsonObject.get(NAME);
            Double skating = (Double) coachJsonObject.get(SKATING);
            Double shooting = (Double) coachJsonObject.get(SHOOTING);
            Double checking = (Double) coachJsonObject.get(CHECKING);
            Double saving = (Double) coachJsonObject.get(SAVING);
            ICoach coach = setCoachVariables(name, skating, shooting, checking, saving);
            coachList.add(coach);
        }
        return coachList;
    }


    private List<IPlayer> loadFreeAgentJSON(JSONArray freeAgent) throws IllegalArgumentException {

        List<IPlayer> freeAgentList = new ArrayList<>();

        for (Object freeAgentObjectFromJSONArray : freeAgent) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;
            String playerName = (String) freeAgentJsonObject.get(PLAYER_NAME);
            Position position = validatePosition(freeAgentJsonObject);
            int skating = getPlayerSkating(freeAgentJsonObject);

            int shooting = getPlayerShooting(freeAgentJsonObject);

            int checking = getPlayerChecking(freeAgentJsonObject);

            int saving = getPlayerSaving(freeAgentJsonObject);

            IPlayer player = setFreePlayerVariables(playerName, position, skating, shooting, checking, saving);

            if (player.validName()) {
                freeAgentList.add(player);
            } else {
                throw new IllegalArgumentException("Free Agent Position is not valid. Please Correct it. Exiting the app!");
            }

        }
        return freeAgentList;
    }

    private int getPlayerSaving(JSONObject playerJsonObject) {
        return (int) (long) playerJsonObject.get(SAVING);
    }

    private IPlayer setFreePlayerVariables(String playerName,
                                           Position position, int skating, int shooting, int checking, int saving) {
        IPlayerFactory playerFactory = hockeyContext.getPlayerFactory();
        IPlayer player = playerFactory.newPlayer();
        player.setName(playerName);
        player.setPosition(position);
        //player.setAge(age);
        player.setSkating(skating);
        player.setShooting(shooting);
        player.setChecking(checking);
        player.setSaving(saving);
        player.setStrength();
        player.setInjured(false);
        player.setIsFreeAgent(true);
        return player;
    }

    private int getPlayerChecking(JSONObject playerJsonObject) throws IllegalArgumentException {
        return (int) (long) playerJsonObject.get(CHECKING);
    }

    private IAging loadAgingJson(JSONObject agingJSONObject) {
        IAgingFactory agingFactory = hockeyContext.getAgingFactory();
        IAging aging = agingFactory.newAging();
        IAgingDao agingDao = agingFactory.newAgingDao();
        agingDao.loadAgingFromJSON(aging, agingJSONObject);
        return aging;
    }

    private IInjury loadInjuryJson(JSONObject injuriesJSONObject) {

        IInjuryFactory injuryConcrete = hockeyContext.getInjuryFactory();
        IInjury injury = injuryConcrete.newInjury();

        double randomInjuryChance = (Double) injuriesJSONObject.get(RANDOM_INJURY_CHANCE);
        int injuryDaysLow = (int) (long) injuriesJSONObject.get(INJURY_DAYS_LOW);
        int injuryDaysHigh = (int) (long) injuriesJSONObject.get(INJURY_DAYS_HIGH);

        injury.setRandomInjuryChance(randomInjuryChance);
        injury.setInjuryDaysLow(injuryDaysLow);
        injury.setInjuryDaysHigh(injuryDaysHigh);
        return injury;
    }

    private ITraining loadTrainingJson(JSONObject trainingJSONObject) {

        int daysUntil = (int) (long) trainingJSONObject.get(DAYS_UNTIL_STAT_INCREASE_CHECK);
        ITrainingFactory trainingConcrete = hockeyContext.getTrainingFactory();
        ITraining training = trainingConcrete.newTraining();
        training.setDaysUntilStatIncreaseCheck(daysUntil);
        return training;
    }

    private ITrading loadTradingJson(JSONObject tradingJSONObject) {

        int lossPoint = (int) (long) tradingJSONObject.get(LOSS_POINT);
        double randomTradeOfferChance = (Double) tradingJSONObject.get(RANDOM_TRADE_OFFER_CHANCE);
        int maxPlayersPerTrade = (int) (long) tradingJSONObject.get(MAX_PLAYERS_PER_TRADE);
        double randomAcceptanceChance = (Double) tradingJSONObject.get(RANDOM_ACCEPTANCE_CHANCE);

        /*JSONObject gmTableJsonObject = (JSONObject) tradingJSONObject.get(GM_TABLE);
        Map<String, Double> gmTable = new HashMap<>();
        for(Object key : gmTableJsonObject.keySet()){
            String attribute = (String) key;
            double attributeValue = (Double) gmTableJsonObject.get(attribute);
            gmTable.put(attribute, attributeValue);
        }*/

        ITradingFactory tradingConcrete = hockeyContext.getTradingFactory();
        ITrading trading = tradingConcrete.newTrading();
        trading.setLossPoint(lossPoint);
        trading.setRandomTradeOfferChance(randomTradeOfferChance);
        trading.setMaxPlayersPerTrade(maxPlayersPerTrade);
        trading.setRandomAcceptanceChance(randomAcceptanceChance);
        //trading.setGmTable(gmTable);
        return trading;
    }

    private IGamePlayConfig loadGamePlayConfigJSON(JSONObject gameplayConfigJSONObject) throws IllegalArgumentException {
        IGamePlayConfigFactory gamePlayConfigConcrete = hockeyContext.getGamePlayConfigFactory();
        IGamePlayConfig gamePlayConfig = gamePlayConfigConcrete.newGamePlayConfig();
        JSONObject agingJSONObject = (JSONObject) gameplayConfigJSONObject.get(AGING);
        IAging aging = loadAgingJson(agingJSONObject);
        aging.setLeagueId(leagueId);
        gamePlayConfig.setAging(aging);

        JSONObject injuriesJSONObject = (JSONObject) gameplayConfigJSONObject.get(INJURIES);
        IInjury injury = loadInjuryJson(injuriesJSONObject);
        injury.setLeagueId(leagueId);
        gamePlayConfig.setInjury(injury);

        JSONObject trainingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRAINING);
        ITraining training = loadTrainingJson(trainingJSONObject);
        training.setLeagueId(leagueId);
        gamePlayConfig.setTraining(training);

        JSONObject tradingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRADING);
        ITrading trading = loadTradingJson(tradingJSONObject);
        gamePlayConfig.setTrading(trading);
        gamePlayConfig.setLeagueId(leagueId);
        return gamePlayConfig;
    }

    private List<IConference> loadConferenceJSON(JSONArray conferences) throws IllegalArgumentException {
        List<IConference> conferenceList = new ArrayList<IConference>();

        for (Object conferenceObjectFromJSONArray : conferences) {
            JSONObject conferenceJSONObject = (JSONObject) conferenceObjectFromJSONArray;
            String conferenceName = (String) conferenceJSONObject.get(CONFERENCE_NAME);
            IConferenceFactory conferenceConcrete = hockeyContext.getConferenceFactory();
            IConference conference = conferenceConcrete.newConference();

            conference.setName(conferenceName);

            JSONArray divisions = (JSONArray) conferenceJSONObject.get(DIVISIONS);

            List<IDivision> divisionList = loadDivisionJSON(divisions);

            conference.setDivisionList(divisionList);
            conferenceList.add(conference);
        }
        return conferenceList;
    }

    private List<IDivision> loadDivisionJSON(JSONArray divisions) throws IllegalArgumentException {
        List<IDivision> divisionList = new ArrayList<IDivision>();
        for (Object divisionObjectFromJSONArray : divisions) {
            JSONObject divisionJSONObject = (JSONObject) divisionObjectFromJSONArray;

            String divisionName = (String) divisionJSONObject.get(DIVISION_NAME);

            IDivision division = new Division();
            division.setName(divisionName);

            JSONArray teams = (JSONArray) divisionJSONObject.get(TEAMS);
            List<ITeam> teamList = loadTeamJSON(teams);

            division.setTeamList(teamList);
            divisionList.add(division);
        }
        return divisionList;
    }

    private List<ITeam> loadTeamJSON(JSONArray teams) throws IllegalArgumentException {

        List<ITeam> teamList = new ArrayList<>();
        for (Object teamObjectFromJSONArray : teams) {

            JSONObject teamJSONObject = (JSONObject) teamObjectFromJSONArray;

            String teamName = (String) teamJSONObject.get(TEAM_NAME);

            IManager manager = setTeamManager(teamJSONObject);

            ICoach coach = setTeamCoach(teamJSONObject);

            List<IPlayer> playerList = setTeamPlayerList(teamJSONObject);

            ITeam team = setTeamVariables(teamName, manager, coach, playerList);

            teamId = team.getId();
            teamList.add(team);
        }
        return teamList;
    }

    private ITeam setTeamVariables(String teamName, IManager manager, ICoach coach, List<IPlayer> playerList) throws IllegalArgumentException {
        ITeamFactory teamConcrete = hockeyContext.getTeamFactory();
        ITeam team = teamConcrete.newTeam();
        team.setName(teamName);
        team.setManager(manager);
        team.setCoach(coach);
        team.setAiTeam(true);
        team.setPlayerList(playerList);
        team.setStrength();
        team.setActivePlayerList();
        return team;
    }

    private List<IPlayer> setTeamPlayerList(JSONObject teamJSONObject) throws IllegalArgumentException {
        JSONArray players = (JSONArray) teamJSONObject.get(PLAYERS);
        return loadPlayerJSON(players);
    }

    private List<IPlayer> loadPlayerJSON(JSONArray players) throws IllegalArgumentException {

        List<IPlayer> playerList = new ArrayList<>();
        int captainCount = 0;
        try {
            for (Object playerObjectFromJSONArray : players) {
                JSONObject playerJsonObject = (JSONObject) playerObjectFromJSONArray;
                String playerName = (String) playerJsonObject.get(PLAYER_NAME);

                Position position = validatePosition(playerJsonObject);

                Boolean captain = (Boolean) playerJsonObject.get(CAPTAIN);
                if (captain) {
                    captainCount++;
                    if (captainCount > 1) {
                        throw new IllegalArgumentException("Please make sure only one captain in a team");
                    }
                }

                int skating = getPlayerSkating(playerJsonObject);

                int shooting = getPlayerShooting(playerJsonObject);

                int checking = getPlayerChecking(playerJsonObject);

                int saving = getPlayerSaving(playerJsonObject);

                IPlayer player = setTeamPlayerVariables(playerName, position, captain, skating, shooting, checking, saving);
                player.setTeamId(teamId);
                playerList.add(player);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return playerList;

    }

    private IPlayer setTeamPlayerVariables(String playerName, Position position,
                                           boolean captain, int skating, int shooting, int checking, int saving) {
        IPlayerFactory playerConcrete = hockeyContext.getPlayerFactory();
        IPlayer player = playerConcrete.newPlayer();
        player.setName(playerName);
        player.setPosition(position);
        player.setCaptain(captain);
        player.setSkating(skating);
        player.setShooting(shooting);
        player.setChecking(checking);
        player.setSaving(saving);
        player.setStrength();
        player.setInjured(false);
        player.setIsFreeAgent(false);
        player.setRelativeStrength();
        return player;
    }

    private int getPlayerShooting(JSONObject playerJsonObject) {
        return (int) (long) playerJsonObject.get(SHOOTING);
    }


    private int getPlayerSkating(JSONObject playerJsonObject) throws IllegalArgumentException {
        return (int) (long) playerJsonObject.get(SKATING);
    }

    private Position validatePosition(JSONObject playerJsonObject) throws IllegalArgumentException {
        String positionString = (String) playerJsonObject.get(POSITION);
        Position positionEnum = null;
        if (positionString.equalsIgnoreCase(Position.GOALIE.toString())) {
            positionEnum = Position.GOALIE;
        } else if (positionString.equalsIgnoreCase(Position.FORWARD.toString())) {
            positionEnum = Position.FORWARD;
        } else if (positionString.equalsIgnoreCase(Position.DEFENSE.toString())) {
            positionEnum = Position.DEFENSE;
        }
        return positionEnum;
    }

    private ICoach setTeamCoach(JSONObject teamJSONObject) throws IllegalArgumentException {

        JSONObject coachJsonObject = (JSONObject) teamJSONObject.get(HEAD_COACH);
        String coachName = (String) coachJsonObject.get(NAME);
        Double skating = (Double) coachJsonObject.get(SKATING);
        Double shooting = (Double) coachJsonObject.get(SHOOTING);
        Double checking = (Double) coachJsonObject.get(CHECKING);
        Double saving = (Double) coachJsonObject.get(SAVING);
        return setCoachVariables(coachName, skating, shooting, checking, saving);
    }

    private ICoach setCoachVariables(String coachName, Double skating, Double shooting, Double checking, Double saving) {
        ICoachFactory coachConcrete = hockeyContext.getCoachFactory();
        ICoach coach = coachConcrete.newCoach();
        coach.setName(coachName);
        coach.setSkating(skating);
        coach.setShooting(shooting);
        coach.setChecking(checking);
        coach.setSaving(saving);
        return coach;
    }

    private IManager setTeamManager(JSONObject teamJSONObject) throws IllegalArgumentException {
        JSONObject generalManager = (JSONObject) teamJSONObject.get(MANAGER);

        String name = (String) generalManager.get(NAME);
        String personality = (String) generalManager.get(PERSONALITY);

        IManagerFactory managerConcrete = hockeyContext.getManagerFactory();
        IManager manager = managerConcrete.newManagerConcrete();
        manager.setName(name);
        manager.setPersonality(personality);
        return manager;
    }



}


