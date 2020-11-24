package simulation.factory;

import simulation.dao.IUserDao;
import simulation.model.IUser;

public interface IUserFactory {

    IUser newUser();

    IUser newUserByName(String name, IUserDao loadUserFactory) throws Exception;

    IUser newUserWithIdDao(int id, IUserDao userDao) throws Exception;

    IUserDao newUserDao();
}
