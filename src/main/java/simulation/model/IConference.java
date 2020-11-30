package simulation.model;

import persistance.dao.IConferenceDao;
import persistance.dao.IDivisionDao;

import java.util.List;

public interface IConference {

    int getLeagueId();

    void setLeagueId(int leagueId);

    List<IDivision> getDivisionList();

    void setDivisionList(List<IDivision> divisionList);

    List<String> getDivisionNameList();

    IDivision getDivisionFromListByName(String divisionName);

    void addConference(IConferenceDao addConferenceFactory) throws Exception;

    void loadDivisionListByConferenceId(IDivisionDao loadDivisionFactory) throws Exception;

    String getName();

    void setName(String name);

    int getId();

    void setId(int id);

}
