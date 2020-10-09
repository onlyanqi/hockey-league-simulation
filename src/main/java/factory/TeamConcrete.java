package factory;

import dao.AddPlayerDao;
import dao.AddTeamDao;
import dao.LoadPlayerDao;
import dao.LoadTeamDao;
import data.IAddPlayerFactory;
import data.IAddTeamFactory;
import data.ILoadPlayerFactory;
import data.ILoadTeamFactory;
import model.Player;
import model.Team;

public class TeamConcrete {

    public Team newTeam(){
        return new Team();
    }

    public Team newTeamByName(String name, ILoadTeamFactory loadTeamFactory) throws Exception {
        return new Team(name, loadTeamFactory);
    }

    public ILoadTeamFactory newLoadTeamFactory(){
        return new LoadTeamDao();
    }

    public IAddTeamFactory newAddTeamFactory(){
        return new AddTeamDao();
    }

}
