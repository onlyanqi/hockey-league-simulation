package simulation.data;

import simulation.model.Division;

import java.util.List;

public interface ILoadDivisionFactory {

    void loadDivisionById(int id, Division division) throws Exception;
    Division loadDivisionByName(String divisionName) throws Exception;

    List<Division> loadDivisionListByConferenceId(int conferenceId) throws Exception;
}
