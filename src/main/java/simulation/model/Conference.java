package simulation.model;

import simulation.data.IAddConferenceFactory;
import simulation.data.ILoadConferenceFactory;
import simulation.data.ILoadDivisionFactory;

import java.util.List;

public class Conference extends ParentObj{

    public Conference(){ }

    public Conference(int id){
        setId(id);
    }

    public Conference(int id, ILoadConferenceFactory factory) throws Exception {
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

    public void addConference(IAddConferenceFactory addConferenceFactory) throws Exception {
        addConferenceFactory.addConference(this);
    }

    public void loadDivisionListByConferenceId(ILoadDivisionFactory loadDivisionFactory) throws Exception {
        this.divisionList = loadDivisionFactory.loadDivisionListByConferenceId(getId());
    }

}
