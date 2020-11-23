package simulation.factory;

import db.data.IUserDao;
import simulation.model.IUser;
import simulation.model.User;

public interface IUserFactory {

    IUser newUser();

    IUser newUserByName(String name, IUserDao loadUserFactory) throws Exception;

    IUser newUserWithIdDao(int id, IUserDao userDao) throws Exception;

    IUserDao newUserDao();
}
