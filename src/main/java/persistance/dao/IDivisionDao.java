package persistance.dao;

import simulation.model.IDivision;

import java.util.List;

public interface IDivisionDao {

    int addDivision(IDivision division) throws Exception;

    void loadDivisionById(int id, IDivision division) throws Exception;

    IDivision loadDivisionByName(String divisionName) throws Exception;

    List<IDivision> loadDivisionListByConferenceId(int conferenceId) throws Exception;
}
