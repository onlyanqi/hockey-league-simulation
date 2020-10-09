package model;

import data.IAddConferenceFactory;
import data.IAddDivisionFactory;
import data.ILoadDivisionFactory;
import data.ILoadTeamFactory;

import java.util.List;

public class Division extends ParentObj{

    public Division(){}

    public Division(int id){
        setId(id);
    }

    public Division(int id, ILoadDivisionFactory factory) throws Exception {
        setId(id);
        factory.loadDivisionById(id, this);
    }

    private int conferenceId;

    private List<Team> teamList;

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void addDivision(IAddDivisionFactory addDivisionFactory) throws Exception {
        addDivisionFactory.addDivision(this);
    }

    public void loadTeamListByDivisionId(ILoadTeamFactory teamFactory) throws Exception {
        this.teamList = teamFactory.loadTeamListByDivisionId(getId());
    }
}
