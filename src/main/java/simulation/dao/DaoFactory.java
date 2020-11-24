package simulation.dao;

public class DaoFactory implements IDaoFactory {

    public IAgingDao newAgingDao(){
        return new AgingDao();
    }

    public IConferenceDao newConferenceDao(){
        return new ConferenceDao();
    }

    public IDivisionDao newDivisionDao(){
        return new DivisionDao();
    }

}
