package db.dao;

import db.data.IUserDao;
import simulation.model.IUser;

public class UserDao extends DBExceptionLog implements IUserDao {


    @Override
    public long addUser(IUser user) throws Exception {
        return 0;
    }

    @Override
    public void loadUserById(int id, IUser user) throws Exception {

    }

    @Override
    public void loadUserByName(String userName, IUser user) throws Exception {

    }
}
