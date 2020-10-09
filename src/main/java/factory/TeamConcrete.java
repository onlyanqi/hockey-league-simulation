package factory;


import dao.AddTeamDao;

import dao.LoadTeamDao;

import data.IAddTeamFactory;

import data.ILoadTeamFactory;

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
