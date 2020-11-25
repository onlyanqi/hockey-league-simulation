package simulation.mock;

import simulation.dao.IManagerDao;
import simulation.model.IManager;
import simulation.model.Manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerMock implements IManagerDao {

    @Override
    public int addManager(IManager manager) throws Exception {
        manager = new Manager(1);
        return manager.getId();

    }

    @Override
    public void loadManagerById(int managerId, IManager manager) throws Exception {
        switch (managerId) {
            case 1:
                manager.setName("Sam Mathew");
                manager.setLeagueId(1);
                manager.setTeamId(0);
                break;
            case 2:
                manager.setName("Sid Patric");
                manager.setLeagueId(1);
                manager.setTeamId(0);
                break;
            case 3:
                manager.setName("Mark Mark");
                manager.setLeagueId(1);
                manager.setTeamId(0);
                break;
            case 4:
                manager.setName("Ranbir Kapoor");
                manager.setLeagueId(1);
                manager.setTeamId(0);
                break;
            case 5:
                manager.setName("Manager 5");
                manager.setLeagueId(1);
                manager.setTeamId(0);
                break;
            case 6:
                manager.setName("Manager 6");
                manager.setLeagueId(1);
                manager.setTeamId(1);
                break;
        }
    }

    @Override
    public List<IManager> loadFreeManagersByLeagueId(int leagueId) throws Exception {
        List<IManager> managerList = new ArrayList<>();
        IManagerDao managerFactory = new ManagerMock();
        for (int i = 1; i <= 5; i++) {
            Manager manager = new Manager(i, managerFactory);
            managerList.add(manager);
        }
        return managerList;
    }

    @Override
    public IManager loadManagerByTeamId(int teamId) throws Exception {
        IManager manager = new Manager();
        manager.setTeamId(1);
        manager.setName("Alix Sarty");
        manager.setId(1);
        manager.setLeagueId(1);
        return manager;
    }
}
