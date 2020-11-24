package simulation.factory;

import simulation.dao.IAgingDao;
import simulation.model.IAging;

public interface IAgingFactory {

    IAging newAging();

    IAgingDao newAgingDao();

}
