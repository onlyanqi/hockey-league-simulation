package data;

import model.League;
import model.User;

public interface IUserFactory {

    void loadUserByName(long id, User user) throws Exception;
    long addUser(User user) throws Exception;

    }
