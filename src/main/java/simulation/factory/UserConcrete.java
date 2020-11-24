package simulation.factory;

import simulation.dao.UserDao;
import simulation.dao.IUserDao;
import simulation.model.IUser;
import simulation.model.User;

public class UserConcrete implements IUserFactory{

    public IUser newUser() {
        return new User();
    }

    public IUser newUserByName(String name, IUserDao loadUserFactory) throws Exception {
        return new User(name, loadUserFactory);
    }

    public IUser newUserWithIdDao(int id, IUserDao userDao) throws Exception {
        return new User(id, userDao);
    }

    public IUserDao newUserDao(){
        return new UserDao();
    }

}
