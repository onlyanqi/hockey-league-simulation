package data;

import model.User;

public interface IUserFactory {

    void loadUserByName(int id, User user) throws Exception;

    long addUser(User user) throws Exception;

    User loadUserByName(String userName) throws Exception;

}
