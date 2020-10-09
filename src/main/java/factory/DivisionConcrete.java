package factory;

import dao.AddConferenceDao;
import dao.AddDivisionDao;
import dao.LoadConferenceDao;
import dao.LoadDivisionDao;
import data.IAddConferenceFactory;
import data.IAddDivisionFactory;
import data.ILoadConferenceFactory;
import data.ILoadDivisionFactory;
import model.Conference;
import model.Division;

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
