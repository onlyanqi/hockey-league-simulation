package model;

import data.ILeagueFactory;
import java.util.List;

public class League extends ParentObj{

    public League(){}

    public League(long id){
        setId(id);
    }

    public League(long id, ILeagueFactory factory) throws Exception{
        setId(id);
        factory.loadLeagueByName(id, this);
    }

    private String country;

    private int createdBy;

    private List<Conference> conferenceList;

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
}
