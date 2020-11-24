package simulation.model;

import simulation.dao.IConferenceDao;
import simulation.dao.IDivisionDao;

public class ModelFactory implements IModelFactory{

    private static IModelFactory modelFactory;

    private ModelFactory(){}

    public static IModelFactory getInstance(){
        if(null == modelFactory){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    @Override
    public IGameSchedule newGameSchedule() {
        return new GameSchedule();
    }

    public IGameResolver newGameResolver() {
        return new GameResolver();
    }

    public IAging newAging() {
        return new Aging();
    }

    @Override
    public ICoach newCoach() {
        return new Coach();
    }

    @Override
    public ICoach newCoachWithCoach(ICoach coach) {
        return new Coach(coach);
    }

    public IConference newConference() {
        return new Conference();
    }

    public IConference newConferenceWithId(int id){
        return new Conference(id);
    }

    public IConference newConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception {
        return new Conference(id, conferenceDao);
    }

    public IDivision newDivision() {
        return new Division();
    }

    public IDivision newDivisionWithIdDao(int id, IDivisionDao divisionDao) throws Exception {
        return new Division(id, divisionDao);
    }

    public IDivision newDivisionWithId(int id){
        return new Division(id);
    }

    public INHLEvents newEvents() {
        return new NHLEvents();
    }

    public IFreeAgent newFreeAgent() {
        return new FreeAgent();
    }

    @Override
    public IGame newGame() {
        return new Game();
    }

    public IGamePlayConfig newGamePlayConfig() {
        return new GamePlayConfig();
    }

}
