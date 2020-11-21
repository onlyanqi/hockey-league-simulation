package simulation.state;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import presentation.ConsoleOutput;
import scala.util.parsing.json.JSON;
import simulation.App;
import simulation.factory.*;
import simulation.model.*;

import java.util.*;


public class ImportState implements IHockeyState {

    private static final String LEAGUE_NAME = "leagueName";
    private static final String CONFERENCE_NAME = "conferenceName";
    private static final String DIVISION_NAME = "divisionName";
    private static final String TEAM_NAME = "teamName";
    private static final String CONFERENCES = "conferences";
    private static final String DIVISIONS = "divisions";
    private static final String TEAMS = "teams";
    private static final String MANAGER = "generalManager";
    private static final String HEAD_COACH = "headCoach";
    private static final String NAME = "name";
    private static final String SKATING = "skating";
    private static final String SHOOTING = "shooting";
    private static final String CHECKING = "checking";
    private static final String SAVING = "saving";
    private static final String PLAYERS = "players";
    private static final String PLAYER_NAME = "playerName";
    private static final String CAPTAIN = "captain";
    private static final String POSITION = "position";
    private static final String AGE = "age";
    private static final String FREE_AGENTS = "freeAgents";
    private static final String MANAGERS = "generalManagers";
    private static final String COACHES = "coaches";
    private static final String GAMEPLAY_CONFIG = "gameplayConfig";
    private static final String AGING = "aging";
    private static final String GAME_RESOLVER = "gameResolver";
    private static final String INJURIES = "injuries";
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
    private final Set<String> appearedName = new HashSet<>();
    private IHockeyContext hockeyContext;
    private String filePath;
    private JSONObject jsonFromInput;
    private League league;
    private int leagueId;
    private int teamId;
    private static Logger log = Logger.getLogger(ImportState.class);
    private static final String PERSONALITY = "personality";


    public ImportState(IHockeyContext hockeyContext, JSONObject jsonFromInput) {
        this.jsonFromInput = jsonFromInput;
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
        if (league == null) {
            LeagueConcrete leagueConcrete = new LeagueConcrete();
            league = leagueConcrete.newLeague();
        }
        leagueId = league.getId();
    }

    @Override
    public void entry() {
        ConsoleOutput.getInstance().printMsgToConsole(INITIALIZE_INFO);
    }

    @Override
    public void process() throws Exception {
        parseJSONAndInstantiateLeague(jsonFromInput);
        hockeyContext.getUser().setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        return null;
    }

    private void parseJSONAndInstantiateLeague(JSONObject leagueJSON) throws Exception {

        try {
            if (validateKeyInObject(leagueJSON, LEAGUE_NAME)) {
                throw new IllegalArgumentException("Please make sure league name is provided in JSON");
            }
            String leagueName = (String) leagueJSON.get(LEAGUE_NAME);
            if (validateString(leagueName)) {
                throw new IllegalArgumentException("Please make sure league name " + leagueName + " is valid");
            }

            JSONObject gameplayConfigJSONObject = validateGameCofig(leagueJSON);

            JSONArray conferences = validateConferences(leagueJSON);

            JSONArray freeAgents = validateFreeAgents(leagueJSON);

            JSONArray coaches = validateCoaches(leagueJSON);

            JSONArray managers = validateManagers(leagueJSON);

            GamePlayConfig gamePlayConfig = loadGamePlayConfigJSON(gameplayConfigJSONObject);

            List<Conference> conferenceList = loadConferenceJSON(conferences);

            FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();
            FreeAgent freeAgent = freeAgentConcrete.newFreeAgent();
            List<Player> freeAgentList = loadFreeAgentJSON(freeAgents);
            freeAgent.setPlayerList(freeAgentList);

            List<Coach> coachList = loadCoachJSON(coaches);

            List<Manager> managerList = loadManagerJSON(managers);

            List<Player> retiredPlayerList = new ArrayList<>();

            setLeagueVariables(leagueName, gamePlayConfig, conferenceList, freeAgent, coachList, managerList, retiredPlayerList);
        } catch (Exception e){
            log.error("ImportState: parseJSONAndInstantiateLeague: Exception: "+e);
            throw e;
        }
    }

    private void setLeagueVariables(String leagueName, GamePlayConfig gamePlayConfig, List<Conference> conferenceList, FreeAgent freeAgent, List<Coach> coachList, List<Manager> managerList, List<Player> retiredPlayerList) {
        league.setName(leagueName);
        league.setConferenceList(conferenceList);
        league.setFreeAgent(freeAgent);
        league.setManagerList(managerList);
        league.setCoachList(coachList);
        league.setGamePlayConfig(gamePlayConfig);
        league.setRetiredPlayerList(retiredPlayerList);
    }

    private JSONArray validateManagers(JSONObject leagueJSON) throws IllegalArgumentException {
        if (validateKeyInObject(leagueJSON, MANAGERS)) {
            throw new IllegalArgumentException("Please make sure generalManagers are provided in JSON");
        }
        JSONArray managers = (JSONArray) leagueJSON.get(MANAGERS);
        if (validateArray(managers)) {
            throw new IllegalArgumentException("Please make sure at least one Manager is free");
        }
        return managers;
    }

    private JSONArray validateCoaches(JSONObject leagueJSON) throws IllegalArgumentException {
        if (validateKeyInObject(leagueJSON, COACHES)) {
            throw new IllegalArgumentException("Please make sure coaches are provided in JSON");
        }
        JSONArray coaches = (JSONArray) leagueJSON.get(COACHES);
        if (validateArray(coaches)) {
            throw new IllegalArgumentException("Please make sure at least one Coach is free");
        }
        return coaches;
    }

    private JSONArray validateFreeAgents(JSONObject leagueJSON) throws IllegalArgumentException {
        if (validateKeyInObject(leagueJSON, FREE_AGENTS)) {
            throw new IllegalArgumentException("Please make sure freeAgents are provided in JSON");
        }
        JSONArray freeAgents = (JSONArray) leagueJSON.get(FREE_AGENTS);
        if (validateArray(freeAgents)) {
            throw new IllegalArgumentException("Please make sure at least one Player is in Free Agent");
        }
        return freeAgents;
    }

    private JSONArray validateConferences(JSONObject leagueJSON) throws IllegalArgumentException {
        if (validateKeyInObject(leagueJSON, CONFERENCES)) {
            throw new IllegalArgumentException("Please make sure conferences are provided in JSON");
        }
        JSONArray conferences = (JSONArray) leagueJSON.get(CONFERENCES);
        if (validateArray(conferences)) {
            throw new IllegalArgumentException("Please make sure at least one conference is provided");
        }
        return conferences;
    }

    private JSONObject validateGameCofig(JSONObject leagueJSON) throws IllegalArgumentException {
        if (validateKeyInObject(leagueJSON, GAMEPLAY_CONFIG)) {
            throw new IllegalArgumentException("Please make sure gameplayConfig is provided in JSON");
        }
        return (JSONObject) leagueJSON.get(GAMEPLAY_CONFIG);
    }

    private GamePlayConfig loadGamePlayConfigJSON(JSONObject gameplayConfigJSONObject) throws IllegalArgumentException {
        GamePlayConfigConcrete gamePlayConfigConcrete = new GamePlayConfigConcrete();
        GamePlayConfig gamePlayConfig = gamePlayConfigConcrete.newGamePlayConfig();

        if (validateKeyInObject(gameplayConfigJSONObject, AGING)) {
            throw new IllegalArgumentException("Please make sure aging is provided in JSON");
        }
        JSONObject agingJSONObject = (JSONObject) gameplayConfigJSONObject.get(AGING);
        //IAging aging = loadAgingJson(agingJSONObject);
        Aging aging = loadAgingJson(agingJSONObject);
        aging.setLeagueId(leagueId);
        gamePlayConfig.setAging(aging);

        /*if (validateKeyInObject(gameplayConfigJSONObject, GAME_RESOLVER)) {
            throw new IllegalArgumentException("Please make sure gameResolver is provided in JSON");
        }
        JSONObject gameResolverJSONObject = (JSONObject) gameplayConfigJSONObject.get(GAME_RESOLVER);
        GameResolver gameResolver = loadGameResolverJson(gameResolverJSONObject);
        gamePlayConfig.setGameResolver(gameResolver);*/

        if (validateKeyInObject(gameplayConfigJSONObject, INJURIES)) {
            throw new IllegalArgumentException("Please make sure injuries is provided in JSON");
        }
        JSONObject injuriesJSONObject = (JSONObject) gameplayConfigJSONObject.get(INJURIES);
        Injury injury = loadInjuryJson(injuriesJSONObject);
        injury.setLeagueId(leagueId);
        gamePlayConfig.setInjury(injury);

        if (validateKeyInObject(gameplayConfigJSONObject, TRAINING)) {
            throw new IllegalArgumentException("Please make sure training is provided in JSON");
        }
        JSONObject trainingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRAINING);
        Training training = loadTrainingJson(trainingJSONObject);
        training.setLeagueId(leagueId);
        gamePlayConfig.setTraining(training);

        if (validateKeyInObject(gameplayConfigJSONObject, TRADING)) {
            throw new IllegalArgumentException("Please make sure trading is provided in JSON");
        }
        JSONObject tradingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRADING);
        Trading trading = loadTradingJson(tradingJSONObject);
        gamePlayConfig.setTrading(trading);
        gamePlayConfig.setLeagueId(leagueId);
        return gamePlayConfig;
    }

    private List<Team> loadTeamJSON(JSONArray teams) throws IllegalArgumentException {
        List<Team> teamList = new ArrayList<>();
        for (Object teamObjectFromJSONArray : teams) {

            JSONObject teamJSONObject = (JSONObject) teamObjectFromJSONArray;

            if (validateKeyInObject(teamJSONObject, TEAM_NAME)) {
                throw new IllegalArgumentException("Please make sure team name is provided and correct");
            }
            String teamName = (String) teamJSONObject.get(TEAM_NAME);
            if (validateString(teamName)) {
                throw new IllegalArgumentException("Please make sure team name is valid");
            }

            if (isTeamExistsInDivision(teamList, teamName)) {
                throw new IllegalArgumentException("Please make sure team name is unique in one division");
            }

            if (isTeamExistsInLeague(teamName)) {
                throw new IllegalArgumentException("Please make sure team name is unique in one league");
            }

            Manager manager = setTeamManager(teamJSONObject);

            Coach coach = setTeamCoach(teamJSONObject);

            List<Player> playerList = setTeamPlayerList(teamJSONObject);

            Team team = setTeamVariables(teamName, manager, coach, playerList);

            teamId = team.getId();
            teamList.add(team);
        }
        return teamList;
    }

    private List<Player> setTeamPlayerList(JSONObject teamJSONObject) throws IllegalArgumentException {
        if (validateKeyInObject(teamJSONObject, PLAYERS)) {
            throw new IllegalArgumentException("Please make sure team players is provided in JSON");
        }
        JSONArray players = (JSONArray) teamJSONObject.get(PLAYERS);
        if (validateArray(players)) {
            throw new IllegalArgumentException("Please make sure at least one player is provided");
        }

        return loadPlayerJSON(players);
    }

    private Coach setTeamCoach(JSONObject teamJSONObject) throws IllegalArgumentException {
        if (validateKeyInObject(teamJSONObject, HEAD_COACH)) {
            throw new IllegalArgumentException("Please make sure team headCoach is provided in JSON");
        }
        JSONObject coachJsonObject = (JSONObject) teamJSONObject.get(HEAD_COACH);
        if (validateKeyInObject(coachJsonObject, NAME)) {
            throw new IllegalArgumentException("Please make sure team headCoach name is provided in JSON");
        }
        String coachName = (String) coachJsonObject.get(NAME);
        if (validateKeyInObject(coachJsonObject, SKATING)) {
            throw new IllegalArgumentException("Please make sure team headCoach skating is provided in JSON");
        }
        Double skating = (Double) coachJsonObject.get(SKATING);
        if (validateKeyInObject(coachJsonObject, SHOOTING)) {
            throw new IllegalArgumentException("Please make sure team headCoach shooting is provided in JSON");
        }
        Double shooting = (Double) coachJsonObject.get(SHOOTING);
        if (validateKeyInObject(coachJsonObject, CHECKING)) {
            throw new IllegalArgumentException("Please make sure team headCoach checking is provided in JSON");
        }
        Double checking = (Double) coachJsonObject.get(CHECKING);
        if (validateKeyInObject(coachJsonObject, SAVING)) {
            throw new IllegalArgumentException("Please make sure team headCoach saving is provided in JSON");
        }
        Double saving = (Double) coachJsonObject.get(SAVING);
        return setCoachVariables(coachName, skating, shooting, checking, saving);
    }

    private Manager setTeamManager(JSONObject teamJSONObject) throws IllegalArgumentException {
        if (validateKeyInObject(teamJSONObject, MANAGER)) {
            throw new IllegalArgumentException("Please make sure manager name is provided and correct");
        }
        JSONObject generalManager = (JSONObject) teamJSONObject.get(MANAGER);

        String name = (String) generalManager.get(NAME);
        if (validateString(name)) {
            throw new IllegalArgumentException("Please make sure managerName is valid");
        }

        String personality = (String) generalManager.get(PERSONALITY);
        if(validateString(personality)){
            throw new IllegalArgumentException("Please make sure manager's personality is valid");
        }

        ManagerConcrete managerConcrete = new ManagerConcrete();
        Manager manager = managerConcrete.newManagerConcrete();
        manager.setName(name);
        manager.setPersonality(personality);
        return manager;
    }

    private Coach setCoachVariables(String coachName, Double skating, Double shooting, Double checking, Double saving) {
        ICoachFactory coachConcrete = hockeyContext.getCoachFactory();
        Coach coach = coachConcrete.newCoach();
        coach.setName(coachName);
        coach.setSkating(skating);
        coach.setShooting(shooting);
        coach.setChecking(checking);
        coach.setSaving(saving);
        return coach;
    }

    private Team setTeamVariables(String teamName, Manager manager, Coach coach, List<Player> playerList) throws IllegalArgumentException {
        TeamConcrete teamConcrete = new TeamConcrete();
        Team team = teamConcrete.newTeam();
        team.setName(teamName);
        team.setManager(manager);
        team.setCoach(coach);
        team.setAiTeam(true);
        team.setPlayerList(playerList);
        team.setStrength();
        team.setActivePlayerList();
        return team;
    }

    private List<Player> loadPlayerJSON(JSONArray players) throws IllegalArgumentException {

        List<Player> playerList = new ArrayList<>();
        int captainCount = 0;
        try {
            for (Object playerObjectFromJSONArray : players) {
                JSONObject playerJsonObject = (JSONObject) playerObjectFromJSONArray;

                if (validateKeyInObject(playerJsonObject, PLAYER_NAME)) {
                    throw new IllegalArgumentException("Please make sure player name is provided");
                }
                String playerName = (String) playerJsonObject.get(PLAYER_NAME);
                if (validateString(playerName)) {
                    throw new IllegalArgumentException("Please make sure player name is valid ");
                }

                Player.Position position = validatePosition(playerJsonObject);

                if (validateKeyInObject(playerJsonObject, CAPTAIN)) {
                    throw new IllegalArgumentException("Please make sure player captain is provided");
                }
                Boolean captain = (Boolean) playerJsonObject.get(CAPTAIN);
                if (validateBoolean(captain)) {
                    throw new IllegalArgumentException("Please make sure captain is boolean");
                }
                if (captain) {
                    captainCount++;
                    if (captainCount > 1) {
                        throw new IllegalArgumentException("Please make sure only one captain in a team");
                    }
                }

                //int age = getPlayerAge(playerJsonObject);

                int skating = getPlayerSkating(playerJsonObject);

                int shooting = getPlayerShooting(playerJsonObject);

                int checking = getPlayerChecking(playerJsonObject);

                int saving = getPlayerSaving(playerJsonObject);

                Player player = setTeamPlayerVariables(playerName, position, captain, skating, shooting, checking, saving);
                player.setTeamId(teamId);
                playerList.add(player);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return playerList;

    }

    private int getPlayerSkating(JSONObject playerJsonObject) throws IllegalArgumentException {
        if (validateKeyInObject(playerJsonObject, SKATING)) {
            throw new IllegalArgumentException("Please make sure player skating is provided in JSON");
        }

        return (int) (long) playerJsonObject.get(SKATING);
    }

    private int getPlayerShooting(JSONObject playerJsonObject) {
        if (validateKeyInObject(playerJsonObject, SHOOTING)) {
            throw new IllegalArgumentException("Please make sure player shooting is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(SHOOTING);
    }

    private int getPlayerSaving(JSONObject playerJsonObject) {
        if (validateKeyInObject(playerJsonObject, SAVING)) {
            throw new IllegalArgumentException("Please make sure player saving is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(SAVING);
    }

    private int getPlayerChecking(JSONObject playerJsonObject) throws IllegalArgumentException {
        if (validateKeyInObject(playerJsonObject, CHECKING)) {
            throw new IllegalArgumentException("Please make sure player checking is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(CHECKING);
    }

    /*private int getPlayerAge(JSONObject playerJsonObject) {
        if (validateKeyInObject(playerJsonObject, AGE)) {
            throw new IllegalArgumentException("Please make sure player age is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(AGE);
    }*/

    private Player.Position validatePosition(JSONObject playerJsonObject) throws IllegalArgumentException {
        if (validateKeyInObject(playerJsonObject, POSITION)) {
            throw new IllegalArgumentException("Please make sure player position is provided");
        }
        String positionString = (String) playerJsonObject.get(POSITION);
        Player.Position positionEnum = null;
        if (positionString.equalsIgnoreCase(Player.Position.GOALIE.toString())) {
            positionEnum = Player.Position.GOALIE;
        } else if (positionString.equalsIgnoreCase(Player.Position.FORWARD.toString())) {
            positionEnum = Player.Position.FORWARD;
        } else if (positionString.equalsIgnoreCase(Player.Position.DEFENSE.toString())) {
            positionEnum = Player.Position.DEFENSE;
        }
        return positionEnum;
    }

    private Player setTeamPlayerVariables(String playerName, Player.Position position, boolean captain, int skating, int shooting, int checking, int saving) {
        PlayerConcrete playerConcrete = new PlayerConcrete();
        Player player = playerConcrete.newPlayer();
        player.setName(playerName);
        player.setPosition(position);
        player.setCaptain(captain);
        //player.setAge(age);
        player.setSkating(skating);
        player.setShooting(shooting);
        player.setChecking(checking);
        player.setSaving(saving);
        player.setStrength();
        player.setInjured(false);
        player.setIsFreeAgent(false);
        return player;
    }

    private List<Division> loadDivisionJSON(JSONArray divisions) throws IllegalArgumentException {
        List<Division> divisionList = new ArrayList<Division>();
        for (Object divisionObjectFromJSONArray : divisions) {
            JSONObject divisionJSONObject = (JSONObject) divisionObjectFromJSONArray;

            if (validateKeyInObject(divisionJSONObject, DIVISION_NAME)) {
                throw new IllegalArgumentException("Please make sure divisionName is provided in JSON");
            }
            String divisionName = (String) divisionJSONObject.get(DIVISION_NAME);
            if (validateString(divisionName)) {
                throw new IllegalArgumentException("Please make sure divisionName is valid");
            }

            if (isDivisionExists(divisionList, divisionName)) {
                throw new IllegalArgumentException("Please make sure only one division is provided");
            }

            Division division = new Division();
            division.setName(divisionName);

            if (validateKeyInObject(divisionJSONObject, TEAMS)) {
                throw new IllegalArgumentException("Please make sure teams is provided in JSON");
            }
            JSONArray teams = (JSONArray) divisionJSONObject.get(TEAMS);
            if (validateArray(teams)) {
                throw new IllegalArgumentException("Please make sure at least one team is provided");
            }

            List<Team> teamList = loadTeamJSON(teams);

            division.setTeamList(teamList);
            divisionList.add(division);
        }
        return divisionList;
    }

    private List<Conference> loadConferenceJSON(JSONArray conferences) throws IllegalArgumentException {
        List<Conference> conferenceList = new ArrayList<Conference>();
        for (Object conferenceObjectFromJSONArray : conferences) {
            JSONObject conferenceJSONObject = (JSONObject) conferenceObjectFromJSONArray;

            if (validateKeyInObject(conferenceJSONObject, CONFERENCE_NAME)) {
                throw new IllegalArgumentException("Please make sure conferenceName is provided in JSON");
            }

            String conferenceName = (String) conferenceJSONObject.get(CONFERENCE_NAME);

            if (validateString(conferenceName)) {
                throw new IllegalArgumentException("Please make sure conferenceName is valid ");
            }

            if (isConferenceExists(conferenceList, conferenceName)) {
                throw new IllegalArgumentException("Please make sure there are no duplicates in conference name");
            }

            ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
            Conference conference = conferenceConcrete.newConference();

            conference.setName(conferenceName);

            JSONArray divisions = validateDivisions(conferenceJSONObject);

            List<Division> divisionList = loadDivisionJSON(divisions);

            conference.setDivisionList(divisionList);
            conferenceList.add(conference);
        }
        return conferenceList;
    }

    private JSONArray validateDivisions(JSONObject conferenceJSONObject) {
        if (validateKeyInObject(conferenceJSONObject, DIVISIONS)) {
            throw new IllegalArgumentException("Please make sure divisions is provided in JSON");
        }
        JSONArray divisions = (JSONArray) conferenceJSONObject.get(DIVISIONS);

        if (validateArray(divisions)) {
            throw new IllegalArgumentException("Please make sure atleast one division is provided");
        }
        return divisions;
    }

    private List<Player> loadFreeAgentJSON(JSONArray freeAgents) throws IllegalArgumentException {

        ArrayList<Player> freeAgentList = new ArrayList<>();


        for (Object freeAgentObjectFromJSONArray : freeAgents) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;
            if (validateKeyInObject(freeAgentJsonObject, PLAYER_NAME)) {
                throw new IllegalArgumentException("Please make sure playerName is provided in JSON");
            }
            String playerName = (String) freeAgentJsonObject.get(PLAYER_NAME);
            if (validateString(playerName)) {
                throw new IllegalArgumentException("Please make sure player name is valid in Free Agent");
            }

            Player.Position position = validatePosition(freeAgentJsonObject);

            //int age = getPlayerAge(freeAgentJsonObject);

            int skating = getPlayerSkating(freeAgentJsonObject);

            int shooting = getPlayerShooting(freeAgentJsonObject);

            int checking = getPlayerChecking(freeAgentJsonObject);

            int saving = getPlayerSaving(freeAgentJsonObject);

            Player player = setFreePlayerVariables(playerName, position, skating, shooting, checking, saving);

            if (player.validName()) {
                freeAgentList.add(player);
            } else {
                throw new IllegalArgumentException("Free Agent Position is not valid. Please Correct it. Exiting the app!");
            }

        }
        return freeAgentList;
    }

    private Player setFreePlayerVariables(String playerName, Player.Position position, int skating, int shooting, int checking, int saving) {
        PlayerConcrete playerConcrete = new PlayerConcrete();
        Player player = playerConcrete.newPlayer();
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

    private List<Manager> loadManagerJSON(JSONArray managers) {
        List<Manager> managerList = new ArrayList<>();
        int managerSize = managers.size();

        for (int i = 0; i < managerSize; i++) {
            JSONObject generalManager = (JSONObject) managers.get(i);

            String name = (String) generalManager.get(NAME);
            if (validateString(name)) {
                throw new IllegalArgumentException("Please make sure managerName is valid");
            }

            String personality = (String) generalManager.get(PERSONALITY);
            if(validateString(personality)){
                throw new IllegalArgumentException("Please make sure manager's personality is valid");
            }

            ManagerConcrete managerConcrete = new ManagerConcrete();
            Manager manager = managerConcrete.newManagerConcrete();
            manager.setName(name);
            manager.setPersonality(personality);
            managerList.add(manager);
        }
        return managerList;
    }

    private List<Coach> loadCoachJSON(JSONArray coaches) {
        ArrayList<Coach> coachList = new ArrayList<>();
        for (Object coachObjectFromJSONArray : coaches) {
            JSONObject coachJsonObject = (JSONObject) coachObjectFromJSONArray;
            String name = (String) coachJsonObject.get(NAME);
            Double skating = (Double) coachJsonObject.get(SKATING);
            Double shooting = (Double) coachJsonObject.get(SHOOTING);
            Double checking = (Double) coachJsonObject.get(CHECKING);
            Double saving = (Double) coachJsonObject.get(SAVING);
            Coach coach = setCoachVariables(name, skating, shooting, checking, saving);
            coachList.add(coach);
        }
        return coachList;
    }

    //private IAging loadAgingJson(JSONObject agingJSONObject) {
    private Aging loadAgingJson(JSONObject agingJSONObject) {
        if (validateKeyInObject(agingJSONObject, AVERAGE_RETIREMENT_AGE)) {
            throw new IllegalArgumentException("Please make sure averageRetirementAge is provided in JSON");
        }
        int averageRetirementAge = (int) (long) agingJSONObject.get(AVERAGE_RETIREMENT_AGE);

        if (validateKeyInObject(agingJSONObject, MAXIMUM_AGE)) {
            throw new IllegalArgumentException("Please make sure maximumAge is provided in JSON");
        }
        int maximumAge = (int) (long) agingJSONObject.get(MAXIMUM_AGE);
        IAgingFactory agingFactory = hockeyContext.getAgingFactory();
        //IAging aging = agingFactory.newAging();
        Aging aging = agingFactory.newAging();
        aging.setAverageRetirementAge(averageRetirementAge);
        aging.setMaximumAge(maximumAge);
        return aging;
    }

    /*private GameResolver loadGameResolverJson(JSONObject gameResolverJSONObject) {
        if (validateKeyInObject(gameResolverJSONObject, RANDOM_WIN_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomWinChance is provided in JSON");
        }
        double randomWinChance = (Double) gameResolverJSONObject.get(RANDOM_WIN_CHANCE);
        GameResolverConcrete gameResolverConcrete = new GameResolverConcrete();
        GameResolver gameResolver = gameResolverConcrete.newGameResolver();
        gameResolver.setRandomWinChance(randomWinChance);
        return gameResolver;
    }*/

    private Injury loadInjuryJson(JSONObject injuriesJSONObject) {
        if (validateKeyInObject(injuriesJSONObject, RANDOM_INJURY_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomInjuryChance is provided in JSON");
        }
        double randomInjuryChance = (Double) injuriesJSONObject.get(RANDOM_INJURY_CHANCE);

        if (validateKeyInObject(injuriesJSONObject, INJURY_DAYS_LOW)) {
            throw new IllegalArgumentException("Please make sure injuryDaysLow is provided in JSON");
        }
        int injuryDaysLow = (int) (long) injuriesJSONObject.get(INJURY_DAYS_LOW);

        if (validateKeyInObject(injuriesJSONObject, INJURY_DAYS_HIGH)) {
            throw new IllegalArgumentException("Please make sure injuryDaysHigh is provided in JSON");
        }
        int injuryDaysHigh = (int) (long) injuriesJSONObject.get(INJURY_DAYS_HIGH);

        InjuryConcrete injuryConcrete = new InjuryConcrete();
        Injury injury = injuryConcrete.newInjury();
        injury.setRandomInjuryChance(randomInjuryChance);
        injury.setInjuryDaysLow(injuryDaysLow);
        injury.setInjuryDaysHigh(injuryDaysHigh);
        return injury;
    }

    private Training loadTrainingJson(JSONObject trainingJSONObject) {
        if (validateKeyInObject(trainingJSONObject, DAYS_UNTIL_STAT_INCREASE_CHECK)) {
            throw new IllegalArgumentException("Please make sure daysUntilStatIncreaseCheck is provided in JSON");
        }
        int daysUntil = (int) (long) trainingJSONObject.get(DAYS_UNTIL_STAT_INCREASE_CHECK);
        TrainingConcrete trainingConcrete = new TrainingConcrete();
        Training training = trainingConcrete.newTraining();
        training.setDaysUntilStatIncreaseCheck(daysUntil);
        return training;
    }

    private Trading loadTradingJson(JSONObject tradingJSONObject) {
        if (validateKeyInObject(tradingJSONObject, LOSS_POINT)) {
            throw new IllegalArgumentException("Please make sure lossPoint is provided in JSON");
        }
        int lossPoint = (int) (long) tradingJSONObject.get(LOSS_POINT);

        if (validateKeyInObject(tradingJSONObject, RANDOM_TRADE_OFFER_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomTradeOfferChance is provided in JSON");
        }
        double randomTradeOfferChance = (Double) tradingJSONObject.get(RANDOM_TRADE_OFFER_CHANCE);

        if (validateKeyInObject(tradingJSONObject, MAX_PLAYERS_PER_TRADE)) {
            throw new IllegalArgumentException("Please make sure maxPlayersPerTrade is provided in JSON");
        }
        int maxPlayersPerTrade = (int) (long) tradingJSONObject.get(MAX_PLAYERS_PER_TRADE);

        if (validateKeyInObject(tradingJSONObject, RANDOM_ACCEPTANCE_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomAcceptanceChance is provided in JSON");
        }
        double randomAcceptanceChance = (Double) tradingJSONObject.get(RANDOM_ACCEPTANCE_CHANCE);

        if(validateKeyInObject(tradingJSONObject, GM_TABLE)){
            throw new IllegalArgumentException("Please make sure gmTable is provided in JSON");
        }

        JSONObject gmTableJsonObject = (JSONObject) tradingJSONObject.get(GM_TABLE);
        Map<String, Double> gmTable = new HashMap<>();
        for(Object key : gmTableJsonObject.keySet()){
            String attribute = (String) key;
            double attributeValue = (Double) gmTableJsonObject.get(attribute);
            gmTable.put(attribute, attributeValue);
        }

        TradingConcrete tradingConcrete = new TradingConcrete();
        Trading trading = tradingConcrete.newTrading();
        trading.setLossPoint(lossPoint);
        trading.setRandomTradeOfferChance(randomTradeOfferChance);
        trading.setMaxPlayersPerTrade(maxPlayersPerTrade);
        trading.setRandomAcceptanceChance(randomAcceptanceChance);
        trading.setGmTable(gmTable);
        return trading;
    }

    private boolean validateBoolean(Boolean bool) {
        return bool == null;
    }

    private boolean validateString(String name) {
        if (name == null || name.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateArray(JSONArray array) {
        if (array == null || array.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateKeyInObject(JSONObject object, String key) {
        if (object.containsKey(key)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isDivisionExists(List<Division> list, String name) {
        boolean isExist = false;
        for (Division division : list) {
            if (division.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private boolean isConferenceExists(List<Conference> list, String name) {
        boolean isExist = false;
        for (Conference conference : list) {
            if (conference.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private boolean isTeamExistsInDivision(List<Team> list, String name) {
        boolean isExist = false;
        for (Team team : list) {
            if (team.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private boolean isTeamExistsInLeague(String teamName) {
        if (appearedName.add(teamName)) {
            return false;
        } else {
            return true;
        }
    }
}

