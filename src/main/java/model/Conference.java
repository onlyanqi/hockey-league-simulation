package model;

import data.IConferenceFactory;

import java.util.List;

public class Conference extends ParentObj{

    public Conference(){ }

    public Conference(long id){
        setId(id);
    }

    public Conference(long id, IConferenceFactory factory){
        setId(id);
        factory.loadConference(id, this);
    }

    private long leagueId;

    private List<Division> divisionList;

    public long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(long leagueId) {
        this.leagueId = leagueId;
    }

    public List<Division> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<Division> divisionList) {
        this.divisionList = divisionList;
    }
}
