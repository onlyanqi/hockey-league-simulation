package simulation.factory;

import simulation.model.IManager;
import simulation.model.Manager;

public interface IManagerFactory {

    IManager newManagerConcrete();

}
