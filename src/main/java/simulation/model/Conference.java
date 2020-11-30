package simulation.model;

import persistance.dao.IConferenceDao;
import persistance.dao.IDivisionDao;
import persistance.serializers.ModelsForDeserialization.model.Division;
import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

import java.util.ArrayList;
import java.util.List;

public class Conference extends SharedAttributes implements IConference {

    private int leagueId;
    private List<IDivision> divisionList = new ArrayList<>();

    public Conference() {
        setId(System.identityHashCode(this));
    }

    public Conference(int id) {
        setId(id);
    }

    public Conference(int id, IConferenceDao factory) throws Exception {
        setId(id);
        factory.loadConferenceById(id, this);
    }

    public Conference(persistance.serializers.ModelsForDeserialization.model.Conference conferenceFromDeserialization) {
        IHockeyContext hockeyContextFactory = HockeyContext.getInstance();
        IModelFactory modelFactory = hockeyContextFactory.getModelFactory();
        leagueId = conferenceFromDeserialization.leagueId;
        for (Division division : conferenceFromDeserialization.divisionList) {
            this.divisionList.add(modelFactory.createDivisionFromDeserialization(division));
        }
        this.setName(conferenceFromDeserialization.name);
        this.setId(conferenceFromDeserialization.id);
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
        addConferenceFactory.addConference(this);
    }

    public void loadDivisionListByConferenceId(IDivisionDao divisionDao) throws Exception {
        this.divisionList = divisionDao.loadDivisionListByConferenceId(getId());
    }

}
