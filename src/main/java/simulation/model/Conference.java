package simulation.model;

import db.data.IConferenceFactory;
import db.data.IDivisionFactory;

import java.util.List;

public class Conference extends ParentObj{

    public Conference(){ }

    public Conference(int id){
        setId(id);
    }

    public Conference(int id, IConferenceFactory factory) throws Exception {
        setId(id);
        factory.loadConferenceByName(id, this);
    }

    private int leagueId;

    private List<Division> divisionList;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public List<Division> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<Division> divisionList) {
        this.divisionList = divisionList;
    }

    public void addConference(IConferenceFactory addConferenceFactory) throws Exception {
        addConferenceFactory.addConference(this);
    }

    public void loadDivisionListByConferenceId(IDivisionFactory loadDivisionFactory) throws Exception {
        this.divisionList = loadDivisionFactory.loadDivisionListByConferenceId(getId());
    }

}
