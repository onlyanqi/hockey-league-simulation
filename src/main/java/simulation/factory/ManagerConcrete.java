package simulation.factory;

import db.data.IManagerFactory;
import simulation.model.Manager;

public class ManagerConcrete {

    public Manager newManagerConcrete() {
        return new Manager();
    }

}
