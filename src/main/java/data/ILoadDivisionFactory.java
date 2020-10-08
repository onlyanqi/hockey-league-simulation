package data;

import model.Conference;
import model.Division;

public interface ILoadDivisionFactory {

    void loadDivisionById(int id, Division division) throws Exception;
    Division loadDivisionByName(String divisionName) throws Exception;

}
