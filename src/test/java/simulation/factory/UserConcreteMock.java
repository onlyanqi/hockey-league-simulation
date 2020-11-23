package simulation.factory;

import db.data.IUserDao;
import simulation.factory.IUserFactory;
import simulation.mock.UserMock;
import simulation.model.IUser;
import simulation.model.User;

public class UserConcreteMock implements IUserFactory {

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
        return new UserMock();
    }

}
