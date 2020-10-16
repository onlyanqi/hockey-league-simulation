package model;

import simulation.data.IAddUserFactory;
import simulation.model.User;

public class AddUserMock implements IAddUserFactory {

    @Override
    public long addUser(User user) throws Exception {
        user = new User(1);
        return user.getId();
    }
}
