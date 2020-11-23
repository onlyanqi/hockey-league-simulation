package simulation.factory;

import db.data.IAgingDao;
import simulation.model.IAging;

public interface IAgingFactory {

    IAging newAging();

    IAgingDao newAgingDao();

}
