package simulation.factory;

import db.dao.AgingDao;
import db.data.IAgingDao;
import simulation.model.Aging;
import simulation.model.IAging;

public class AgingConcrete implements IAgingFactory {

    public IAging newAging() {
        return new Aging();
    }

    public IAgingDao newAgingDao(){
        return new AgingDao();
    }
}