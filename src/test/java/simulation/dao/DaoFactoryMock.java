package simulation.dao;

import persistance.dao.*;
import simulation.mock.*;

public class DaoFactoryMock implements IDaoFactory {

    private static DaoFactoryMock daoFactoryMock;

    private DaoFactoryMock() {
    }

    public static DaoFactoryMock getInstance() {
        if (null == daoFactoryMock) {
            daoFactoryMock = new DaoFactoryMock();
        }
        return daoFactoryMock;
    }

    @Override
    public IAgingDao createAgingDao() {
        return new AgingMock();
    }

    @Override
    public IConferenceDao createConferenceDao() {
        return new ConferenceMock();
    }

    @Override
    public IDivisionDao createDivisionDao() {
        return new DivisionMock();
    }

    public ILeagueDao createLeagueDao() {
        return new LeagueMock();
    }

    @Override
    public IPlayerDao createPlayerDao() {
        return new PlayerMock();
    }

    public ITeamDao createTeamDao() {
        return new TeamMock();
    }

    public ITradingDao createTradingDao() {
        return new TradingMock();
    }

    public ITradeOfferDao createTradeOfferDao() {
        return new TradeOfferMock();
    }

    public IUserDao createUserDao() {
        return new UserMock();
    }

    public IFreeAgentDao createFreeAgentDao() {
        return new FreeAgentMock();
    }

    public ISeasonDao createSeasonDao() {
        return new SeasonMock();
    }
}
