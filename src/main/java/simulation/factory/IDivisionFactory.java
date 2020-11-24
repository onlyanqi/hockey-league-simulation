package simulation.factory;

import simulation.dao.IDivisionDao;
import simulation.model.IDivision;

public interface IDivisionFactory {

    IDivision newDivision();

    IDivisionDao newDivisionDao();

    IDivision newDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception;

    IDivision newDivisionWithId(int id);
}
