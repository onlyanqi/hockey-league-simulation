package simulation.mock;

import simulation.dao.IConferenceDao;
import simulation.dao.IDivisionDao;
import simulation.dao.ITeamDao;
import simulation.factory.HockeyContextConcreteMock;
import simulation.factory.IDivisionFactory;
import simulation.factory.IHockeyContextFactory;
import simulation.factory.ITeamFactory;
import simulation.model.IDivision;
import simulation.model.ITeam;
import simulation.state.IHockeyContext;

import java.util.ArrayList;
import java.util.List;

public class DivisionMock implements IDivisionDao {

    private IHockeyContextFactory hockeyContextFactory;
    private IHockeyContext hockeyContext;
    private ITeamFactory teamFactory;
    private ITeamDao teamDao;
    private IDivisionFactory divisionFactory;

    public DivisionMock(){
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        teamFactory = hockeyContext.getTeamFactory();
        teamDao = teamFactory.newTeamDao();
        divisionFactory = hockeyContext.getDivisionFactory();
    }

    public List<ITeam> formTeamList() throws Exception {
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = teamFactory.newTeamWithIdDao(1, teamDao);
        teamList.add(team);

        team = teamFactory.newTeamWithIdDao(3, teamDao);
        teamList.add(team);

        return teamList;
    }

    public List<ITeam> formCreateTeamTeamList() throws Exception {
        List<ITeam> teamList = new ArrayList<>();
        ITeam team = teamFactory.newTeamWithIdDao(1, teamDao);
        teamList.add(team);
        return teamList;

    }

    @Override
    public int addDivision(IDivision division) throws Exception {
        division = divisionFactory.newDivisionWithId(1);
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
        IDivision division = divisionFactory.newDivision();
        division.setName("Division1");
        division.setConferenceId(1);
        division.setTeamList(formTeamList());
        return division;
    }

    @Override
    public List<IDivision> loadDivisionListByConferenceId(int conferenceId) throws Exception {
        IConferenceDao conferenceDao = hockeyContext.getConferenceFactory().newConferenceDao();
        return conferenceDao.formDivisionList();
    }

}
