package simulation.state;

import db.data.*;
import presentation.ConsoleOutput;
import simulation.factory.*;
import simulation.model.*;
import simulation.serializers.LeagueDataSerializerDeSerializer;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersistState implements ISimulateState {

    private final IHockeyContext hockeyContext;
    private final ILeague league;
    private final INHLEvents nhlEvents;

    public PersistState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.nhlEvents = league.getNHLRegularSeasonEvents();
    }

    @Override
    public ISimulateState action() throws Exception {
        ConsoleOutput.getInstance().printMsgToConsole("Saving to DB... Please wait");
        saveToPersistence();
        return exit();
    }

    private void saveToPersistence() {
        LeagueDataSerializerDeSerializer serializerDeSerializer = new LeagueDataSerializerDeSerializer();
        serializerDeSerializer.serialize(league);
    }
/*        if(todayIsStartOfSeason()){
            persistLeagueToDB();

        }
        if(stanleyCupWinnerDetermined()){

            List<TeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
            if (teamScoreList.get(0).getNumberOfWins() > teamScoreList.get(1).getNumberOfWins()) {
                ConsoleOutput.getInstance().printMsgToConsole(teamScoreList.get(0).getTeamName() + " won the stanley cup!");
            } else {
                ConsoleOutput.getInstance().printMsgToConsole(teamScoreList.get(1).getTeamName() + " won the stanley cup!");
            }

            updateDataBaseWithSimulatedDate();
            persistRetiredPlayers();
        } */

//    private void persistRetiredPlayers() {
//
//        try {
//            addRetiredPlayerList(league.getRetiredPlayerList());
//        } catch (SQLException sqlException) {
//            System.out.println("Unable to save the retired players! Please try again" + sqlException.getMessage());
//            System.exit(1);
//            sqlException.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void updateDataBaseWithSimulatedDate() {
//        if (validation.isNotNull(league)) {
//            try {
//                updateTeam();
//                updatePlayers();
//                updateGames();
//                if (league.getCurrentDate().equals(nhlEvents.getEndOfRegularSeason().plusDays(1))) {
//                    addPlayOffTeamStanding(league.getId(), league.getActiveTeamStanding().getTeamsScoreList());
//                } else if (league.getCurrentDate().isBefore(nhlEvents.getEndOfRegularSeason().plusDays(1))) {
//                    updateRegularSeasonStanding(league.getActiveTeamStanding().getTeamsScoreList());
//                } else if (league.getCurrentDate().isAfter(nhlEvents.getPlayOffStartDate().minusDays(1))) {
//                    updatePlayOffStanding(league.getActiveTeamStanding().getTeamsScoreList());
//                }
//
//                List<TradeOffer> tradeOfferList = league.getTradeOfferList();
//                if (validation.isListNotEmpty(tradeOfferList)) {
//                    addTradeOfferList(tradeOfferList);
//                }
//                System.out.println("Trade Offer done....");
//
//            } catch (SQLException sqlException) {
//                ConsoleOutput.getInstance().printMsgToConsole("Unable to save the league! Please try again" + sqlException.getMessage());
//                System.exit(1);
//                sqlException.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.exit(1);
//            } finally {
//                clearTradeOffers();
//            }
//        }
//    }
//
//    private void updateGames() throws Exception {
//        if (league.getCurrentDate().isBefore(nhlEvents.getEndOfRegularSeason().plusDays(1))) {
//            updateGameOnCurrentDay();
//        } else {
//            addNewGames();
//            updateGameOnCurrentDay();
//        }
//    }
//
//    private void updateGameOnCurrentDay() throws Exception {
//        List<Game> gameList = league.getGames().getGamesOnDate(league.getCurrentDate());
//
//        GameConcrete gameConcrete = new GameConcrete();
//        IGameFactory gameFactory = gameConcrete.newAddGamesFactory();
//        for (Game game : gameList) {
//            gameFactory.updateGameById(game);
//        }
//
//    }
//
//    private void clearTradeOffers() {
//
//        List<TradeOffer> tradeOffers = league.getTradeOfferList();
//        if (validation.isListNotEmpty(tradeOffers)) {
//            tradeOffers.clear();
//        }
//    }
//
//    private void addNewGames() throws Exception {
//        List<Game> games = getNewGames();
//        addGameList(league.getId(), games);
//    }
//
//    private List<Game> getNewGames() throws Exception {
//
//        GameConcrete gameConcrete = new GameConcrete();
//        IGameFactory gameFactory = gameConcrete.newAddGamesFactory();
//        List<Game> gameListFromPersistence = gameFactory.loadGamesByLeagueId(league.getId());
//        List<Game> gamesFromModel = league.getGames().getGameList();
//
//        Set<Integer> ids = gameListFromPersistence.stream()
//                .map(Game::getId)
//                .collect(Collectors.toSet());
//        List<Game> gameList = gamesFromModel.stream()
//                .filter(game -> !ids.contains(game.getId()))
//                .collect(Collectors.toList());
//
//        return gameList;
//    }
//
//    private void persistLeagueToDB() {
//        if (validation.isNotNull(league)) {
//            league.setCreatedBy(hockeyContext.getUser().getId());
//            Season season = new Season();
//            season.setName(String.valueOf(LocalDate.now().getYear()));
//
//            try {
//                LeagueConcrete leagueConcrete = new LeagueConcrete();
//                ILeagueFactory addLeagueFactory = leagueConcrete.newAddLeagueFactory();
//                league.addLeague(addLeagueFactory);
//                int leagueId = league.getId();
//
//                SeasonConcrete seasonConcrete = new SeasonConcrete();
//                ISeasonFactory addSeasonDao = seasonConcrete.newAddSeasonFactory();
//                season.addSeason(addSeasonDao);
//                int seasonId = season.getId();
//
//                addEvents(league.getId(), league.getNHLRegularSeasonEvents());
//                addGameList(league.getId(), league.getGames().getGameList());
//                addTeamStanding(league.getId(), league.getActiveTeamStanding().getTeamsScoreList());
//                addCoaches(league.getCoachList());
//                addManagers(league.getManagerList());
//                if (leagueId > 0 && seasonId > 0) {
//                    if (validation.isNotNull(league.getFreeAgent())) {
//                        int freeAgentId = addFreeAgent(leagueId, seasonId);
//                        addPlayerList(0, freeAgentId, league.getFreeAgent().getPlayerList());
//                    }
//                    if (validation.isNotNull(league.getConferenceList()) && league.getConferenceList().size() > 0) {
//                        ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
//                        IConferenceFactory addConferenceDao = conferenceConcrete.newAddConferenceFactory();
//                        for (Conference conference : league.getConferenceList()) {
//                            conference.setLeagueId(leagueId);
//                            conference.addConference(addConferenceDao);
//                            int conferenceId = conference.getId();
//                            for (Division division : conference.getDivisionList()) {
//                                DivisionConcrete divisionConcrete = new DivisionConcrete();
//                                IDivisionFactory addDivisionDao = divisionConcrete.newAddDivisionFactory();
//
//                                division.setConferenceId(conferenceId);
//                                division.addDivision(addDivisionDao);
//                                int divisionId = division.getId();
//                                for (Team team : division.getTeamList()) {
//                                    TeamConcrete teamConcrete = new TeamConcrete();
//                                    ITeamFactory addTeamDao = teamConcrete.newTeamFactory();
//
//                                    team.setDivisionId(divisionId);
//                                    team.addTeam(addTeamDao);
//                                    int teamId = team.getId();
//
//                                    CoachConcrete coachConcrete = new CoachConcrete();
//                                    ICoachFactory addCoachFactory = coachConcrete.newCoachFactory();
//                                    Coach coach = team.getCoach();
//                                    coach.setLeagueId(leagueId);
//                                    coach.setTeamId(teamId);
//                                    addCoachFactory.addCoach(coach);
//
//                                    ManagerConcrete managerConcrete = new ManagerConcrete();
//                                    IManagerFactory addManagerFactory = managerConcrete.newManagerFactory();
//                                    Manager manager = team.getManager();
//                                    manager.setLeagueId(leagueId);
//                                    manager.setTeamId(teamId);
//                                    addManagerFactory.addManager(manager);
//
//                                    addPlayerList(teamId, 0, team.getPlayerList());
//                                }
//                            }
//                        }
//                    }
//                    Trading trading = league.getGamePlayConfig().getTrading();
//                    trading.setLeagueId(leagueId);
//                    if (validation.isNotNull(trading)) {
//                        addTrading(trading);
//                    }
//
//                    List<TradeOffer> tradeOfferList = league.getTradeOfferList();
//                    if (validation.isListNotEmpty(tradeOfferList)) {
//                        addTradeOfferList(tradeOfferList);
//                    }
//                }
//
//            } catch (SQLException sqlException) {
//                ConsoleOutput.getInstance().printMsgToConsole("Unable to save the league! Please try again" + sqlException.getMessage());
//                System.exit(1);
//                sqlException.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                clearTradeOffers();
//            }
//        }
//    }
//
//    private void addManagers(List<Manager> managerList) throws Exception {
//        ManagerConcrete managerConcrete = new ManagerConcrete();
//        IManagerFactory managerFactory = managerConcrete.newManagerFactory();
//        for (Manager manager : managerList) {
//            manager.setLeagueId(league.getId());
//            managerFactory.addManager(manager);
//        }
//    }
//
//    private void addCoaches(List<Coach> coachList) throws Exception {
//        CoachConcrete coachConcrete = new CoachConcrete();
//        ICoachFactory coachFactory = coachConcrete.newCoachFactory();
//        for (Coach coach : coachList) {
//            coach.setLeagueId(league.getId());
//            coachFactory.addCoach(coach);
//        }
//    }
//
//    public void addTrading(Trading trading) throws Exception {
//        TradingConcrete tradingConcrete = new TradingConcrete();
//        ITradingFactory tradingFactory = tradingConcrete.newTradingFactory();
//        tradingFactory.addTradingDetails(trading);
//    }
//
//    public void addTradeOfferList(List<TradeOffer> tradeOfferList) throws Exception {
//        TradeOfferConcrete tradeOfferConcrete = new TradeOfferConcrete();
//        ITradeOfferFactory tradeOfferFactory = tradeOfferConcrete.newTradeOfferFactory();
//        for (TradeOffer tradeOffer : tradeOfferList) {
//            tradeOffer.setLeagueId(league.getId());
//            tradeOffer.setTradingId(league.getGamePlayConfig().getTrading().getId());
//            tradeOfferFactory.addTradeOfferDetails(tradeOffer);
//        }
//    }
//
//    private void addTeamStanding(int id, List<TeamScore> teamScoreList) throws Exception {
//        TeamScoreConcrete teamScoreConcrete = new TeamScoreConcrete();
//        ITeamScoreFactory addTeamScoreFactory = teamScoreConcrete.newAddTeamScoreFactory();
//        for (TeamScore teamScore : teamScoreList) {
//            addTeamScoreFactory.addTeamScore(id, 1, teamScore);
//        }
//    }
//
//    private void addPlayOffTeamStanding(int id, List<TeamScore> teamsScoreList) throws Exception {
//        TeamScoreConcrete teamScoreConcrete = new TeamScoreConcrete();
//        ITeamScoreFactory addTeamScoreFactory = teamScoreConcrete.newAddTeamScoreFactory();
//        for (TeamScore teamScore : teamsScoreList) {
//            addTeamScoreFactory.addTeamScore(id, 0, teamScore);
//        }
//    }
//
//    private void updateRegularSeasonStanding(List<TeamScore> teamsScoreList) throws Exception {
//        TeamScoreConcrete teamScoreConcrete = new TeamScoreConcrete();
//        ITeamScoreFactory addTeamScoreFactory = teamScoreConcrete.newAddTeamScoreFactory();
//        for (TeamScore teamScore : teamsScoreList) {
//            addTeamScoreFactory.updateTeamScoreById(teamScore);
//        }
//    }
//
//    private void updatePlayOffStanding(List<TeamScore> teamsScoreList) throws Exception {
//        TeamScoreConcrete teamScoreConcrete = new TeamScoreConcrete();
//        ITeamScoreFactory addTeamScoreFactory = teamScoreConcrete.newAddTeamScoreFactory();
//        for (TeamScore teamScore : teamsScoreList) {
//            addTeamScoreFactory.updateTeamScoreById(teamScore);
//        }
//    }
//
//    private void updatePlayers() throws Exception {
//        PlayerConcrete playerConcrete = new PlayerConcrete();
//        IPlayerFactory addPlayerFactory = playerConcrete.newPlayerFactory();
//        for (Conference conference : league.getConferenceList()) {
//            for (Division division : conference.getDivisionList()) {
//                for (Team team : division.getTeamList()) {
//                    for (Player player : team.getPlayerList()) {
//                        addPlayerFactory.updatePlayerById(player.getId(), player);
//                    }
//                }
//            }
//        }
//    }
//
//    private void updateTeam() throws Exception {
//        TeamConcrete teamConcrete = new TeamConcrete();
//        ITeamFactory teamFactory = teamConcrete.newTeamFactory();
//        for (Conference conference : league.getConferenceList()) {
//            for (Division division : conference.getDivisionList()) {
//                for (Team team : division.getTeamList()) {
//                    team.setLossPoint(0);
//                    team.setTradeOfferCountOfSeason(0);
//                    teamFactory.updateTeamById(team);
//                }
//            }
//        }
//    }
//
//
//    private void addEvents(int leagueId, NHLEvents nhlEvents) throws Exception {
//        EventConcrete eventConcrete = new EventConcrete();
//        IEventFactory addEventFactory = eventConcrete.newAddEventsFactory();
//        addEventFactory.addEvent(leagueId, nhlEvents);
//    }
//
//    private void addGameList(int leagueId, List<Game> gameList) throws Exception {
//        if (validation.isNotNull(gameList) && gameList.size() > 0) {
//            GameConcrete gameConcrete = new GameConcrete();
//            IGameFactory addGamesFactory = gameConcrete.newAddGamesFactory();
//            for (Game game : gameList) {
//                addGamesFactory.addGame(leagueId, game);
//            }
//        }
//    }
//
//    private int addFreeAgent(int leagueId, int seasonId) throws Exception {
//        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();
//        IFreeAgentFactory freeAgentDao = freeAgentConcrete.newAddFreeAgentFactory();
//        FreeAgent freeAgent = league.getFreeAgent();
//        freeAgent.setSeasonId(seasonId);
//        freeAgent.setLeagueId(leagueId);
//        freeAgent.addFreeAgent(freeAgentDao);
//        return freeAgent.getId();
//    }
//
//    private void addPlayerList(int teamId, int freeAgentId, List<Player> playerList) throws Exception {
//        if (validation.isNotNull(playerList) && playerList.size() > 0) {
//            PlayerConcrete playerConcrete = new PlayerConcrete();
//            IPlayerFactory addPlayerDao = playerConcrete.newPlayerFactory();
//            for (Player player : playerList) {
//                player.setTeamId(teamId);
//                player.setFreeAgentId(freeAgentId);
//                if(player.getPosition() == null){
//                    player.setPosition(Player.Position.forward);
//                }
//                addPlayerDao.addPlayer(player);
//            }
//        }
//    }
//
//    private void addRetiredPlayerList(List<Player> playerList) throws Exception {
//        if (validation.isNotNull(playerList) && playerList.size() > 0) {
//            PlayerConcrete playerConcrete = new PlayerConcrete();
//            IPlayerFactory addPlayerDao = playerConcrete.newPlayerFactory();
//            for (Player player : playerList) {
//                addPlayerDao.addRetiredPlayer(league.getId(), player);
//                System.out.println("One Retired Player Added to DB...");
//            }
//        }
//    }
//
//    private Boolean todayIsStartOfSeason() {
//        if (league.getCurrentDate().equals(LocalDate.of(LocalDate.now().getYear(), 10, 01))) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
    private ISimulateState exit() {
        if (stanleyCupWinnerDetermined()) {
            return null;
        } else {
            return new AdvanceTimeState(hockeyContext);
        }
    }

    public Boolean stanleyCupWinnerDetermined() {
        IGameSchedule games = league.getGames();
        ITeamStanding teamStanding = league.getActiveTeamStanding();
        if (nhlEvents.checkRegularSeasonPassed(league.getCurrentDate())
                && games.doGamesDoesNotExistAfterDate(league.getCurrentDate())
                && teamStanding.getTeamsScoreList().size() == 2) {
            return true;
        }
        return false;
    }
}
