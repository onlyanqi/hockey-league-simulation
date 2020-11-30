package simulation.mock;

import persistance.dao.IConferenceDao;
import persistance.dao.IDaoFactory;
import persistance.dao.IDivisionDao;
import persistance.dao.ITeamDao;
import simulation.factory.HockeyContextConcreteMock;
import simulation.factory.IHockeyContextFactory;
import simulation.model.IDivision;
import simulation.model.IModelFactory;
import simulation.model.ITeam;
import simulation.state.IHockeyContext;

import java.util.ArrayList;
import java.util.List;

public class DivisionMock implements IDivisionDao {

    private final IHockeyContextFactory hockeyContextFactory;
    private final IHockeyContext hockeyContext;
    private final IModelFactory modelFactory;
    private final IDaoFactory daoFactory;
    private final ITeamDao teamDao;

    public DivisionMock() {
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        modelFactory = hockeyContext.getModelFactory();
        daoFactory = hockeyContext.getDaoFactory();
        teamDao = daoFactory.createTeamDao();
    }

    public List<ITeam> formTeamList() throws Exception {
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = modelFactory.createTeamWithIdDao(1, teamDao);
        teamList.add(team);

        team = modelFactory.createTeamWithIdDao(3, teamDao);
        teamList.add(team);

        return teamList;
    }

    public List<ITeam> formCreateTeamTeamList() throws Exception {
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = modelFactory.createTeamWithIdDao(1, teamDao);
        teamList.add(team);
        return teamList;

    }

    @Override
    public int addDivision(IDivision division) throws Exception {
        division = modelFactory.createDivisionWithId(1);
        return division.getId();
    }

    @Override
    public void loadDivisionById(int id, IDivision division) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                division.setName("Division1");
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;

            case 2:
                division.setName(null);
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;

            case 3:
                division.setName("Invalid Date");
                division.setConferenceId(1);
                division.setTeamList(formTeamList());
                break;
            case 4:
                division.setName("Division4");
                division.setConferenceId(1);
                division.setTeamList(formCreateTeamTeamList());
        }

    }

    @Override
    public IDivision loadDivisionByName(String divisionName) throws Exception {
        IDivision division = modelFactory.createDivision();
        division.setName("Division1");
        division.setConferenceId(1);
        division.setTeamList(formTeamList());
        return division;
    }

    @Override
    public List<IDivision> loadDivisionListByConferenceId(int conferenceId) throws Exception {
        IConferenceDao conferenceDao = hockeyContext.getDaoFactory().createConferenceDao();
        return conferenceDao.formDivisionList();
    }

}
