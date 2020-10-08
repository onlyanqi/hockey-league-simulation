package data;

import model.User;

public interface ILoadUserFactory {

    void loadUserById(int id, User user) throws Exception;
    User loadUserByName(String userName) throws Exception;

}
