package simulation.factory;

import simulation.dao.DaoFactoryMock;
import simulation.dao.IDaoFactory;
import simulation.model.IModelFactory;
import simulation.model.ModelFactory;
import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

public class HockeyContextConcreteMock implements IHockeyContextFactory {

    private static IHockeyContextFactory hockeyContextConcrete;

    private HockeyContextConcreteMock() {
    }

    public static IHockeyContextFactory getInstance() {
        if (hockeyContextConcrete == null) {
            return new HockeyContextConcreteMock();
        }
        return hockeyContextConcrete;
    }

    public IHockeyContext newHockeyContext() {
        return createHockeyContext();
    }

    private IHockeyContext createHockeyContext() {
        IHockeyContext hockeyContext = HockeyContext.getInstance();

        IModelFactory modelFactory = ModelFactory.getInstance();
        IDaoFactory daoFactory = DaoFactoryMock.getInstance();

        hockeyContext.setModelFactory(modelFactory);
        hockeyContext.setDaoFactory(daoFactory);

        return hockeyContext;
    }

}
