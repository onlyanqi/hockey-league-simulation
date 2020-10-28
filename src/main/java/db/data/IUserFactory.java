package db.data;

import simulation.model.User;

public interface IUserFactory {

    long addUser(User user) throws Exception;

    void loadUserById(int id, User user) throws Exception;

    void loadUserByName(String userName, User user) throws Exception;
}
