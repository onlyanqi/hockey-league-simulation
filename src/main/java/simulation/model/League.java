package simulation.model;

import db.data.*;
import simulation.factory.CoachConcrete;
import simulation.factory.ICoachFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class League extends SharedAttributes implements ILeague {

    private int createdBy;
    private String user;
    private String userCreatedTeamName;
    private List<IConference> conferenceList;
    private List<ICoach> coachList;
    private List<IManager> managerList;
    private List<IPlayer> retiredPlayerList;
    private IFreeAgent freeAgent;
    private LocalDate currentDate;
    private IGamePlayConfig gamePlayConfig;
    private IGameSchedule games;
    private ITeamStanding regularSeasonStanding;
    private ITeamStanding playOffStanding;
    private ITeamStanding activeTeamStanding;
    HashMap<String,Integer> stanleyCupFinalsTeamScores;
    private INHLEvents nhlEvents;
    private List<ITradeOffer> tradeOfferList;

    public League() {
        setId(System.identityHashCode(this));
    }

    public League(int id) {
        setId(id);
    }

    public League(int id, ILeagueDao factory) throws Exception {
        if (factory == null) {
            return;
        }
        setId(id);
        factory.loadLeagueById(id, this);
    }

    public League(String leagueName, int userId, ILeagueDao loadLeagueFactory) throws Exception {
        loadLeagueFactory.loadLeagueByName(leagueName, userId, this);
    }


    public String getUserCreatedTeamName() {
        return userCreatedTeamName;
    }

    public void setUserCreatedTeamName(String userCreatedTeamName) {
        this.userCreatedTeamName = userCreatedTeamName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public INHLEvents getNhlEvents() {
        return nhlEvents;
    }

    public void setNhlEvents(INHLEvents nhlEvents) {
        this.nhlEvents = nhlEvents;
    }

    public INHLEvents getNHLRegularSeasonEvents() {
        return nhlEvents;
    }

    public void setNhlRegularSeasonEvents(INHLEvents nhlEvents) {
        if (nhlEvents == null) {
            return;
        }
        this.nhlEvents = nhlEvents;
    }

    public IGameSchedule getGames() {
        return games;
    }

    public void setGames(IGameSchedule games) {
        if (games == null) {
            return;
        }
        this.games = games;
    }

    public IGamePlayConfig getGamePlayConfig() {
        return gamePlayConfig;
    }

    public void setGamePlayConfig(IGamePlayConfig gamePlayConfig) {
        if (gamePlayConfig == null) {
            return;
        }
        this.gamePlayConfig = gamePlayConfig;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        if (currentDate == null) {
            return;
        }
        this.currentDate = currentDate;
    }

    public List<IConference> getConferenceList() {
        return conferenceList;
    }

    public void setConferenceList(List<IConference> conferenceList) {
        if (conferenceList == null) {
            return;
        }
        this.conferenceList = conferenceList;
    }

    public List<ICoach> getCoachList() {
        return coachList;
    }

    public void setCoachList(List<ICoach> coachList) {
        if (coachList == null) {
            return;
        }
        this.coachList = coachList;
    }

    public List<IManager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<IManager> managerList) {
        if (managerList == null) {
            return;
        }
        this.managerList = managerList;
    }

    public List<IPlayer> getRetiredPlayerList() {
        return retiredPlayerList;
    }

    public void setRetiredPlayerList(List<IPlayer> retiredPlayerList) {
        this.retiredPlayerList = retiredPlayerList;
    }

    public ITeamStanding getRegularSeasonStanding() {
        return regularSeasonStanding;
    }

    public void setRegularSeasonStanding(ITeamStanding regularSeasonStanding) {
        if (regularSeasonStanding == null) {
            return;
        }
        this.regularSeasonStanding = regularSeasonStanding;
    }

    public ITeamStanding getPlayOffStanding() {
        return playOffStanding;
    }

    public void setPlayOffStanding(ITeamStanding playOffStanding) {
        if (playOffStanding == null) {
            return;
        }
        this.playOffStanding = playOffStanding;
    }

    public ITeamStanding getActiveTeamStanding() {
        return activeTeamStanding;
    }

    public void setActiveTeamStanding(ITeamStanding activeTeamStanding) {
        if (activeTeamStanding == null) {
            return;
        }
        this.activeTeamStanding = activeTeamStanding;
    }

    public HashMap<String, Integer> getStanleyCupFinalsTeamScores() {
        return stanleyCupFinalsTeamScores;
    }

    public void setStanleyCupFinalsTeamScores(HashMap<String, Integer> stanleyCupFinalsTeamScores) {
        this.stanleyCupFinalsTeamScores = stanleyCupFinalsTeamScores;
    }

    public List<IManager> removeManagerFromManagerListById(List<IManager> managerList, int indexOfManagerObject) {
        if (null == managerList) {
            return null;
        }
        int managerListSize = managerList.size();
        IManager manager = new Manager(managerList.get(managerListSize - 1));
        managerList.set(indexOfManagerObject, manager);
        managerList.remove(managerListSize - 1);
        return managerList;
    }

    public List<ICoach> removeCoachFromCoachListById(List<ICoach> coachList,
                                                    int indexOfCoachObject, ICoachFactory coachFactory) {
        if (null == coachList) {
            return null;
        }
        int coachListSize = coachList.size();
        ICoach coach = coachFactory.newCoachWithCoach(coachList.get(coachListSize - 1));
        coachList.set(indexOfCoachObject, coach);
        coachList.remove(coachListSize - 1);
        return coachList;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public IFreeAgent getFreeAgent() {
        return freeAgent;
    }

    public void setFreeAgent(IFreeAgent freeAgent) {
        if (freeAgent == null) {
            return;
        }
        this.freeAgent = freeAgent;
    }

    public void addLeague(ILeagueDao addLeagueFactory) throws Exception {
        if (addLeagueFactory == null) {
            return;
        }
        addLeagueFactory.addLeague(this);
    }

    public void loadConferenceListByLeagueId(IConferenceDao loadConferenceFactory) throws Exception {
        if (loadConferenceFactory == null) {
            return;
        }
        this.conferenceList = loadConferenceFactory.loadConferenceListByLeagueId(getId());
    }

    public List<String> createConferenceNameList() {
        List<String> conferenceNameList = new ArrayList<>();
        for (IConference conference : conferenceList) {
            conferenceNameList.add(conference.getName().toLowerCase());
        }
        return conferenceNameList;
    }

    public IConference getConferenceFromListByName(String conferenceName) {
        IConference foundConference = null;
        for (IConference conference : conferenceList) {
            if (conference.getName().toLowerCase().equals(conferenceName.toLowerCase())) {
                foundConference = conference;
                break;
            }
        }
        return foundConference;
    }

    public void loadFreeAgentByLeagueId(IFreeAgentDao loadFreeAgentFactory) throws Exception {
        this.freeAgent = loadFreeAgentFactory.loadFreeAgentByLeagueId(getId());
    }

    public ITeam getTeamByTeamName(String teamName) {
        for (IConference conference : getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    if (team.getName().equals(teamName)) {
                        return team;
                    }
                }
            }
        }
        return null;
    }


    public List<ITradeOffer> getTradeOfferList() {
        return this.tradeOfferList;
    }

    public void setTradeOfferList(List<ITradeOffer> tradeOfferList) {
        this.tradeOfferList = tradeOfferList;
    }

    public void loadTradingOfferDetailsByLeagueId(ITradeOfferDao tradingOfferFactory) throws Exception {
        this.tradeOfferList = tradingOfferFactory.loadTradeOfferDetailsByLeagueId(getId());
    }

}
