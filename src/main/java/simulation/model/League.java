package simulation.model;

import db.data.*;
import db.data.IConferenceFactory;
import db.data.IFreeAgentFactory;
import db.data.ILeagueFactory;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class League extends SharedAttributes {

    private int createdBy;
    private List<Conference> conferenceList;
    private List<Coach> coachList;
    private List<Manager> managerList;
    private FreeAgent freeAgent;
    private LocalDate currentDate;
    private GamePlayConfig gamePlayConfig;
    private Games games;
    private TeamStanding regularSeasonStanding;
    private TeamStanding playOffStanding;
    private TeamStanding activeTeamStanding;
    private NHLEvents nhlEvents;

    public League() {
    }

    public League(int id) {
        setId(id);
    }

    public League(int id, ILeagueFactory factory) throws Exception {
        if(factory == null){
            return;
        }
        setId(id);
        factory.loadLeagueById(id, this);
    }

    public League(String leagueName, int userId, ILeagueFactory loadLeagueFactory) throws Exception {
        if(isNotEmpty(leagueName)){
            loadLeagueFactory.loadLeagueByName(leagueName, userId, this);
        }
    }

    public NHLEvents getNHLRegularSeasonEvents() {
        return nhlEvents;
    }

    public void setNhlRegularSeasonEvents(NHLEvents nhlEvents) {
        if(nhlEvents == null){
            return;
        }
        this.nhlEvents = nhlEvents;
    }


    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        if(games == null){
            return;
        }
        this.games = games;
    }

    public GamePlayConfig getGamePlayConfig() {
        return gamePlayConfig;
    }

    public void setGamePlayConfig(GamePlayConfig gamePlayConfig) {
        if(gamePlayConfig == null){
            return;
        }
        this.gamePlayConfig = gamePlayConfig;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        if(currentDate == null){
            return;
        }
        this.currentDate = currentDate;
    }

    public List<Conference> getConferenceList() {
        return conferenceList;
    }

    public void setConferenceList(List<Conference> conferenceList) {
        if(conferenceList == null){
            return;
        }
        this.conferenceList = conferenceList;
    }

    public List<Coach> getCoachList() {
        return coachList;
    }

    public void setCoachList(List<Coach> coachList) {
        if(coachList == null){
            return;
        }
        this.coachList = coachList;
    }

    public List<Manager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<Manager> managerList) {
        if(managerList == null){
            return;
        }
        this.managerList = managerList;
    }

    public TeamStanding getRegularSeasonStanding() {
        return regularSeasonStanding;
    }

    public void setRegularSeasonStanding(TeamStanding regularSeasonStanding) {
        if(regularSeasonStanding == null){
            return;
        }
        this.regularSeasonStanding = regularSeasonStanding;
    }

    public TeamStanding getPlayOffStanding() {
        return playOffStanding;
    }

    public void setPlayOffStanding(TeamStanding playOffStanding) {
        if(playOffStanding == null){
            return;
        }
        this.playOffStanding = playOffStanding;
    }

    public TeamStanding getActiveTeamStanding() {
        return activeTeamStanding;
    }

    public void setActiveTeamStanding(TeamStanding activeTeamStanding) {
        if(activeTeamStanding == null){
            return;
        }
        this.activeTeamStanding = activeTeamStanding;
    }

    public List<Manager> removeManagerFromManagerListById(List<Manager> managerList, int indexOfManagerObject) {
        if(managerList == null){
            return null;
        }
        int managerListSize = managerList.size();
        Manager manager = new Manager(managerList.get(managerListSize - 1));
        managerList.set(indexOfManagerObject, manager);
        managerList.remove(managerListSize - 1);
        return managerList;
    }

    public List<Coach> removeCoachFromCoachListById(List<Coach> coachList, int indexOfCoachObject) {
        if(coachList == null){
            return null;
        }
        int coachListSize = coachList.size();
        Coach coach = new Coach(coachList.get(coachListSize - 1));
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

    public FreeAgent getFreeAgent() {
        return freeAgent;
    }

    public void setFreeAgent(FreeAgent freeAgent) {
        if(freeAgent == null){
            return;
        }
        this.freeAgent = freeAgent;
    }

    public void addLeague(ILeagueFactory addLeagueFactory) throws Exception {
        if(addLeagueFactory == null){
            return;
        }
        addLeagueFactory.addLeague(this);
    }

    public void loadConferenceListByLeagueId(IConferenceFactory loadConferenceFactory) throws Exception {
        if(loadConferenceFactory == null){
            return;
        }
        this.conferenceList = loadConferenceFactory.loadConferenceListByLeagueId(getId());
    }

    public List<String> createConferenceNameList() {
        List<String> conferenceNameList = new ArrayList<>();
        for (Conference conference : conferenceList) {
            conferenceNameList.add(conference.getName().toLowerCase());
        }
        return conferenceNameList;
    }

    public Conference getConferenceFromListByName(String conferenceName) {
        if(isNotEmpty(conferenceName)){
            Conference foundConference = null;
            for (Conference conference : conferenceList) {
                if (conference.getName().toLowerCase().equals(conferenceName.toLowerCase())) {
                    foundConference = conference;
                    break;
                }
            }
            return foundConference;
        }else{
            return null;
        }

    }

    public void loadFreeAgentByLeagueId(IFreeAgentFactory loadFreeAgentFactory) throws Exception {
        this.freeAgent = loadFreeAgentFactory.loadFreeAgentByLeagueId(getId());
    }

    public Team getTeamByTeamName(String teamName) {
        if(isNotEmpty(teamName)){
            for (Conference conference : getConferenceList()) {
                for (Division division : conference.getDivisionList()) {
                    for (Team team : division.getTeamList()) {
                        if (team.getName().equals(teamName)) {
                            return team;
                        }
                    }
                }
            }
        }
        return null;
    }

    private transient Trading trading;

    public Trading getTrading() {
        return trading;
    }

    public void setTrading(Trading trading) {
        if(trading == null){
            return;
        }
        this.trading = trading;
    }

    public void loadTradingDetailsByLeagueId(ITradingFactory tradingFactory) throws Exception {
        if(tradingFactory == null){
            return;
        }
        this.trading = tradingFactory.loadTradingDetailsByLeagueId(getId());
    }

    private transient List<TradeOffer> tradeOfferList;

    public List<TradeOffer> getTradingOfferList(){
        return this.tradeOfferList;
    }

    public void setTradingOfferList(List<TradeOffer> tradeOfferList){
        if(tradeOfferList == null){
            return;
        }
        this.tradeOfferList = tradeOfferList;
    }

    public void loadTradingOfferDetailsByLeagueId(ITradeOfferFactory tradingOfferFactory) throws Exception {
        if(tradingOfferFactory == null){
            return;
        }
        this.tradeOfferList = tradingOfferFactory.loadTradingOfferDetailsByLeagueId(getId());
    }

}
