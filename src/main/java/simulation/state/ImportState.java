package simulation.state;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import simulation.factory.*;
import simulation.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    private HockeyContext hockeyContext;
    private String filePath;
    private JSONObject jsonFromInput;
    private League league;
    private final Set<String> appearedName = new HashSet<>();


    public ImportState(HockeyContext hockeyContext, JSONObject jsonFromInput) {
        this.jsonFromInput = jsonFromInput;
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
        if (league == null) {
            LeagueConcrete leagueConcrete = new LeagueConcrete();
            league = leagueConcrete.newLeague();
        }
    }

    @Override
    public void entry() {

    }

    @Override
    public void process() throws IllegalArgumentException {
        parseJSONAndInstantiateLeague(jsonFromInput);
        hockeyContext.getUser().setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        return null;
    }

    private void parseJSONAndInstantiateLeague(JSONObject leagueJSON) throws IllegalArgumentException {

        if (notValidKeyInObject(leagueJSON, LEAGUE_NAME)) {
            throw new IllegalArgumentException("Please make sure league name is provided in JSON");
        }
        String leagueName = (String) leagueJSON.get(LEAGUE_NAME);
        if (notValidString(leagueName)) {
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

        setLeagueVariables(leagueName, gamePlayConfig, conferenceList, freeAgent, coachList, managerList);

    }

    private void setLeagueVariables(String leagueName, GamePlayConfig gamePlayConfig, List<Conference> conferenceList, FreeAgent freeAgent, List<Coach> coachList, List<Manager> managerList) {
        league.setName(leagueName);
        league.setConferenceList(conferenceList);
        league.setFreeAgent(freeAgent);
        league.setManagerList(managerList);
        league.setCoachList(coachList);
        league.setGamePlayConfig(gamePlayConfig);
    }

    private JSONArray validateManagers(JSONObject leagueJSON) throws IllegalArgumentException {
        if (notValidKeyInObject(leagueJSON, MANAGERS)) {
            throw new IllegalArgumentException("Please make sure generalManagers are provided in JSON");
        }
        JSONArray managers = (JSONArray) leagueJSON.get(MANAGERS);
        if (notValidArray(managers)) {
            throw new IllegalArgumentException("Please make sure at least one Manager is free");
        }
        return managers;
    }

    private JSONArray validateCoaches(JSONObject leagueJSON) throws IllegalArgumentException {
        if (notValidKeyInObject(leagueJSON, COACHES)) {
            throw new IllegalArgumentException("Please make sure coaches are provided in JSON");
        }
        JSONArray coaches = (JSONArray) leagueJSON.get(COACHES);
        if (notValidArray(coaches)) {
            throw new IllegalArgumentException("Please make sure at least one Coach is free");
        }
        return coaches;
    }

    private JSONArray validateFreeAgents(JSONObject leagueJSON) throws IllegalArgumentException {
        if (notValidKeyInObject(leagueJSON, FREE_AGENTS)) {
            throw new IllegalArgumentException("Please make sure freeAgents are provided in JSON");
        }
        JSONArray freeAgents = (JSONArray) leagueJSON.get(FREE_AGENTS);
        if (notValidArray(freeAgents)) {
            throw new IllegalArgumentException("Please make sure at least one Player is in Free Agent");
        }
        return freeAgents;
    }

    private JSONArray validateConferences(JSONObject leagueJSON) throws IllegalArgumentException {
        if (notValidKeyInObject(leagueJSON, CONFERENCES)) {
            throw new IllegalArgumentException("Please make sure conferences are provided in JSON");
        }
        JSONArray conferences = (JSONArray) leagueJSON.get(CONFERENCES);
        if (notValidArray(conferences)) {
            throw new IllegalArgumentException("Please make sure at least one conference is provided");
        }
        return conferences;
    }

    private JSONObject validateGameCofig(JSONObject leagueJSON) throws IllegalArgumentException {
        if (notValidKeyInObject(leagueJSON, GAMEPLAY_CONFIG)) {
            throw new IllegalArgumentException("Please make sure gameplayConfig is provided in JSON");
        }
        return (JSONObject) leagueJSON.get(GAMEPLAY_CONFIG);
    }

    private GamePlayConfig loadGamePlayConfigJSON(JSONObject gameplayConfigJSONObject) throws IllegalArgumentException {
        GamePlayConfigConcrete gamePlayConfigConcrete = new GamePlayConfigConcrete();
        GamePlayConfig gamePlayConfig = gamePlayConfigConcrete.newGamePlayConfig();

        if (notValidKeyInObject(gameplayConfigJSONObject, AGING)) {
            throw new IllegalArgumentException("Please make sure aging is provided in JSON");
        }
        JSONObject agingJSONObject = (JSONObject) gameplayConfigJSONObject.get(AGING);
        Aging aging = loadAgingJson(agingJSONObject);
        gamePlayConfig.setAging(aging);

        if (notValidKeyInObject(gameplayConfigJSONObject, GAME_RESOLVER)) {
            throw new IllegalArgumentException("Please make sure gameResolver is provided in JSON");
        }
        JSONObject gameResolverJSONObject = (JSONObject) gameplayConfigJSONObject.get(GAME_RESOLVER);
        GameResolver gameResolver = loadGameResolverJson(gameResolverJSONObject);
        gamePlayConfig.setGameResolver(gameResolver);

        if (notValidKeyInObject(gameplayConfigJSONObject, INJURIES)) {
            throw new IllegalArgumentException("Please make sure injuries is provided in JSON");
        }
        JSONObject injuriesJSONObject = (JSONObject) gameplayConfigJSONObject.get(INJURIES);
        Injury injury = loadInjuryJson(injuriesJSONObject);
        gamePlayConfig.setInjury(injury);

        if (notValidKeyInObject(gameplayConfigJSONObject, TRAINING)) {
            throw new IllegalArgumentException("Please make sure training is provided in JSON");
        }
        JSONObject trainingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRAINING);
        Training training = loadTrainingJson(trainingJSONObject);
        gamePlayConfig.setTraining(training);

        if (notValidKeyInObject(gameplayConfigJSONObject, TRADING)) {
            throw new IllegalArgumentException("Please make sure trading is provided in JSON");
        }
        JSONObject tradingJSONObject = (JSONObject) gameplayConfigJSONObject.get(TRADING);
        Trading trading = loadTradingJson(tradingJSONObject);
        gamePlayConfig.setTrading(trading);
        return gamePlayConfig;
    }

    private List<Team> loadTeamJSON(JSONArray teams) throws IllegalArgumentException {
        ArrayList<Team> teamList = new ArrayList<>();
        for (Object teamObjectFromJSONArray : teams) {

            JSONObject teamJSONObject = (JSONObject) teamObjectFromJSONArray;

            if (notValidKeyInObject(teamJSONObject, TEAM_NAME)) {
                throw new IllegalArgumentException("Please make sure team name is provided and correct");
            }
            String teamName = (String) teamJSONObject.get(TEAM_NAME);
            if (notValidString(teamName)) {
                throw new IllegalArgumentException("Please make sure team name is valid");
            }

            if (isTeamExists(teamList, teamName)) {
                throw new IllegalArgumentException("Please make sure team name is unique in one division");
            }
            if (!appearedName.add(teamName)) {
                throw new IllegalArgumentException("Please make sure team name is unique in one league");
            }

            Manager manager = setTeamManager(teamJSONObject);

            Coach coach = setTeamCoach(teamJSONObject);

            List<Player> playerList = setTeamPlayerList(teamJSONObject);

            Team team = setTeamVariables(teamName, manager, coach, playerList);

            teamList.add(team);
        }
        return teamList;
    }

    private List<Player> setTeamPlayerList(JSONObject teamJSONObject) throws IllegalArgumentException {
        if (notValidKeyInObject(teamJSONObject, PLAYERS)) {
            throw new IllegalArgumentException("Please make sure team players is provided in JSON");
        }
        JSONArray players = (JSONArray) teamJSONObject.get(PLAYERS);
        if (notValidArray(players)) {
            throw new IllegalArgumentException("Please make sure at least one player is provided");
        }

        return loadPlayerJSON(players);
    }

    private Coach setTeamCoach(JSONObject teamJSONObject) throws IllegalArgumentException {
        if (notValidKeyInObject(teamJSONObject, HEAD_COACH)) {
            throw new IllegalArgumentException("Please make sure team headCoach is provided in JSON");
        }
        JSONObject coachJsonObject = (JSONObject) teamJSONObject.get(HEAD_COACH);
        if (notValidKeyInObject(coachJsonObject, NAME)) {
            throw new IllegalArgumentException("Please make sure team headCoach name is provided in JSON");
        }
        String coachName = (String) coachJsonObject.get(NAME);
        if (notValidKeyInObject(coachJsonObject, SKATING)) {
            throw new IllegalArgumentException("Please make sure team headCoach skating is provided in JSON");
        }
        Double skating = (Double) coachJsonObject.get(SKATING);
        if (notValidKeyInObject(coachJsonObject, SHOOTING)) {
            throw new IllegalArgumentException("Please make sure team headCoach shooting is provided in JSON");
        }
        Double shooting = (Double) coachJsonObject.get(SHOOTING);
        if (notValidKeyInObject(coachJsonObject, CHECKING)) {
            throw new IllegalArgumentException("Please make sure team headCoach checking is provided in JSON");
        }
        Double checking = (Double) coachJsonObject.get(CHECKING);
        if (notValidKeyInObject(coachJsonObject, SAVING)) {
            throw new IllegalArgumentException("Please make sure team headCoach saving is provided in JSON");
        }
        Double saving = (Double) coachJsonObject.get(SAVING);
        return setCoachVariables(coachName, skating, shooting, checking, saving);
    }

    private Manager setTeamManager(JSONObject teamJSONObject) throws IllegalArgumentException {
        if (notValidKeyInObject(teamJSONObject, MANAGER)) {
            throw new IllegalArgumentException("Please make sure manager name is provided and correct");
        }
        String managerName = (String) teamJSONObject.get(MANAGER);
        if (notValidString(managerName)) {
            throw new IllegalArgumentException("Please make sure managerName is valid");
        }
        ManagerConcrete managerConcrete = new ManagerConcrete();
        Manager manager = managerConcrete.newManagerConcrete();
        manager.setName(managerName);
        return manager;
    }

    private Coach setCoachVariables(String coachName, Double skating, Double shooting, Double checking, Double saving) {
        CoachConcrete coachConcrete = new CoachConcrete();
        Coach coach = coachConcrete.newCoach();
        coach.setName(coachName);
        coach.setSkating(skating);
        coach.setShooting(shooting);
        coach.setChecking(checking);
        coach.setSaving(saving);
        return coach;
    }

    private Team setTeamVariables(String teamName, Manager manager, Coach coach, List<Player> playerList) {
        TeamConcrete teamConcrete = new TeamConcrete();
        Team team = teamConcrete.newTeam();
        team.setName(teamName);
        team.setManager(manager);
        team.setCoach(coach);
        team.setAiTeam(true);
        team.setPlayerList(playerList);
        team.setStrength();

        return team;
    }

    private List<Player> loadPlayerJSON(JSONArray players) throws IllegalArgumentException {

        ArrayList<Player> playerList = new ArrayList<>();
        int captainCount = 0;
        try {
            for (Object playerObjectFromJSONArray : players) {
                JSONObject playerJsonObject = (JSONObject) playerObjectFromJSONArray;

                if (notValidKeyInObject(playerJsonObject, PLAYER_NAME)) {
                    throw new IllegalArgumentException("Please make sure player name is provided");
                }
                String playerName = (String) playerJsonObject.get(PLAYER_NAME);
                if (notValidString(playerName)) {
                    throw new IllegalArgumentException("Please make sure player name is valid ");
                }

                Player.Position position = validatePosition(playerJsonObject);

                if (notValidKeyInObject(playerJsonObject, CAPTAIN)) {
                    throw new IllegalArgumentException("Please make sure player captain is provided");
                }
                Boolean captain = (Boolean) playerJsonObject.get(CAPTAIN);
                if (notBoolean(captain)) {
                    throw new IllegalArgumentException("Please make sure captain is boolean");
                }
                if (captain) {
                    captainCount++;
                    if (captainCount > 1) {
                        throw new IllegalArgumentException("Please make sure only one captain in a team");
                    }
                }

                int age = getPlayerAge(playerJsonObject);

                int skating = getPlayerSkating(playerJsonObject);

                int shooting = getPlayerShooting(playerJsonObject);

                int checking = getPlayerChecking(playerJsonObject);

                int saving = getPlayerSaving(playerJsonObject);

                Player player = setTeamPlayerVariables(playerName, position, captain, age, skating, shooting, checking, saving);

                playerList.add(player);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerList;

    }

    private int getPlayerSkating(JSONObject playerJsonObject) throws IllegalArgumentException {
        if (notValidKeyInObject(playerJsonObject, SKATING)) {
            throw new IllegalArgumentException("Please make sure player skating is provided in JSON");
        }

        return (int) (long) playerJsonObject.get(SKATING);
    }

    private int getPlayerShooting(JSONObject playerJsonObject) {
        if (notValidKeyInObject(playerJsonObject, SHOOTING)) {
            throw new IllegalArgumentException("Please make sure player shooting is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(SHOOTING);
    }

    private int getPlayerSaving(JSONObject playerJsonObject) {
        if (notValidKeyInObject(playerJsonObject, SAVING)) {
            throw new IllegalArgumentException("Please make sure player saving is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(SAVING);
    }

    private int getPlayerChecking(JSONObject playerJsonObject) throws IllegalArgumentException {
        if (notValidKeyInObject(playerJsonObject, CHECKING)) {
            throw new IllegalArgumentException("Please make sure player checking is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(CHECKING);
    }

    private int getPlayerAge(JSONObject playerJsonObject) {
        if (notValidKeyInObject(playerJsonObject, AGE)) {
            throw new IllegalArgumentException("Please make sure player age is provided in JSON");
        }
        return (int) (long) playerJsonObject.get(AGE);
    }

    private Player.Position validatePosition(JSONObject playerJsonObject) throws IllegalArgumentException {
        if (notValidKeyInObject(playerJsonObject, POSITION)) {
            throw new IllegalArgumentException("Please make sure player position is provided");
        }
        String positionString = (String) playerJsonObject.get(POSITION);

        return Player.Position.valueOf(positionString);
    }

    private Player setTeamPlayerVariables(String playerName, Player.Position position, boolean captain, int age, int skating, int shooting, int checking, int saving) {
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
        player.setInjured(false);
        player.setIsFreeAgent(false);
        return player;
    }

    private List<Division> loadDivisionJSON(JSONArray divisions) throws IllegalArgumentException {
        ArrayList<Division> divisionList = new ArrayList<Division>();
        for (Object divisionObjectFromJSONArray : divisions) {
            JSONObject divisionJSONObject = (JSONObject) divisionObjectFromJSONArray;

            if (notValidKeyInObject(divisionJSONObject, DIVISION_NAME)) {
                throw new IllegalArgumentException("Please make sure divisionName is provided in JSON");
            }
            String divisionName = (String) divisionJSONObject.get(DIVISION_NAME);
            if (notValidString(divisionName)) {
                throw new IllegalArgumentException("Please make sure divisionName is valid");
            }

            if (isDivisionExists(divisionList, divisionName)) {
                throw new IllegalArgumentException("Please make sure only one division is provided");
            }

            Division division = new Division();
            division.setName(divisionName);

            if (notValidKeyInObject(divisionJSONObject, TEAMS)) {
                throw new IllegalArgumentException("Please make sure teams is provided in JSON");
            }
            JSONArray teams = (JSONArray) divisionJSONObject.get(TEAMS);
            if (notValidArray(teams)) {
                throw new IllegalArgumentException("Please make sure at least one team is provided");
            }

            List<Team> teamList = loadTeamJSON(teams);

            division.setTeamList(teamList);
            divisionList.add(division);
        }
        return divisionList;
    }

    private List<Conference> loadConferenceJSON(JSONArray conferences) throws IllegalArgumentException {
        ArrayList<Conference> conferenceList = new ArrayList<Conference>();
        for (Object conferenceObjectFromJSONArray : conferences) {
            JSONObject conferenceJSONObject = (JSONObject) conferenceObjectFromJSONArray;

            if (notValidKeyInObject(conferenceJSONObject, CONFERENCE_NAME)) {
                throw new IllegalArgumentException("Please make sure conferenceName is provided in JSON");
            }

            String conferenceName = (String) conferenceJSONObject.get(CONFERENCE_NAME);

            if (notValidString(conferenceName)) {
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
        if (notValidKeyInObject(conferenceJSONObject, DIVISIONS)) {
            throw new IllegalArgumentException("Please make sure divisions is provided in JSON");
        }
        JSONArray divisions = (JSONArray) conferenceJSONObject.get(DIVISIONS);

        if (notValidArray(divisions)) {
            throw new IllegalArgumentException("Please make sure atleast one division is provided");
        }
        return divisions;
    }

    private List<Player> loadFreeAgentJSON(JSONArray freeAgents) throws IllegalArgumentException {

        ArrayList<Player> freeAgentList = new ArrayList<>();


        for (Object freeAgentObjectFromJSONArray : freeAgents) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentObjectFromJSONArray;
            if (notValidKeyInObject(freeAgentJsonObject, PLAYER_NAME)) {
                throw new IllegalArgumentException("Please make sure playerName is provided in JSON");
            }
            String playerName = (String) freeAgentJsonObject.get(PLAYER_NAME);
            if (notValidString(playerName)) {
                throw new IllegalArgumentException("Please make sure player name is valid in Free Agent");
            }

            Player.Position position = validatePosition(freeAgentJsonObject);

            int age = getPlayerAge(freeAgentJsonObject);

            int skating = getPlayerSkating(freeAgentJsonObject);

            int shooting = getPlayerShooting(freeAgentJsonObject);

            int checking = getPlayerChecking(freeAgentJsonObject);

            int saving = getPlayerSaving(freeAgentJsonObject);

            Player player = setFreePlayerVariables(playerName, position, age, skating, shooting, checking, saving);

            if (player.validName()) {
                freeAgentList.add(player);
            } else {
                throw new IllegalArgumentException("Free Agent Position is not valid. Please Correct it. Exiting the app!");
            }

        }
        return freeAgentList;
    }

    private Player setFreePlayerVariables(String playerName, Player.Position position, int age, int skating, int shooting, int checking, int saving) {
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
        player.setInjured(false);
        player.setIsFreeAgent(true);
        return player;
    }

    private List<Manager> loadManagerJSON(JSONArray managers) {
        ArrayList<Manager> managerList = new ArrayList<>();
        int managerSize = managers.size();

        for (int i = 0; i < managerSize; i++) {
            String name = (String) managers.get(i);
            ManagerConcrete managerConcrete = new ManagerConcrete();
            Manager manager = managerConcrete.newManagerConcrete();
            manager.setName(name);
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

    private Aging loadAgingJson(JSONObject agingJSONObject) {
        if (notValidKeyInObject(agingJSONObject, AVERAGE_RETIREMENT_AGE)) {
            throw new IllegalArgumentException("Please make sure averageRetirementAge is provided in JSON");
        }
        int averageRetirementAge = (int) (long) agingJSONObject.get(AVERAGE_RETIREMENT_AGE);

        if (notValidKeyInObject(agingJSONObject, MAXIMUM_AGE)) {
            throw new IllegalArgumentException("Please make sure maximumAge is provided in JSON");
        }
        int maximumAge = (int) (long) agingJSONObject.get(MAXIMUM_AGE);
        AgingConcrete agingConcrete = new AgingConcrete();
        Aging aging = agingConcrete.newAging();
        aging.setAverageRetirementAge(averageRetirementAge);
        aging.setMaximumAge(maximumAge);
        return aging;
    }

    private GameResolver loadGameResolverJson(JSONObject gameResolverJSONObject) {
        if (notValidKeyInObject(gameResolverJSONObject, RANDOM_WIN_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomWinChance is provided in JSON");
        }
        double randomWinChance = (Double) gameResolverJSONObject.get(RANDOM_WIN_CHANCE);
        GameResolverConcrete gameResolverConcrete = new GameResolverConcrete();
        GameResolver gameResolver = gameResolverConcrete.newGameResolver();
        gameResolver.setRandomWinChance(randomWinChance);
        return gameResolver;
    }

    private Injury loadInjuryJson(JSONObject injuriesJSONObject) {
        if (notValidKeyInObject(injuriesJSONObject, RANDOM_INJURY_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomInjuryChance is provided in JSON");
        }
        double randomInjuryChance = (Double) injuriesJSONObject.get(RANDOM_INJURY_CHANCE);

        if (notValidKeyInObject(injuriesJSONObject, INJURY_DAYS_LOW)) {
            throw new IllegalArgumentException("Please make sure injuryDaysLow is provided in JSON");
        }
        int injuryDaysLow = (int) (long) injuriesJSONObject.get(INJURY_DAYS_LOW);

        if (notValidKeyInObject(injuriesJSONObject, INJURY_DAYS_HIGH)) {
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
        if (notValidKeyInObject(trainingJSONObject, DAYS_UNTIL_STAT_INCREASE_CHECK)) {
            throw new IllegalArgumentException("Please make sure daysUntilStatIncreaseCheck is provided in JSON");
        }
        int daysUntil = (int) (long) trainingJSONObject.get(DAYS_UNTIL_STAT_INCREASE_CHECK);
        TrainingConcrete trainingConcrete = new TrainingConcrete();
        Training training = trainingConcrete.newTraining();
        training.setDaysUntilStatIncreaseCheck(daysUntil);
        return training;
    }

    private Trading loadTradingJson(JSONObject tradingJSONObject) {
        if (notValidKeyInObject(tradingJSONObject, LOSS_POINT)) {
            throw new IllegalArgumentException("Please make sure lossPoint is provided in JSON");
        }
        int lossPoint = (int) (long) tradingJSONObject.get(LOSS_POINT);

        if (notValidKeyInObject(tradingJSONObject, RANDOM_TRADE_OFFER_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomTradeOfferChance is provided in JSON");
        }
        double randomTradeOfferChance = (Double) tradingJSONObject.get(RANDOM_TRADE_OFFER_CHANCE);

        if (notValidKeyInObject(tradingJSONObject, MAX_PLAYERS_PER_TRADE)) {
            throw new IllegalArgumentException("Please make sure maxPlayersPerTrade is provided in JSON");
        }
        int maxPlayersPerTrade = (int) (long) tradingJSONObject.get(MAX_PLAYERS_PER_TRADE);

        if (notValidKeyInObject(tradingJSONObject, RANDOM_ACCEPTANCE_CHANCE)) {
            throw new IllegalArgumentException("Please make sure randomAcceptanceChance is provided in JSON");
        }
        double randomAcceptanceChance = (Double) tradingJSONObject.get(RANDOM_ACCEPTANCE_CHANCE);

        TradingConcrete tradingConcrete = new TradingConcrete();
        Trading trading = tradingConcrete.newTrading();
        trading.setLossPoint(lossPoint);
        trading.setRandomTradeOfferChance(randomTradeOfferChance);
        trading.setMaxPlayersPerTrade(maxPlayersPerTrade);
        trading.setRandomAcceptanceChance(randomAcceptanceChance);
        return trading;
    }

    private boolean notBoolean(Boolean bool) {
        return bool == null;
    }

    private boolean notValidString(String name) {
        if (name == null || name.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean notValidArray(JSONArray array) {
        if (array == null || array.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean notValidKeyInObject(JSONObject object, String key) {
        if (object.containsKey(key) && object.get(key) != null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isDivisionExists(ArrayList<Division> list, String name) {
        boolean isExist = false;
        for (Division division : list) {
            if (division.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private boolean isConferenceExists(ArrayList<Conference> list, String name) {


        boolean isExist = false;
        for (Conference conference : list) {
            if (conference.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private boolean isTeamExists(ArrayList<Team> list, String name) {
        boolean isExist = false;
        for (Team team : list) {
            if (team.getName().equals(name)) {
                isExist = true;
            }
        }
        return isExist;
    }
}

