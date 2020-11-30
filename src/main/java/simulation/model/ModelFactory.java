package simulation.model;

import simulation.dao.*;

public class ModelFactory implements IModelFactory {

    private static IModelFactory modelFactory;

    private ModelFactory() {
    }

    public static IModelFactory getInstance() {
        if (null == modelFactory) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    @Override
    public IGameSchedule createGameSchedule() {
        return new GameSchedule();
    }

    public IAging createAging() {
        return new Aging();
    }

    @Override
    public ICoach createCoach() {
        return new Coach();
    }

    @Override
    public ICoach createCoachWithCoach(ICoach coach) {
        return new Coach(coach);
    }

    public IConference createConference() {
        return new Conference();
    }

    public IConference createConferenceWithId(int id) {
        return new Conference(id);
    }

    public IConference createConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception {
        return new Conference(id, conferenceDao);
    }

    public IDivision createDivision() {
        return new Division();
    }

    public IDivision createDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception {
        return new Division(id, divisionDao);
    }

    public IDivision createDivisionWithId(int id) {
        return new Division(id);
    }

    public INHLEvents createEvents() {
        return new NHLEvents();
    }

    public IFreeAgent createFreeAgent() {
        return new FreeAgent();
    }

    @Override
    public IGame createGame() {
        return new Game();
    }

    public IGamePlayConfig createGamePlayConfig() {
        return new GamePlayConfig();
    }

    public IInjury createInjury() {
        return new Injury();
    }

    public ISimulate createSimulate() {
        return new Simulate();
    }

    public ILeague createLeague() {
        return new League();
    }

    public ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception {
        return new League(leagueName, userId, leagueFactory);
    }

    @Override
    public ILeague createLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception {
        return new League(id, leagueDao);
    }

    public ILeague createLeagueWithId(int id) {
        return new League(id);
    }

    public ISeason createSeason() {
        return new Season();
    }

    public ITeamScore createTeamScore() {
        return new TeamScore();
    }


    public IPlayer createPlayer() {
        return new Player();
    }

    public IPlayer createPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception {
        return new Player(id, playerDao);
    }

    public IPlayer createPlayerWithId(int id) {
        return new Player(id);
    }

    @Override
    public ITeamStanding createTeamStanding() {
        return new TeamStanding();
    }

    @Override
    public INHLEvents createNHLEvents() {
        return new NHLEvents();
    }

    @Override
    public INHLEvents createNHLEventsByYear(int year) {
        return new NHLEvents(year);
    }

    public ITradeOffer createTradeOffer() {
        return new TradeOffer();
    }

    public ITradeOffer createTradeOfferWithIdDao(int id, ITradeOfferDao tradeOfferDao) throws Exception {
        return new TradeOffer(id, tradeOfferDao);
    }

    public ITeam createTeam() {
        return new Team();
    }

    public ITeam createTeamByName(String name, ITeamDao loadTeamFactory) throws Exception {
        return new Team(name, loadTeamFactory);
    }

    public ITeam createTeamWithIdDao(int id, ITeamDao teamDao) throws Exception {
        return new Team(id, teamDao);
    }

    public ITrading createTrading() {
        return new Trading();
    }

    public ITrading createTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception {
        return new Trading(id, tradingDao);
    }

    public IManager newManagerConcrete() {
        return new Manager();
    }

    public IUser createUser() {
        return new User();
    }

    public IUser createUserByName(String name, IUserDao loadUserFactory) throws Exception {
        return new User(name, loadUserFactory);
    }

    public IUser createUserWithIdDao(int id, IUserDao userDao) throws Exception {
        return new User(id, userDao);
    }

    public ITraining newTraining() {
        return new Training();
    }

    @Override
    public ITrophy newTrophy() {
        return new Trophy();
    }

    public IFreeAgent createFreeAgentWithId(int id) {
        return new FreeAgent(id);
    }

    public IFreeAgent createFreeAgentWithIdDao(int id, IFreeAgentDao freeAgentDao) throws Exception {
        return new FreeAgent(id, freeAgentDao);
    }

    public IShift newShift() {
        return new Shift();
    }

    @Override
    public IGameSimulation newGameSimulationFromTeams(ITeam team1, ITeam team2) {
        return new GameSimulation(team1, team2);
    }

    public ISeason createSeasonWithId(int id) {
        return new Season(id);
    }

    public ISeason createSeasonWithIdDao(int id, ISeasonDao seasonDao) throws Exception {
        return new Season(id, seasonDao);
    }

    public ITeam createTeamWithId(int id) {
        return new Team(id);
    }

    public IUser createUserWithId(int id) {
        return new User(id);
    }
}
