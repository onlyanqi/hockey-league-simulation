package simulation.model;

import db.data.IDivisionDao;
import db.data.ITeamDao;
import java.util.ArrayList;
import java.util.List;

public class Division extends SharedAttributes implements IDivision {

    private int conferenceId;
    private List<ITeam> teamList;

    public Division() {
        setId(System.identityHashCode(this));
    }

    public Division(int id) {
        setId(id);
    }

    public Division(int id, IDivisionDao factory) throws Exception {
        if (factory == null) {
            return;
        }
        setId(id);
        factory.loadDivisionById(id, this);
    }

    public List<ITeam> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<ITeam> teamList) {
        if (teamList == null) {
            return;
        }
        this.teamList = teamList;
    }

    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void addDivision(IDivisionDao addDivisionFactory) throws Exception {
        if (addDivisionFactory == null) {
            return;
        }
        addDivisionFactory.addDivision(this);
    }

    public void loadTeamListByDivisionId(ITeamDao teamFactory) throws Exception {
        if (teamFactory == null) {
            return;
        }
        this.teamList = teamFactory.loadTeamListByDivisionId(getId());
    }

    public List<String> getTeamNameList() {
        List<String> teamNameList = new ArrayList<>();
        for (ITeam team : this.getTeamList()) {
            teamNameList.add(team.getName().toLowerCase());
        }
        return teamNameList;
    }

}
