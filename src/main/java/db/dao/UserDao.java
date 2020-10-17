package db.dao;

import common.Constants;
import db.data.IUserFactory;
import simulation.model.User;

import java.sql.SQLException;

public class UserDao implements IUserFactory {

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

    @Override
    public void loadUserById(int id, User user) throws Exception{
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadUserByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.executeLoad();


            user.setId(callDB.returnOutputParameterInt(2));
            user.setPassword(callDB.returnOutputParameterString(3));

        }catch (Exception e){
            e.printStackTrace();
            throw e;

        } finally {
            callDB.closeConnection();
        }


    }

    @Override
    public void loadUserByName(String userName, User user) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadUserByName);
            callDB.setInputParameterString(1, userName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.executeLoad();
            user.setId(callDB.returnOutputParameterInt(2));
            user.setPassword(callDB.returnOutputParameterString(3));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            callDB.closeConnection();
        }
    }

}
