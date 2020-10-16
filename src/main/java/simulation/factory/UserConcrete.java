package factory;

import dao.AddUserDao;
import dao.LoadUserDao;
import simulation.data.IAddUserFactory;
import simulation.data.ILoadUserFactory;
import simulation.model.User;

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
