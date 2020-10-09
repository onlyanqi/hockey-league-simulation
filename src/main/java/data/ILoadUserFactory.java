package data;

import model.User;

public interface ILoadUserFactory {

    void loadUserById(int id, User user) throws Exception;
    void loadUserByName(String userName, User user) throws Exception;

}
