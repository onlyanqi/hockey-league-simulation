package simulation.model;

import db.data.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class League extends SharedAttributes {

    public League(){}

    public League(int id){
        setId(id);
    }

    public League(int id, ILeagueFactory factory) throws Exception{
        setId(id);
        factory.loadLeagueById(id, this);
    }

    public League(String leagueName, int userId, ILeagueFactory loadLeagueFactory) throws Exception {
        loadLeagueFactory.loadLeagueByName(leagueName, userId, this);
    }

    private List<Conference> conferenceList;

    private List<Coach> coachList;

    private List<Manager> managerList;

    private FreeAgent freeAgent;

    private Date currentDate;

    private GamePlayConfig gamePlayConfig;

    public void setGamePlayConfig(GamePlayConfig gamePlayConfig){
        this.gamePlayConfig = gamePlayConfig;
    }

    public GamePlayConfig getGamePlayConfig(){
        return gamePlayConfig;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
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

    public Manager getManagerByName(List<Manager> managerList, String managerName){
        for(int i=0;i<managerList.size();i++){
            if(managerList.get(i).getName().equals(managerName)){
                return managerList.get(i);
            }else{
                return null;
            }
        }
        return null;
    }

    public List<Manager> removeManagerFromManagerListById(List<Manager> managerList, int indexOfManagerObject){
        int managerListSize = managerList.size();
        for(int i=indexOfManagerObject; i<managerListSize-2;i++){
            Manager manager = new Manager(managerList.get(i+1));
            managerList.remove(i);
            managerList.set(i, manager);
        }
  //      managerList.remove(managerListSize-1);
        return managerList;
    }

    public Coach getCoachByName(List<Coach> coachList, String coachName){
        for(int i=0;i<coachList.size();i++){
            if(coachList.get(i).getName().equals(coachName)){
                return coachList.get(i);
            }else{
                return null;
            }
        }
        return null;
    }

    public List<Coach> removeCoachFromCoachListById(List<Coach> coachList, int indexOfCoachObject){
        int coachListSize = coachList.size();
        for(int i=indexOfCoachObject; i<coachListSize-2;i++){
            Coach coach = new Coach(coachList.get(i+1));
            coachList.remove(i);
            coachList.set(i, coach);
        }
 //       coachList.remove(coachListSize-1);
        return coachList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
    }

    public FreeAgent getFreeAgent() {
        return freeAgent;
    }

    public void setFreeAgent(FreeAgent freeAgent) {
        this.freeAgent = freeAgent;
    }

    public void addLeague(ILeagueFactory addLeagueFactory) throws Exception {
        addLeagueFactory.addLeague(this);
    }

    public void loadConferenceListByLeagueId(IConferenceFactory loadConferenceFactory) throws Exception {
        this.conferenceList = loadConferenceFactory.loadConferenceListByLeagueId(getId());
    }

    public List<String> createConferenceNameList(){
        List<String> conferenceNameList = new ArrayList<>();
        for(Conference conference : conferenceList){
            conferenceNameList.add(conference.getName().toLowerCase());
        }
        return conferenceNameList;
    }

    public Conference getConferenceFromListByName(String conferenceName){
        Conference foundConference = null;
        for(Conference conference : conferenceList) {
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
