package simulation.dao;

public class DaoFactory implements IDaoFactory {

    private static IDaoFactory daoFactory;

    private DaoFactory(){}

    public static IDaoFactory getInstance(){
        if(null == daoFactory){
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public IAgingDao newAgingDao(){
        return new AgingDao();
    }

    public IConferenceDao newConferenceDao(){
        return new ConferenceDao();
    }

    public IDivisionDao newDivisionDao(){
        return new DivisionDao();
    }

    public ILeagueDao newLeagueDao(){
        return new LeagueDao();
    }

    public IPlayerDao newPlayerDao(){
        return new PlayerDao();
    }

    public ITradeOfferDao newTradeOfferDao(){
        return new TradeOfferDao();
    }

    public ITeamDao newTeamDao(){
        return new TeamDao();
    }

    public ITradingDao newTradingDao(){
        return new TradingDao();
    }

    public IUserDao newUserDao(){
        return new UserDao();
    }
}
