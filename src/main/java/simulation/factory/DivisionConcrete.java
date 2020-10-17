package simulation.factory;

import db.dao.DivisionDao;
import db.data.IDivisionFactory;
import simulation.model.Division;

public class DivisionConcrete {

    public Division newDivision(){
        return new Division();
    }

    public IDivisionFactory newLoadDivisionFactory(){
        return new DivisionDao();
    }

    public IDivisionFactory newAddDivisionFactory(){
        return new DivisionDao();
    }

}
