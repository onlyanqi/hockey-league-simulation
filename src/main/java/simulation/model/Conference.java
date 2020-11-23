package simulation.model;

import db.data.IConferenceDao;
import db.data.IDivisionDao;
import java.util.ArrayList;
import java.util.List;

public class Conference extends SharedAttributes implements IConference {

    private int leagueId;
    private List<IDivision> divisionList;

    public Conference() {
        setId(System.identityHashCode(this));
    }

    public Conference(int id) {
        setId(id);
    }

    public Conference(int id, IConferenceDao factory) throws Exception {
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

    public List<IDivision> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<IDivision> divisionList) {
        if (divisionList == null) {
            return;
        }
        this.divisionList = divisionList;
    }

    public List<String> getDivisionNameList() {
        List<String> divisionNameList = new ArrayList<>();
        for (IDivision division : this.getDivisionList()) {
            divisionNameList.add(division.getName().toLowerCase());
        }
        return divisionNameList;
    }

    public IDivision getDivisionFromListByName(String divisionName) {
        IDivision foundDivision = null;
        for (IDivision division : divisionList) {
            if (division.getName().toLowerCase().equals(divisionName.toLowerCase())) {
                foundDivision = division;
                break;
            }
        }
        return foundDivision;
    }

    public void addConference(IConferenceDao addConferenceFactory) throws Exception {
        if (addConferenceFactory == null) {
            return;
        }
        addConferenceFactory.addConference(this);
    }

    public void loadDivisionListByConferenceId(IDivisionDao divisionDao) throws Exception {
        if (divisionDao == null) {
            return;
        }
        this.divisionList = divisionDao.loadDivisionListByConferenceId(getId());
    }

}
