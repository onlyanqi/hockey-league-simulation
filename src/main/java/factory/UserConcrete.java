package factory;

import dao.AddTeamDao;
import dao.AddUserDao;
import dao.LoadTeamDao;
import dao.LoadUserDao;
import data.IAddTeamFactory;
import data.IAddUserFactory;
import data.ILoadTeamFactory;
import data.ILoadUserFactory;
import model.Team;
import model.User;

public class UserConcrete {

    public User newUser(){
        return new User();
    }

    public User newUserByName(String name, ILoadUserFactory loadUserFactory) throws Exception {
        return new User(name, loadUserFactory);
    }

    public ILoadUserFactory newLoadUserFactory(){
        return new LoadUserDao();
    }

    public IAddUserFactory newAddUserFactory(){
        return new AddUserDao();
    }

}
