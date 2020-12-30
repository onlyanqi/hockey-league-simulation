package simulation.model;

import persistance.dao.IConferenceDao;
import persistance.dao.IFreeAgentDao;
import persistance.dao.ILeagueDao;
import persistance.dao.ITradeOfferDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ILeague {

    INHLEvents getNHLRegularSeasonEvents();

    void setNhlRegularSeasonEvents(INHLEvents nhlEvents);

    List<ITrophy> getHistoricalTrophyList();

    void setHistoricalTrophyList(List<ITrophy> historicalTrophyList);

    IGameSchedule getGames();

    void setGames(IGameSchedule games);

    IGamePlayConfig getGamePlayConfig();

    void setGamePlayConfig(IGamePlayConfig gamePlayConfig);

    LocalDate getCurrentDate();

    void setCurrentDate(LocalDate currentDate);

    List<IConference> getConferenceList();

    void setConferenceList(List<IConference> conferenceList);

    List<ICoach> getCoachList();

    void setCoachList(List<ICoach> coachList);

    List<IManager> getManagerList();

    void setManagerList(List<IManager> managerList);

    List<IPlayer> getRetiredPlayerList();

    void setRetiredPlayerList(List<IPlayer> retiredPlayerList);

    ITeamStanding getRegularSeasonStanding();

    void setRegularSeasonStanding(ITeamStanding regularSeasonStanding);

    ITeamStanding getPlayOffStanding();

    void setPlayOffStanding(ITeamStanding playOffStanding);

    ITeamStanding getActiveTeamStanding();

    void setActiveTeamStanding(ITeamStanding activeTeamStanding);

    HashMap<ITeam, Integer> getStanleyCupFinalsTeamScores();

    void setStanleyCupFinalsTeamScores(HashMap<ITeam, Integer> stanleyCupFinalsTeamScores);

    ArrayList<TeamStat> getTeamStats();

    void setTeamStats(ArrayList<TeamStat> teamStats);

    List<IManager> removeManagerFromManagerListById(List<IManager> managerList, int indexOfManagerObject);

    List<ICoach> removeCoachFromCoachListById(List<ICoach> coachList,
                                              int indexOfCoachObject, IModelFactory coachFactory);

    int getCreatedBy();

    void setCreatedBy(int createdBy);

    IFreeAgent getFreeAgent();

    void setFreeAgent(IFreeAgent freeAgent);

    void addLeague(ILeagueDao addLeagueFactory) throws Exception;

    void loadConferenceListByLeagueId(IConferenceDao loadConferenceFactory) throws Exception;

    List<String> createConferenceNameList();

    IConference getConferenceFromListByName(String conferenceName);

    void loadFreeAgentByLeagueId(IFreeAgentDao loadFreeAgentFactory) throws Exception;

    ITeam getTeamByTeamName(String teamName);

    TeamStat getTeamStatByTeamName(String teamName);

    List<ITradeOffer> getTradeOfferList();

    void setTradeOfferList(List<ITradeOffer> tradeOfferList);

    void loadTradingOfferDetailsByLeagueId(ITradeOfferDao tradingOfferFactory) throws Exception;

    List<IPlayer> getDraftedPlayerList();

    void setDraftedPlayerList(List<IPlayer> draftedPlayerList);

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    ITrophy getTrophy();

    void setTrophy(ITrophy trophy);

    String getUserCreatedTeamName();

    void setUserCreatedTeamName(String userCreatedTeamName);

    String getUser();

    void setUser(String user);

    List<ITeam> createTeamList();

    List<IPlayer> createPlayerList();

    List<ICoach> createCoachList();

    void initializeTeamStats();
}
