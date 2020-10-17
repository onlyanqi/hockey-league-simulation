/*
package dao;

import common.Constants;
import simulation.data.ILoadUserFactory;
import simulation.model.User;

public class LoadUserDao implements ILoadUserFactory {

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
*/
