package model;

import data.ILoadLeagueFactory;
import java.util.List;

public class League extends ParentObj{

    public League(){}

    public League(int id){
        setId(id);
    }

    public League(int id, ILoadLeagueFactory factory) throws Exception{
        setId(id);
        factory.loadLeagueById(id, this);
    }

    private String country;

    private int createdBy;

    private List<Conference> conferenceList;

    private FreeAgent freeAgent;

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
}
