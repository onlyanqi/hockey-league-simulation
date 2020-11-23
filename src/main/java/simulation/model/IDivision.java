package simulation.model;

import db.data.IDivisionDao;
import db.data.ITeamDao;

import java.util.List;

public interface IDivision{

    List<ITeam> getTeamList();

    void setTeamList(List<ITeam> teamList);

    int getConferenceId();

    void setConferenceId(int conferenceId);

    void addDivision(IDivisionDao addDivisionFactory) throws Exception;

    void loadTeamListByDivisionId(ITeamDao teamFactory) throws Exception;

    List<String> getTeamNameList();

    String getName();

    void setName(String name);

    int getId();

    void setId(int id);

}
