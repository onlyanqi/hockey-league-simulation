package simulation.factory;

import simulation.model.IManager;
import simulation.model.Manager;

public class ManagerConcrete implements IManagerFactory{

    public IManager newManagerConcrete() {
        return new Manager();
    }

}
