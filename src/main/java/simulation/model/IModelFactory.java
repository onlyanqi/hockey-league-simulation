package simulation.model;

import persistance.dao.*;

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

    IShift createShift();

    IManager createManagerConcrete();

    ITraining createTraining();

    ITeam createTeamFromDeserialization(persistance.serializers.ModelsForDeserialization.model.Team team);

    IPlayer createPlayerFromSerialization(persistance.serializers.ModelsForDeserialization.model.Player player);

    ICoach createCoachFromDeserialization(persistance.serializers.ModelsForDeserialization.model.Coach coach);

    IManager createManagerFromDeserialization(persistance.serializers.ModelsForDeserialization.model.Manager manager);

    ITrophy createTrophyFromDeserialization(persistance.serializers.ModelsForDeserialization.model.Trophy trophy);

    ITrophy createTrophy();

    IDivision createDivisionFromDeserialization(persistance.serializers.ModelsForDeserialization.model.Division division);

    IGameSimulation createGameSimulationFromTeams(ITeam team1, ITeam team2);

    ITeamStanding createTeamStandingFromDeserialization(persistance.serializers.ModelsForDeserialization.model.TeamStanding teamStanding);

    IConference creatConferenceFromDeserialization(persistance.serializers.ModelsForDeserialization.model.Conference conference);

    IFreeAgent creatFreeAgentFromDeserialization(persistance.serializers.ModelsForDeserialization.model.FreeAgent freeAgent);

    IGamePlayConfig creatGamePlayConfigFromDeserialization(persistance.serializers.ModelsForDeserialization.model.GamePlayConfig gamePlayConfig);

    IGameSchedule createGameScheduleFromDeserialization(persistance.serializers.ModelsForDeserialization.model.GameSchedule gameSchedule);

    INHLEvents createNHLEventsFromDeserialization(persistance.serializers.ModelsForDeserialization.model.NHLEvents nhlEvents);

    TeamStat createTeamStatFromDeserialization(persistance.serializers.ModelsForDeserialization.model.TeamStat teamStat);

    ITradeOffer createTradeOfferFromDeserialization(persistance.serializers.ModelsForDeserialization.model.TradeOffer tradeOffer);

    IManager createManagerFromManager(IManager manager);

    IPlayer createPlayerFromPlayer(IPlayer player);

    ITeamScore createTeamScoreFromDeserialization(persistance.serializers.ModelsForDeserialization.model.TeamScore teamScore);

    IGame createGameFromDeserialization(persistance.serializers.ModelsForDeserialization.model.Game game);
}
