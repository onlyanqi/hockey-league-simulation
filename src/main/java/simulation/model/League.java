package simulation.model;

import simulation.dao.IConferenceDao;
import simulation.dao.IFreeAgentDao;
import simulation.dao.ILeagueDao;
import simulation.dao.ITradeOfferDao;
import simulation.serializers.ModelsForDeserialization.model.Coach;
import simulation.serializers.ModelsForDeserialization.model.Conference;
import simulation.serializers.ModelsForDeserialization.model.LeagueDeserializationModel;
import simulation.serializers.ModelsForDeserialization.model.Player;
import simulation.serializers.ModelsForDeserialization.model.TradeOffer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class League extends SharedAttributes implements ILeague {

    private int createdBy;
    private String user;
    private String userCreatedTeamName;
    private List<IConference> conferenceList = new ArrayList<>();
    private List<ICoach> coachList = new ArrayList<>();
    private List<IManager> managerList = new ArrayList<>();
    private List<IPlayer> retiredPlayerList = new ArrayList<>();
    private List<IPlayer> draftedPlayerList = new ArrayList<>();
    private IFreeAgent freeAgent;
    private LocalDate currentDate;
    private IGamePlayConfig gamePlayConfig;
    private IGameSchedule games;
    private ITeamStanding regularSeasonStanding;
    private ITeamStanding playOffStanding;
    private ITeamStanding activeTeamStanding;
    private HashMap<String,Integer> stanleyCupFinalsTeamScores = new HashMap<>();
    private ArrayList<TeamStat> teamStats = new ArrayList<>();
    private INHLEvents nhlEvents;
    private List<ITradeOffer> tradeOfferList = new ArrayList<>();
    private List<ITrophy> historicalTrophyList = new ArrayList<>();
    private ITrophy trophy;

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

    public League(LeagueDeserializationModel leagueDeserializationModel){
        this.activeTeamStanding = new TeamStanding(leagueDeserializationModel.activeTeamStanding);
        for(Coach coach : leagueDeserializationModel.coachList){
            this.coachList.add(new simulation.model.Coach(coach));
        }
        for(Conference conference : leagueDeserializationModel.conferenceList){
            this.conferenceList.add(new simulation.model.Conference(conference));
        }
        this.createdBy = leagueDeserializationModel.createdBy;
        this.currentDate = leagueDeserializationModel.currentDate;
        this.freeAgent = new FreeAgent(leagueDeserializationModel.freeAgent);
        this.gamePlayConfig = new GamePlayConfig(leagueDeserializationModel.gamePlayConfig);
        this.games = new GameSchedule(leagueDeserializationModel.games);
        for(simulation.serializers.ModelsForDeserialization.model.Manager manager : leagueDeserializationModel.managerList){
            this.managerList.add(new Manager(manager));
        }
        this.nhlEvents = new NHLEvents(leagueDeserializationModel.nhlEvents);
        this.playOffStanding = new TeamStanding(leagueDeserializationModel.playOffStanding);
        this.regularSeasonStanding = new TeamStanding(leagueDeserializationModel.regularSeasonStanding);
        for(Player player : leagueDeserializationModel.retiredPlayerList){
            this.retiredPlayerList.add(new simulation.model.Player(player));
        }
        if(leagueDeserializationModel.stanleyCupFinalsTeamScores == null){
            this.stanleyCupFinalsTeamScores = new HashMap<>();
        }else{
            this.stanleyCupFinalsTeamScores = leagueDeserializationModel.stanleyCupFinalsTeamScores;
        }

        if(leagueDeserializationModel.teamStats == null){
            this.teamStats = new ArrayList<>();
        }else{
            for(simulation.serializers.ModelsForDeserialization.model.TeamStat teamStat : leagueDeserializationModel.teamStats){
                this.teamStats.add(new TeamStat(teamStat));
            }
        }

        this.nhlEvents = new NHLEvents(leagueDeserializationModel.nhlEvents);
        for(TradeOffer tradeOffer : leagueDeserializationModel.tradeOfferList){
            this.tradeOfferList.add(new simulation.model.TradeOffer(tradeOffer));
        }
        for(simulation.serializers.ModelsForDeserialization.model.Trophy trophy : leagueDeserializationModel.historicalTrophyList){
            this.historicalTrophyList.add(new Trophy(trophy));
        }

        if(leagueDeserializationModel.trophy == null){
            this.trophy = new Trophy();
        }else{
            this.trophy = new Trophy(leagueDeserializationModel.trophy);
        }
        this.user = leagueDeserializationModel.user;
        this.userCreatedTeamName = leagueDeserializationModel.userCreatedTeamName;
        this.setName(leagueDeserializationModel.name);
        this.setId(leagueDeserializationModel.id);
    }

    @Override
    public ITrophy getTrophy() {
        return trophy;
    }

    @Override
    public void setTrophy(ITrophy trophy) {
        this.trophy = trophy;
    }


    @Override
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

    @Override
    public List<ITrophy> getHistoricalTrophyList() {
        return historicalTrophyList;
    }

    @Override
    public void setHistoricalTrophyList(List<ITrophy> historicalTrophyList) {
        this.historicalTrophyList = historicalTrophyList;
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

    @Override
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

    @Override
    public ArrayList<TeamStat> getTeamStats() {
        return teamStats;
    }

    @Override
    public void setTeamStats(ArrayList<TeamStat> teamStats) {
        this.teamStats = teamStats;
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
                                                    int indexOfCoachObject, IModelFactory coachFactory) {
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

    @Override
    public TeamStat getTeamStatByTeamName(String teamName) {
        for(TeamStat teamStat: teamStats){
            if(teamStat.getTeamName().equals(teamName)){
                return teamStat;
            }
        }
        throw new IllegalArgumentException("Provided teamName doesn't contain any stats");
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

    public List<IPlayer> getDraftedPlayerList() {
        return draftedPlayerList;
    }

    public void setDraftedPlayerList(List<IPlayer> draftedPlayerList) {
        this.draftedPlayerList = draftedPlayerList;
    }


    @Override
    public List<ITeam> createTeamList(){
        List<ITeam> teamList=new ArrayList<>();
        for (IConference conference : getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    teamList.add(team);
                }
            }
        }
        return teamList;
    }

    @Override
    public List<IPlayer> createPlayerList(){
        List<IPlayer> playerList=new ArrayList<>();
        for (IConference conference : getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    playerList.addAll(team.getPlayerList());
                }
            }
        }
        return playerList;
    }

    @Override
    public List<ICoach> createCoachList(){
        List<ICoach> coachList=new ArrayList<>();
        for (IConference conference : getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    coachList.add(team.getCoach());
                }
            }
        }
        return coachList;
    }

    public void initializeTeamStats(){
        ArrayList<TeamStat> teamStats = new ArrayList<>();
        for(IConference conference : getConferenceList()){
            for(IDivision division : conference.getDivisionList()){
                for(ITeam team : division.getTeamList()){
                    TeamStat teamStat = new TeamStat();
                    teamStat.setTeamName(team.getName());
                    teamStats.add(teamStat);
                }
            }
        }
        setTeamStats(teamStats);
    }

}
