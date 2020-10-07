package dao;

import common.Constants;
import data.IUserFactory;
import model.User;

import java.sql.ResultSet;
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
    public void loadUserByName(int id, User user) throws Exception{
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadUserByName);
            callDB.setInputParameterString(1, user.getName());
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    user.setId(rs.getInt(2));
                    user.setPassword(rs.getString(3));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    /*public static void main(String[] args){

        UserDao userDao = new UserDao();

        try {
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setName("Name" + i);
                user.setPassword("Password"+i);
                userDao.addUser(user);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/

}
