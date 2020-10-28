package simulation.model;

import db.data.*;
import com.google.gson.annotations.SerializedName;
import db.data.IConferenceFactory;
import db.data.IFreeAgentFactory;
import db.data.ILeagueFactory;
import simulation.RegularSeasonEvents.NHLEvents;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class League extends SharedAttributes {

    private String country;
    private int createdBy;
    private List<Conference> conferenceList;
    private List<Coach> coachList;
    private List<Manager> managerList;
    private List<String> generalManagers;
    private FreeAgent freeAgent;
    private List<Player> freeAgents;
    private LocalDate currentDate;
    private GamePlayConfig gamePlayConfig;
    private Games games;
    private RegularSeasonScoreBoard regularSeasonScoreBoard;
    private NHLEvents nhlEvents;

    public League() {
    }

    public League(int id) {
        setId(id);
    }

    public League(int id, ILeagueFactory factory) throws Exception {
        setId(id);
        factory.loadLeagueById(id, this);
    }

    public League(String leagueName, int userId, ILeagueFactory loadLeagueFactory) throws Exception {
        loadLeagueFactory.loadLeagueByName(leagueName, userId, this);
    }

    public NHLEvents getNHLRegularSeasonEvents() {
        return nhlEvents;
    }

    public void setNhlRegularSeasonEvents(NHLEvents nhlEvents) {
        this.nhlEvents = nhlEvents;
    }

    public RegularSeasonScoreBoard getRegularSeasonScoreBoard() {
        return regularSeasonScoreBoard;
    }

    public void setRegularSeasonScoreBoard(RegularSeasonScoreBoard regularSeasonScoreBoard) {
        this.regularSeasonScoreBoard = regularSeasonScoreBoard;
    }

    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

    public GamePlayConfig getGamePlayConfig() {
        return gamePlayConfig;
    }

    public void setGamePlayConfig(GamePlayConfig gamePlayConfig) {
        this.gamePlayConfig = gamePlayConfig;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public List<Player> getFreeAgentList() {
        return freeAgents;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Conference> getConferenceList() {
        return conferenceList;
    }

    public void setConferenceList(List<Conference> conferenceList) {
        this.conferenceList = conferenceList;
    }

    public List<Coach> getCoachList() {
        return coachList;
    }

    public void setCoachList(List<Coach> coachList) {
        this.coachList = coachList;
    }

    public List<Manager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
        this.generalManagers = createManagerNameList(managerList);
    }

    public List<Manager> removeManagerFromManagerListById(List<Manager> managerList, int indexOfManagerObject) {
        int managerListSize = managerList.size();
        Manager manager = new Manager(managerList.get(managerListSize - 1));
        managerList.set(indexOfManagerObject, manager);
        managerList.remove(managerListSize - 1);
        return managerList;
    }

    public List<Coach> removeCoachFromCoachListById(List<Coach> coachList, int indexOfCoachObject) {
        int coachListSize = coachList.size();
        Coach coach = new Coach(coachList.get(coachListSize - 1));
        coachList.set(indexOfCoachObject, coach);
        coachList.remove(coachListSize - 1);
        return coachList;
    }

    public List<String> createManagerNameList(List<Manager> managerList) {
        List<String> managerNameList = new ArrayList<>();
        for (int i = 0; i < managerList.size(); i++) {
            managerNameList.add(managerList.get(i).getName());
        }
        return managerNameList;
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
        this.freeAgent = freeAgent;
        this.freeAgents = freeAgent.getPlayerList();
    }

    public void addLeague(ILeagueFactory addLeagueFactory) throws Exception {
        addLeagueFactory.addLeague(this);
    }

    public void loadConferenceListByLeagueId(IConferenceFactory loadConferenceFactory) throws Exception {
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
        Conference foundConference = null;
        for (Conference conference : conferenceList) {
            if (conference.getName().toLowerCase().equals(conferenceName.toLowerCase())) {
                foundConference = conference;
                break;
            }
        }
        return foundConference;
    }

    public void loadFreeAgentByLeagueId(IFreeAgentFactory loadFreeAgentFactory) throws Exception {
        this.freeAgent = loadFreeAgentFactory.loadFreeAgentByLeagueId(getId());
    }

    public Team getTeamByTeamName(String teamName) {
        for (Conference conference : getConferenceList()) {
            for (Division division : conference.getDivisionList()) {
                for (Team team : division.getTeamList()) {
                    if (team.getName().equals(teamName)) {
                        return team;
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
        this.trading = trading;
    }

    public void loadTradingDetailsByLeagueId(ITradingFactory tradingFactory) throws Exception {
        this.trading = tradingFactory.loadTradingDetailsByLeagueId(getId());
    }

    private transient List<TradeOffer> tradeOfferList;

    public List<TradeOffer> getTradingOfferList(){
        return this.tradeOfferList;
    }

    public void setTradingOfferList(List<TradeOffer> tradeOfferList){
        this.tradeOfferList = tradeOfferList;
    }

    public void loadTradingOfferDetailsByLeagueId(ITradeOfferFactory tradingOfferFactory) throws Exception {
        this.tradeOfferList = tradingOfferFactory.loadTradingOfferDetailsByLeagueId(getId());
    }

}
