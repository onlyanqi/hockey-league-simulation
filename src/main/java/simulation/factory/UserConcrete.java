package simulation.factory;

import db.data.IUserDao;
import simulation.model.IUser;
import simulation.model.User;

public class UserConcrete implements IUserFactory{

    public IUser newUser() {
        return new User();
    }

    public IUser newUserByName(String name, IUserDao loadUserFactory) throws Exception {
        return new User(name, loadUserFactory);
    }


}
