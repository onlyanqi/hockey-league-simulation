package simulation.model;

import simulation.dao.*;
import simulation.serializers.ModelsForDeserialization.model.LeagueDeserializationModel;

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
    public IGameSchedule newGameSchedule() {
        return new GameSchedule();
    }

    public IAging newAging() {
        return new Aging();
    }

    @Override
    public ICoach newCoach() {
        return new Coach();
    }

    @Override
    public ICoach newCoachWithCoach(ICoach coach) {
        return new Coach(coach);
    }

    public IConference newConference() {
        return new Conference();
    }

    public IConference newConferenceWithId(int id) {
        return new Conference(id);
    }

    public IConference newConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception {
        return new Conference(id, conferenceDao);
    }

    public IDivision newDivision() {
        return new Division();
    }

    public IDivision newDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception {
        return new Division(id, divisionDao);
    }

    public IDivision newDivisionWithId(int id) {
        return new Division(id);
    }

    public INHLEvents newEvents() {
        return new NHLEvents();
    }

    public IFreeAgent newFreeAgent() {
        return new FreeAgent();
    }

    @Override
    public IGame newGame() {
        return new Game();
    }

    public IGamePlayConfig newGamePlayConfig() {
        return new GamePlayConfig();
    }

    public IInjury newInjury() {
        return new Injury();
    }

    public ISimulate newSimulate() {
        return new Simulate();
    }

    public ILeague newLeague() {
        return new League();
    }

    public ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception {
        return new League(leagueName, userId, leagueFactory);
    }

    public ILeague createLeagueFromDeserializationObject(LeagueDeserializationModel leagueDeserializationModel) {
        return new League(leagueDeserializationModel);
    }

    @Override
    public ILeague newLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception {
        return new League(id, leagueDao);
    }

    public ISeason newSeason() {
        return new Season();
    }

    public ITeamScore newTeamScore() {
        return new TeamScore();
    }


    public IPlayer newPlayer() {
        return new Player();
    }

    @Override
    public IPlayer newPlayerFromSerialization(simulation.serializers.ModelsForDeserialization.model.Player player) {
        return new Player(player);
    }

    public IPlayer newPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception {
        return new Player(id, playerDao);
    }

    @Override
    public ITeamStanding newTeamStanding() {
        return new TeamStanding();
    }

    @Override
    public INHLEvents newNHLEvents() {
        return new NHLEvents();
    }

    @Override
    public INHLEvents newNHLEventsByYear(int year) {
        return new NHLEvents(year);
    }

    public ITradeOffer newTradeOffer() {
        return new TradeOffer();
    }

    public ITradeOffer newTradeOfferWithIdDao(int id, ITradeOfferDao tradeOfferDao) throws Exception {
        return new TradeOffer(id, tradeOfferDao);
    }

    public ITeam newTeam() {
        return new Team();
    }

    public ITeam newTeamByName(String name, ITeamDao loadTeamFactory) throws Exception {
        return new Team(name, loadTeamFactory);
    }

    public ITeam newTeamWithIdDao(int id, ITeamDao teamDao) throws Exception {
        return new Team(id, teamDao);
    }

    public ITrading newTrading() {
        return new Trading();
    }

    public ITrading newTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception {
        return new Trading(id, tradingDao);
    }

    public IManager newManagerConcrete() {
        return new Manager();
    }

    public IUser newUser() {
        return new User();
    }

    public IUser newUserByName(String name, IUserDao loadUserFactory) throws Exception {
        return new User(name, loadUserFactory);
    }

    public IUser newUserWithIdDao(int id, IUserDao userDao) throws Exception {
        return new User(id, userDao);
    }

    public ITraining newTraining() {
        return new Training();
    }

    @Override
    public ITrophy newTrophy() {
        return new Trophy();
    }

    public IFreeAgent newFreeAgentWithId(int id) {
        return new FreeAgent(id);
    }

    public IFreeAgent newFreeAgentWithIdDao(int id, IFreeAgentDao freeAgentDao) throws Exception {
        return new FreeAgent(id, freeAgentDao);
    }

    public IShift newShift() {
        return new Shift();
    }

    @Override
    public IGameSimulation newGameSimulationFromTeams(ITeam team1, ITeam team2) {
        return new GameSimulation(team1, team2);
    }

    @Override
    public ICoach newCoachFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Coach coach) {
        return new Coach(coach);
    }


    @Override
    public IManager newManagerFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Manager manager) {
        return new Manager(manager);
    }

    @Override
    public ITeam newTeamFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Team team) {
        return new Team(team);
    }

    @Override
    public IDivision newDivisionFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Division division) {
        return new Division(division);
    }

    @Override
    public IGame createGameFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Game game) {
        return new Game(game);
    }

    @Override
    public ITeamScore createTeamScoreFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TeamScore teamScore) {
        return new TeamScore(teamScore);
    }

    @Override
    public ITeamStanding createTeamStandingFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TeamStanding teamStanding) {
        return new TeamStanding(teamStanding);
    }

    @Override
    public IConference creatConferenceFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Conference conference) {
        return new Conference(conference);
    }

    @Override
    public IFreeAgent creatFreeAgentFromDeserialization(simulation.serializers.ModelsForDeserialization.model.FreeAgent freeAgent) {
        return new FreeAgent(freeAgent);
    }

    @Override
    public IGamePlayConfig creatGamePlayConfigFromDeserialization(simulation.serializers.ModelsForDeserialization.model.GamePlayConfig gamePlayConfig) {
        return new GamePlayConfig(gamePlayConfig);
    }

    @Override
    public IGameSchedule createGameScheduleFromDeserialization(simulation.serializers.ModelsForDeserialization.model.GameSchedule gameSchedule) {
        return new GameSchedule(gameSchedule);
    }

    @Override
    public INHLEvents createNHLEventsFromDeserialization(simulation.serializers.ModelsForDeserialization.model.NHLEvents nhlEvents) {
        return new NHLEvents(nhlEvents);
    }

    @Override
    public TeamStat createTeamStatFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TeamStat teamStat) {
        return new TeamStat(teamStat);
    }

    @Override
    public ITradeOffer createTradeOfferFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TradeOffer tradeOffer) {
        return new TradeOffer(tradeOffer);
    }

    @Override
    public ITrophy createTrophyFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Trophy trophy) {
        return new Trophy(trophy);
    }

    @Override
    public IManager createManagerFromManager(IManager manager) {
        return new Manager(manager);
    }

    @Override
    public IPlayer createPlayerFromPlayer(IPlayer player) {
        return new Player(player);
    }


}
