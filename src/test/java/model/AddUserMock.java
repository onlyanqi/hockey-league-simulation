package model;

import data.IAddUserFactory;
import data.ILoadUserFactory;

public class AddUserMock implements IAddUserFactory {

    @Override
    public long addUser(User user) throws Exception {
        user = new User(1);
        return user.getId();
    }
}
