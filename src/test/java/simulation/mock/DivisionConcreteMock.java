package simulation.mock;

import simulation.dao.IDivisionDao;
import simulation.factory.IDivisionFactory;
import simulation.model.Division;
import simulation.model.IDivision;

public class DivisionConcreteMock implements IDivisionFactory {

    public IDivision newDivision() {
        return new Division();
    }

    public IDivisionDao newDivisionDao(){
        return new DivisionMock();
    }

    public IDivision newDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception {
        return new Division(id, divisionDao);
    }

    public IDivision newDivisionWithId(int id){
        return new Division(id);
    }
}
