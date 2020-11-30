package simulation.model;

import persistance.dao.*;
import persistance.serializers.ModelsForDeserialization.model.Coach;
import persistance.serializers.ModelsForDeserialization.model.Conference;
import persistance.serializers.ModelsForDeserialization.model.Division;
import persistance.serializers.ModelsForDeserialization.model.FreeAgent;
import persistance.serializers.ModelsForDeserialization.model.Game;
import persistance.serializers.ModelsForDeserialization.model.GamePlayConfig;
import persistance.serializers.ModelsForDeserialization.model.GameSchedule;
import persistance.serializers.ModelsForDeserialization.model.Manager;
import persistance.serializers.ModelsForDeserialization.model.NHLEvents;
import persistance.serializers.ModelsForDeserialization.model.Player;
import persistance.serializers.ModelsForDeserialization.model.Team;
import persistance.serializers.ModelsForDeserialization.model.TeamScore;
import persistance.serializers.ModelsForDeserialization.model.TeamStanding;
import persistance.serializers.ModelsForDeserialization.model.TradeOffer;
import persistance.serializers.ModelsForDeserialization.model.Trophy;

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

    IManager createManagerConcrete();

    INHLEvents createNHLEvents();

    INHLEvents createNHLEventsByYear(int year);

    IPlayer createPlayer();

    IPlayer createPlayerFromSerialization(Player player);

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

    ITraining createTraining();

    IUser createUser();

    IUser createUserByName(String name, IUserDao loadUserFactory) throws Exception;

    IUser createUserWithIdDao(int id, IUserDao userDao) throws Exception;

    IFreeAgent createFreeAgentWithId(int id);

    ITrophy createTrophy();

    IFreeAgent createFreeAgentWithIdDao(int id, IFreeAgentDao freeAgentDao) throws Exception;

    IShift createShift();

    IGameSimulation createGameSimulationFromTeams(ITeam team1, ITeam team2);

    ICoach createCoachFromDeserialization(Coach coach);

    IManager createManagerFromDeserialization(Manager manager);

    ITeam createTeamFromDeserialization(Team team);

    IDivision createDivisionFromDeserialization(Division division);

    IGame createGameFromDeserialization(Game game);

    ITeamScore createTeamScoreFromDeserialization(TeamScore teamScore);

    ITeamStanding createTeamStandingFromDeserialization(TeamStanding teamStanding);

    IConference creatConferenceFromDeserialization(Conference conference);

    IFreeAgent creatFreeAgentFromDeserialization(FreeAgent freeAgent);

    IGamePlayConfig creatGamePlayConfigFromDeserialization(GamePlayConfig gamePlayConfig);

    IGameSchedule createGameScheduleFromDeserialization(GameSchedule gameSchedule);

    INHLEvents createNHLEventsFromDeserialization(NHLEvents nhlEvents);

    TeamStat createTeamStatFromDeserialization(persistance.serializers.ModelsForDeserialization.model.TeamStat teamStat);

    ITradeOffer createTradeOfferFromDeserialization(TradeOffer tradeOffer);

    ITrophy createTrophyFromDeserialization(Trophy trophy);


    IManager createManagerFromManager(IManager manager);

    IPlayer createPlayerFromPlayer(IPlayer player);
}
