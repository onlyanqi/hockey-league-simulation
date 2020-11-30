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

    ISimulate newSimulate();

    ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception;

    ILeague newLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception;

    IManager newManagerConcrete();

    INHLEvents newNHLEvents();

    INHLEvents newNHLEventsByYear(int year);

    IPlayer newPlayer();

    IPlayer newPlayerFromSerialization(simulation.serializers.ModelsForDeserialization.model.Player player);

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

    IShift newShift();

    IGameSimulation newGameSimulationFromTeams(ITeam team1, ITeam team2);

    ICoach newCoachFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Coach coach);

    IManager newManagerFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Manager manager);

    ITeam newTeamFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Team team);

    IDivision newDivisionFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Division division);

    IGame createGameFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Game game);

    ITeamScore createTeamScoreFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TeamScore teamScore);

    ITeamStanding createTeamStandingFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TeamStanding teamStanding);

    IConference creatConferenceFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Conference conference);

    IFreeAgent creatFreeAgentFromDeserialization(simulation.serializers.ModelsForDeserialization.model.FreeAgent freeAgent);

    IGamePlayConfig creatGamePlayConfigFromDeserialization(simulation.serializers.ModelsForDeserialization.model.GamePlayConfig gamePlayConfig);

    IGameSchedule createGameScheduleFromDeserialization(simulation.serializers.ModelsForDeserialization.model.GameSchedule gameSchedule);

    INHLEvents createNHLEventsFromDeserialization(simulation.serializers.ModelsForDeserialization.model.NHLEvents nhlEvents);

    TeamStat createTeamStatFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TeamStat teamStat);

    ITradeOffer createTradeOfferFromDeserialization(simulation.serializers.ModelsForDeserialization.model.TradeOffer tradeOffer);

    ITrophy createTrophyFromDeserialization(simulation.serializers.ModelsForDeserialization.model.Trophy trophy);


    IManager createManagerFromManager(IManager manager);

    IPlayer createPlayerFromPlayer(IPlayer player);
}
