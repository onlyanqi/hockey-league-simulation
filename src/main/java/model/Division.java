package model;

import data.IDivisionFactory;

import java.util.List;

public class Division extends ParentObj{

    public Division(){}

    public Division(long id){
        setId(id);
    }

    public Division(long id, IDivisionFactory factory){
        setId(id);
        factory.loadDivision(id, this);
    }

    private long conferenceId;

    private List<Team> teamList;

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
        this.conferenceId = conferenceId;
    }
}
