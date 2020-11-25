package simulation.state;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import presentation.ConsoleOutput;
import simulation.factory.*;
import simulation.model.*;

import java.time.LocalDate;
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
    private static final String FREE_AGENTS = "freeAgents";
    private static final String MANAGERS = "generalManagers";
    private static final String COACHES = "coaches";
    private static final String GAMEPLAY_CONFIG = "gameplayConfig";
    private static final String AGING = "aging";
    private static final String INJURIES = "injuries";
    private static final String TRAINING = "training";
    private static final String TRADING = "trading";
    private static final String AVERAGE_RETIREMENT_AGE = "averageRetirementAge";
    private static final String MAXIMUM_AGE = "maximumAge";
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
    public static final String BIRTH_DAY = "birthDay";
    public static final String BIRTH_MONTH = "birthMonth";
    public static final String BIRTH_YEAR = "birthYear";
    public static final String STAT_DECAY_CHANCE = "statDecayChance";
    private static final String PERSONALITY = "personality";
    private final Set<String> appearedName = new HashSet<>();
    private IHockeyContext hockeyContext;
    private JSONObject jsonFromInput;
    private ILeague league;
    private int leagueId;
    private int teamId;
    private int conferenceId;
    private int divisionId;
    private static Logger log = Logger.getLogger(ImportState.class);

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

            IGamePlayConfig gamePlayConfig = loadGamePlayConfigJSON(gameplayConfigJSONObject);

            List<IConference> conferenceList = loadConferenceJSON(conferences);

            IFreeAgentFactory freeAgentConcrete = hockeyContext.getFreeAgentFactory();
            IFreeAgent freeAgent = freeAgentConcrete.newFreeAgent();
            List<IPlayer> freeAgentList = loadFreeAgentJSON(freeAgents);
            freeAgent.setPlayerList(freeAgentList);

            List<ICoach> coachList = loadCoachJSON(coaches);

            List<IManager> managerList = loadManagerJSON(managers);

            List<IPlayer> retiredPlayerList = new ArrayList<>();

            setLeagueVariables(leagueName, gamePlayConfig, conferenceList, freeAgent, coachList, managerList, retiredPlayerList);

        } catch (Exception e) {
            log.error("ImportState: parseJSONAndInstantiateLeague: Exception: " + e);
            throw e;
        }
    }

    private void setLeagueVariables(String leagueName, IGamePlayConfig gamePlayConfig,
                                    List<IConference> conferenceList, IFreeAgent freeAgent,
                                    List<ICoach> coachList, List<IManager> managerList, List<IPlayer> retiredPlayerList) {
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

    private IGamePlayConfig loadGamePlayConfigJSON(JSONObject gameplayConfigJSONObject) throws IllegalArgumentException {
        IGamePlayConfigFactory gamePlayConfigConcrete = hockeyContext.getGamePlayConfigFactory();
        IGamePlayConfig gamePlayConfig = gamePlayConfigConcrete.newGamePlayConfig();

        if (validateKeyInObject(gameplayConfigJSONObject, AGING)) {
            throw new IllegalArgumentException("Please make sure aging is provided in JSON");
        }
        JSONObject agingJSONObject = (JSONObject) gameplayConfigJSONObject.get(AGING);
        IAging aging = loadAgingJson(agingJSONObject);
        aging.setLeagueId(leagueId);
        gamePlayConfig.setAging(aging);

        if (validateKeyInObject(gameplayConfigJSONObject, INJURIES)) {
            throw new IllegalArgumentException("Please make sure injuries is provided in JSON");
        }
        JSONObject injuriesJSONObject = (JSONObject) gameplayConfigJSONObject.get(INJURIES);
        IInjury injury = loadInjuryJson(injuriesJSONObject);
        injury.setLeagueId(leagueId);
        gamePlayConfig.setInjury(injury);

        if (validateKeyInObject(gameplayConfigJSONObject, TRAINING)) {
            throw new IllegalArgumentException("Please make sure training is provided in JSON");
        }
        JSONObject trainingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRAINING);
        ITraining training = loadTrainingJson(trainingJSONObject);
        training.setLeagueId(leagueId);
        gamePlayConfig.setTraining(training);

        if (validateKeyInObject(gameplayConfigJSONObject, TRADING)) {
            throw new IllegalArgumentException("Please make sure trading is provided in JSON");
        }
        JSONObject tradingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRADING);
        ITrading trading = loadTradingJson(tradingJSONObject);
        gamePlayConfig.setTrading(trading);
        gamePlayConfig.setLeagueId(leagueId);
        return gamePlayConfig;
    }

    private List<ITeam> loadTeamJSON(JSONArray teams) throws IllegalArgumentException {

        List<ITeam> teamList = new ArrayList<>();
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

            IManager manager = setTeamManager(teamJSONObject);

            ICoach coach = setTeamCoach(teamJSONObject);

            List<IPlayer> playerList = setTeamPlayerList(teamJSONObject);

            ITeam team = setTeamVariables(teamName, manager, coach, playerList);
            team.setDivisionId(divisionId);
            teamId = team.getId();
            manager.setTeamId(teamId);
            manager.setLeagueId(leagueId);
            coach.setTeamId(teamId);
            coach.setLeagueId(leagueId);
            teamList.add(team);
        }
        return teamList;
    }

    private List<IPlayer> setTeamPlayerList(JSONObject teamJSONObject) throws IllegalArgumentException {
        if (validateKeyInObject(teamJSONObject, PLAYERS)) {
            throw new IllegalArgumentException("Please make sure team players is provided in JSON");
        }
        JSONArray players = (JSONArray) teamJSONObject.get(PLAYERS);
        if (validateArray(players)) {
            throw new IllegalArgumentException("Please make sure at least one player is provided");
        }

        return loadPlayerJSON(players);
    }

    private ICoach setTeamCoach(JSONObject teamJSONObject) throws IllegalArgumentException {
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

    private IManager setTeamManager(JSONObject teamJSONObject) throws IllegalArgumentException {
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

        IManagerFactory managerConcrete = hockeyContext.getManagerFactory();
        IManager manager = managerConcrete.newManagerConcrete();
        manager.setName(name);
        manager.setPersonality(personality);
        return manager;
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

    private ITeam setTeamVariables(String teamName, IManager manager, ICoach coach, List<IPlayer> playerList) throws IllegalArgumentException {
        ITeamFactory teamConcrete = hockeyContext.getTeamFactory();
        ITeam team = teamConcrete.newTeam();
        team.setName(teamName);
        team.setManager(manager);
        team.setCoach(coach);
        team.setAiTeam(true);
        for (IPlayer player : playerList) {
            player.setTeamId(team.getId());
        }
        team.setPlayerList(playerList);
        team.setStrength();
        team.setActivePlayerList();
        return team;
    }

    private List<IPlayer> loadPlayerJSON(JSONArray players) throws IllegalArgumentException {

        List<IPlayer> playerList = new ArrayList<>();
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

                Position position = validatePosition(playerJsonObject);

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

                LocalDate birthday = getPlayerBirthday(playerJsonObject);

                int skating = getPlayerSkating(playerJsonObject);

                int shooting = getPlayerShooting(playerJsonObject);

                int checking = getPlayerChecking(playerJsonObject);

                int saving = getPlayerSaving(playerJsonObject);

                IPlayer player = setTeamPlayerVariables(playerName, position, captain, birthday, skating, shooting, checking, saving);

                player.setFreeAgentId(player.getId());
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

    private LocalDate getPlayerBirthday(JSONObject playerJsonObject) {
        if (validateKeyInObject(playerJsonObject, BIRTH_DAY)) {
            throw new IllegalArgumentException("Please make sure the player's birth day is provided in JSON");
        }
        int birthDay = (int) (long) playerJsonObject.get(BIRTH_DAY);
        if (birthDay < 1 || birthDay > 31) {
            throw new IllegalArgumentException("Please make sure the player's birth day is within 1 to 31");
        }
        if (validateKeyInObject(playerJsonObject, BIRTH_MONTH)) {
            throw new IllegalArgumentException("Please make sure the player's birth month is provided in JSON");
        }
        int birthMonth = (int) (long) playerJsonObject.get(BIRTH_MONTH);
        if (birthMonth < 1 || birthMonth > 12) {
            throw new IllegalArgumentException("Please make sure the player's birth month is within 1 to 12");
        }
        if (validateKeyInObject(playerJsonObject, BIRTH_YEAR)) {
            throw new IllegalArgumentException("Please make sure the player's birthDay is provided in JSON");
        }
        int birthYear = (int) (long) playerJsonObject.get(BIRTH_YEAR);
        if (birthYear < 1920 || birthYear > 2020) {
            throw new IllegalArgumentException("Please make sure the player's birth year is within 1920 to 2020");
        }
        return LocalDate.of(birthYear, birthMonth, birthDay);
    }

    private Position validatePosition(JSONObject playerJsonObject) throws IllegalArgumentException {
        if (validateKeyInObject(playerJsonObject, POSITION)) {
            throw new IllegalArgumentException("Please make sure player position is provided");
        }
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

    private IPlayer setTeamPlayerVariables(String playerName, Position position,
                                           boolean captain, LocalDate birthday,
                                           int skating, int shooting, int checking, int saving) {
        IPlayerFactory playerConcrete = hockeyContext.getPlayerFactory();
        IPlayer player = playerConcrete.newPlayer();
        player.setName(playerName);
        player.setPosition(position);
        player.setCaptain(captain);
        player.setBirthday(birthday);
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

    private List<IDivision> loadDivisionJSON(JSONArray divisions) throws IllegalArgumentException {
        List<IDivision> divisionList = new ArrayList<>();
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
            division.setConferenceId(conferenceId);
            divisionId = division.getId();

            if (validateKeyInObject(divisionJSONObject, TEAMS)) {
                throw new IllegalArgumentException("Please make sure teams is provided in JSON");
            }
            JSONArray teams = (JSONArray) divisionJSONObject.get(TEAMS);
            if (validateArray(teams)) {
                throw new IllegalArgumentException("Please make sure at least one team is provided");
            }

            List<ITeam> teamList = loadTeamJSON(teams);

            division.setTeamList(teamList);
            divisionList.add(division);
        }
        return divisionList;
    }

    private List<IConference> loadConferenceJSON(JSONArray conferences) throws IllegalArgumentException {
        List<IConference> conferenceList = new ArrayList<>();
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

            IConferenceFactory conferenceConcrete = hockeyContext.getConferenceFactory();
            IConference conference = conferenceConcrete.newConference();
            conference.setLeagueId(leagueId);
            conference.setName(conferenceName);
            conferenceId = conference.getId();

            JSONArray divisions = validateDivisions(conferenceJSONObject);

            List<IDivision> divisionList = loadDivisionJSON(divisions);

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
            throw new IllegalArgumentException("Please make sure at least one division is provided");
        }
        return divisions;
    }

    private List<IPlayer> loadFreeAgentJSON(JSONArray freeAgents) throws IllegalArgumentException {

        List<IPlayer> freeAgentList = new ArrayList<>();

        for (Object freeAgentObjectFromJSONArray : freeAgents) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;
            if (validateKeyInObject(freeAgentJsonObject, PLAYER_NAME)) {
                throw new IllegalArgumentException("Please make sure playerName is provided in JSON");
            }
            String playerName = (String) freeAgentJsonObject.get(PLAYER_NAME);
            if (validateString(playerName)) {
                throw new IllegalArgumentException("Please make sure player name is valid in Free Agent");
            }

            Position position = validatePosition(freeAgentJsonObject);

            LocalDate birthday = getPlayerBirthday(freeAgentJsonObject);

            int skating = getPlayerSkating(freeAgentJsonObject);

            int shooting = getPlayerShooting(freeAgentJsonObject);

            int checking = getPlayerChecking(freeAgentJsonObject);

            int saving = getPlayerSaving(freeAgentJsonObject);

            IPlayer player = setFreePlayerVariables(playerName, position, birthday, skating, shooting, checking, saving);

            if (player.validName()) {
                freeAgentList.add(player);
            } else {
                throw new IllegalArgumentException("Free Agent Position is not valid. Please Correct it. Exiting the app!");
            }

        }
        return freeAgentList;
    }

    private IPlayer setFreePlayerVariables(String playerName, Position position, LocalDate birthday, int skating, int shooting, int checking, int saving) {
        IPlayerFactory playerConcrete = hockeyContext.getPlayerFactory();
        IPlayer player = playerConcrete.newPlayer();
        player.setName(playerName);
        player.setPosition(position);
        player.setBirthday(birthday);
        player.setSkating(skating);
        player.setShooting(shooting);
        player.setChecking(checking);
        player.setSaving(saving);
        player.setStrength();
        player.setFreeAgentId(player.getId());
        player.setInjured(false);
        player.setIsFreeAgent(true);
        return player;
    }

    private List<IManager> loadManagerJSON(JSONArray managers) {
        List<IManager> managerList = new ArrayList<>();
        int managerSize = managers.size();

        for (int i = 0; i < managerSize; i++) {
            JSONObject generalManager = (JSONObject) managers.get(i);

            String name = (String) generalManager.get(NAME);
            if (validateString(name)) {
                throw new IllegalArgumentException("Please make sure managerName is valid");
            }

            String personality = (String) generalManager.get(PERSONALITY);
            if (validateString(personality)) {
                throw new IllegalArgumentException("Please make sure manager's personality is valid");
            }

            IManagerFactory managerConcrete = hockeyContext.getManagerFactory();
            IManager manager = managerConcrete.newManagerConcrete();
            manager.setName(name);
            manager.setPersonality(personality);
            managerList.add(manager);
        }
        return managerList;
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

    private IAging loadAgingJson(JSONObject agingJSONObject) {
        if (validateKeyInObject(agingJSONObject, AVERAGE_RETIREMENT_AGE)) {
            throw new IllegalArgumentException("Please make sure averageRetirementAge is provided in JSON");
        }
        int averageRetirementAge = (int) (long) agingJSONObject.get(AVERAGE_RETIREMENT_AGE);

        if (validateKeyInObject(agingJSONObject, MAXIMUM_AGE)) {
            throw new IllegalArgumentException("Please make sure maximumAge is provided in JSON");
        }
        int maximumAge = (int) (long) agingJSONObject.get(MAXIMUM_AGE);

        if (validateKeyInObject(agingJSONObject, STAT_DECAY_CHANCE)) {
            throw new IllegalArgumentException("Please make sure statDecayChance is provided in JSON");
        }
        Double statDecayChance = (Double) agingJSONObject.get(STAT_DECAY_CHANCE);

        IAgingFactory agingFactory = hockeyContext.getAgingFactory();
        IAging aging = agingFactory.newAging();
        aging.setAverageRetirementAge(averageRetirementAge);
        aging.setMaximumAge(maximumAge);
        aging.setStatDecayChance(statDecayChance);
        return aging;
    }

    private IInjury loadInjuryJson(JSONObject injuriesJSONObject) {
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

        IInjuryFactory injuryConcrete = hockeyContext.getInjuryFactory();
        IInjury injury = injuryConcrete.newInjury();
        injury.setRandomInjuryChance(randomInjuryChance);
        injury.setInjuryDaysLow(injuryDaysLow);
        injury.setInjuryDaysHigh(injuryDaysHigh);
        return injury;
    }

    private ITraining loadTrainingJson(JSONObject trainingJSONObject) {
        if (validateKeyInObject(trainingJSONObject, DAYS_UNTIL_STAT_INCREASE_CHECK)) {
            throw new IllegalArgumentException("Please make sure daysUntilStatIncreaseCheck is provided in JSON");
        }
        int daysUntil = (int) (long) trainingJSONObject.get(DAYS_UNTIL_STAT_INCREASE_CHECK);
        ITrainingFactory trainingConcrete = hockeyContext.getTrainingFactory();
        ITraining training = trainingConcrete.newTraining();
        training.setDaysUntilStatIncreaseCheck(daysUntil);
        return training;
    }

    private ITrading loadTradingJson(JSONObject tradingJSONObject) {
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

        if (validateKeyInObject(tradingJSONObject, GM_TABLE)) {
            throw new IllegalArgumentException("Please make sure gmTable is provided in JSON");
        }

        JSONObject gmTableJsonObject = (JSONObject) tradingJSONObject.get(GM_TABLE);
        Map<String, Double> gmTable = new HashMap<>();
        for (Object key : gmTableJsonObject.keySet()) {
            String attribute = (String) key;
            double attributeValue = (Double) gmTableJsonObject.get(attribute);
            gmTable.put(attribute, attributeValue);
        }

        ITradingFactory tradingConcrete = hockeyContext.getTradingFactory();
        ITrading trading = tradingConcrete.newTrading();
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

    private boolean isDivisionExists(List<IDivision> list, String name) {
        boolean isExist = false;
        for (IDivision division : list) {
            if (division.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private boolean isConferenceExists(List<IConference> list, String name) {
        boolean isExist = false;
        for (IConference conference : list) {
            if (conference.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private boolean isTeamExistsInDivision(List<ITeam> list, String name) {
        boolean isExist = false;
        for (ITeam team : list) {
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

