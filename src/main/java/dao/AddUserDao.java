package dao;

import common.Constants;
import data.IAddUserFactory;
import data.ILoadUserFactory;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddUserDao implements IAddUserFactory {

    @Override
    public long addUser(User user) throws Exception{
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.addUser);
            callDB.setInputParameterString(1, user.getName());
            callDB.setInputParameterString(2, user.getPassword());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            user.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return user.getId();
    }

}
