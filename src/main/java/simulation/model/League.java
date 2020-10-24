package simulation.model;

import db.data.IConferenceFactory;
import db.data.IFreeAgentFactory;
import db.data.ILeagueFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class League extends ParentObj{

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

    private String country;

    private int createdBy;

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

    public List<Manager> removeManagerFromManagerListById(List<Manager> managerList, int indexOfManagerObject){
        int managerListSize = managerList.size();
        Manager manager = new Manager(managerList.get(managerListSize-1));
        managerList.set(indexOfManagerObject, manager);
        managerList.remove(managerListSize-1);
        return managerList;
    }

    public List<Coach> removeCoachFromCoachListById(List<Coach> coachList, int indexOfCoachObject){
        int coachListSize = coachList.size();
        Coach coach = new Coach(coachList.get(coachListSize-1));
        coachList.set(indexOfCoachObject, coach);
        coachList.remove(coachListSize-1);
        return coachList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
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
}
