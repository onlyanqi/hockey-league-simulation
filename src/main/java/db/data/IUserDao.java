package db.data;

import simulation.model.IUser;

public interface IUserDao {

    long addUser(IUser user) throws Exception;

    void loadUserById(int id, IUser user) throws Exception;

    void loadUserByName(String userName, IUser user) throws Exception;
}
