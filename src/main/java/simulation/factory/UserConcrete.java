package simulation.factory;

import db.data.IUserFactory;
import simulation.model.User;

public class UserConcrete {

    public User newUser() {
        return new User();
    }

    public User newUserByName(String name, IUserFactory loadUserFactory) throws Exception {
        return new User(name, loadUserFactory);
    }


}
