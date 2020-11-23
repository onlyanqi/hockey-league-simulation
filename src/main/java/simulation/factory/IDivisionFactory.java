package simulation.factory;

import db.data.IDivisionDao;
import simulation.model.Division;
import simulation.model.IDivision;

public interface IDivisionFactory {

    IDivision newDivision();

    IDivisionDao newDivisionDao();

    IDivision newDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception;

    IDivision newDivisionWithId(int id);
}
