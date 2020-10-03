package model;

import data.ILeagueFactory;
import java.util.List;

public class League extends ParentObj{

    public League(){}

    public League(long id){
        setId(id);
    }

    public League(long id, ILeagueFactory factory){
        setId(id);
        factory.loadLeague(id, this);
    }

    private String country;

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
}
