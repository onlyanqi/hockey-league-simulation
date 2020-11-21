package db.dao;

import db.data.IDivisionDao;
import simulation.model.IDivision;

import java.util.List;

public class DivisionDao extends DBExceptionLog implements IDivisionDao {

    @Override
    public int addDivision(IDivision division) throws Exception {
        return 0;
    }

    @Override
    public void loadDivisionById(int id, IDivision division) throws Exception {

    }

    @Override
    public IDivision loadDivisionByName(String divisionName) throws Exception {
        return null;
    }

    @Override
    public List<IDivision> loadDivisionListByConferenceId(int conferenceId) throws Exception {
        return null;
    }

}
