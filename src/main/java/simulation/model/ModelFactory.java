package simulation.model;

import simulation.dao.*;

public class ModelFactory implements IModelFactory{

    private static IModelFactory modelFactory;

    private ModelFactory(){}

    public static IModelFactory getInstance(){
        if(null == modelFactory){
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

    public IConference newConferenceWithId(int id){
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

    public IDivision newDivisionWithId(int id){
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

    public ILeague newLeague() {
        return new League();
    }

    public ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception {
        return new League(leagueName, userId, leagueFactory);
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

}
