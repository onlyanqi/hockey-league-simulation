package simulation.factory;

import db.dao.ManagerDao;
import db.data.IManagerFactory;
import simulation.model.Manager;

public class ManagerConcrete {

    public Manager newManagerConcrete() {
        return new Manager();
    }

    public IManagerFactory newManagerFactory() {
        return new ManagerDao();
    }
}
