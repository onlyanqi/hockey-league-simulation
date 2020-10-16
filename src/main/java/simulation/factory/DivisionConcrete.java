package factory;

import dao.AddDivisionDao;
import dao.LoadDivisionDao;
import simulation.data.IAddDivisionFactory;
import simulation.data.ILoadDivisionFactory;
import simulation.model.Division;

public class DivisionConcrete {

    public Division newDivision(){
        return new Division();
    }

    public ILoadDivisionFactory newLoadDivisionFactory(){
        return new LoadDivisionDao();
    }

    public IAddDivisionFactory newAddDivisionFactory(){
        return new AddDivisionDao();
    }

}
