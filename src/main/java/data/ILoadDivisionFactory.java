package data;

import model.Conference;
import model.Division;

import java.util.List;

public interface ILoadDivisionFactory {

    void loadDivisionById(int id, Division division) throws Exception;
    Division loadDivisionByName(String divisionName) throws Exception;

    List<Division> loadDivisionListByConferenceId(int conferenceId) throws Exception;
}
