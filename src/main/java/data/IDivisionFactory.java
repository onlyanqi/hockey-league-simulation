package data;

import model.Conference;
import model.Division;

public interface IDivisionFactory {

    int addDivision(Division division) throws Exception;
    void loadDivisionByName(int id, Division division) throws Exception;

}
