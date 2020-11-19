package simulation.model;

import db.data.IConferenceFactory;
import db.data.IDivisionFactory;

import java.util.ArrayList;
import java.util.List;

public class Conference extends SharedAttributes {

    private int leagueId;
    private List<Division> divisionList;

    public Conference() {
        setId(System.identityHashCode(this));
    }

    public Conference(int id) {
        setId(id);
    }

    public Conference(int id, IConferenceFactory factory) throws Exception {
        if (factory == null) {
            return;
        }
        setId(id);
        factory.loadConferenceById(id, this);
    }


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
        if (divisionList == null) {
            return;
        }
        this.divisionList = divisionList;
    }

    public List<String> getDivisionNameList() {
        List<String> divisionNameList = new ArrayList<>();
        for (Division division : this.getDivisionList()) {
            divisionNameList.add(division.getName().toLowerCase());
        }
        return divisionNameList;
    }

    public Division getDivisionFromListByName(String divisionName) {
        Division foundDivision = null;
        for (Division division : divisionList) {
            if (division.getName().toLowerCase().equals(divisionName.toLowerCase())) {
                foundDivision = division;
                break;
            }
        }
        return foundDivision;
    }

    public void addConference(IConferenceFactory addConferenceFactory) throws Exception {
        if (addConferenceFactory == null) {
            return;
        }
        addConferenceFactory.addConference(this);
    }

    public void loadDivisionListByConferenceId(IDivisionFactory loadDivisionFactory) throws Exception {
        if (loadDivisionFactory == null) {
            return;
        }
        this.divisionList = loadDivisionFactory.loadDivisionListByConferenceId(getId());
    }

}
