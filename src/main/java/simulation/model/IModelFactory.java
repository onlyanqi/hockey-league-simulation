package simulation.model;

import simulation.dao.*;

public interface IModelFactory {

    IAging createAging();

    ICoach createCoach();

    ICoach createCoachWithCoach(ICoach coach);

    IConference createConference();

    IConference createConferenceWithId(int id);

    IConference createConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception;

    IDivision createDivision();

    IDivision createDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception;

    IDivision createDivisionWithId(int id);

    INHLEvents createEvents();

    IFreeAgent createFreeAgent();

    IGame createGame();

    IGamePlayConfig createGamePlayConfig();

    IGameSchedule createGameSchedule();

    IInjury createInjury();

    ILeague createLeague();

    ISimulate createSimulate();

    ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception;

    ILeague createLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception;

    ILeague createLeagueWithId(int id);

    IManager newManagerConcrete();

    INHLEvents createNHLEvents();

    INHLEvents createNHLEventsByYear(int year);

    IPlayer createPlayer();

    IPlayer createPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception;

    ISeason createSeason();

    ITeam createTeam();

    ITeam createTeamByName(String name, ITeamDao loadTeamFactory) throws Exception;

    ITeam createTeamWithIdDao(int id, ITeamDao teamDao) throws Exception;

    ITeamScore createTeamScore();

    ITeamStanding createTeamStanding();

    ITradeOffer createTradeOffer();

    ITradeOffer createTradeOfferWithIdDao(int id, ITradeOfferDao tradeOfferDao) throws Exception;

    ITrading createTrading();

    ITrading createTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception;

    ITraining newTraining();

    IUser createUser();

    IUser createUserByName(String name, IUserDao loadUserFactory) throws Exception;

    IUser createUserWithIdDao(int id, IUserDao userDao) throws Exception;

    IFreeAgent createFreeAgentWithId(int id);

    ITrophy newTrophy();

    IFreeAgent createFreeAgentWithIdDao(int id, IFreeAgentDao freeAgentDao) throws Exception;

    IShift newShift();

    IGameSimulation newGameSimulationFromTeams(ITeam team1, ITeam team2);

    IPlayer createPlayerWithId(int id);

    ISeason createSeasonWithId(int id);

    ISeason createSeasonWithIdDao(int id, ISeasonDao seasonDao) throws Exception;

    ITeam createTeamWithId(int id);

    IUser createUserWithId(int id);
}
