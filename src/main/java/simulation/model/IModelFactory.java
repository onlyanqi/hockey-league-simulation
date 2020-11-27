package simulation.model;

import simulation.dao.*;

public interface IModelFactory {

    IAging newAging();

    ICoach newCoach();

    ICoach newCoachWithCoach(ICoach coach);

    IConference newConference();

    IConference newConferenceWithId(int id);

    IConference newConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception;

    IDivision newDivision();

    IDivision newDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception;

    IDivision newDivisionWithId(int id);

    INHLEvents newEvents();

    IFreeAgent newFreeAgent();

    IGame newGame();

    IGamePlayConfig newGamePlayConfig();

    IGameSchedule newGameSchedule();

    IInjury newInjury();

    ILeague newLeague();

    ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception;

    ILeague newLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception;

    IManager newManagerConcrete();

    INHLEvents newNHLEvents();

    INHLEvents newNHLEventsByYear(int year);

    IPlayer newPlayer();

    IPlayer newPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception;

    ISeason newSeason();

    ITeam newTeam();

    ITeam newTeamByName(String name, ITeamDao loadTeamFactory) throws Exception;

    ITeam newTeamWithIdDao(int id, ITeamDao teamDao) throws Exception;

    ITeamScore newTeamScore();

    ITeamStanding newTeamStanding();

    ITradeOffer newTradeOffer();

    ITradeOffer newTradeOfferWithIdDao(int id, ITradeOfferDao tradeOfferDao) throws Exception;

    ITrading newTrading();

    ITrading newTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception;

    ITraining newTraining();

    IUser newUser();

    IUser newUserByName(String name, IUserDao loadUserFactory) throws Exception;

    IUser newUserWithIdDao(int id, IUserDao userDao) throws Exception;

    IFreeAgent newFreeAgentWithId(int id);

    ITrophy newTrophy();

    IFreeAgent newFreeAgentWithIdDao(int id, IFreeAgentDao freeAgentDao) throws Exception;
}
