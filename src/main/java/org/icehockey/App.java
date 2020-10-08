package org.icehockey;

import dao.AddUserDao;
import dao.LoadUserDao;
import data.IAddUserFactory;
import data.ILoadUserFactory;
import model.HockeyContext;
import model.User;
import org.json.simple.JSONObject;
import util.CommonUtil;

import static common.Constants.addUser;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {

        String filePath = "";
        JSONObject jsonFromInput = null;


        String userName = GetInput.getUserInput("Please enter username");

        CommonUtil util = new CommonUtil();

        if(userName != null && util.isNotEmpty(userName)) {
            ILoadUserFactory factory = new LoadUserDao();
            User user = factory.loadUserByName(userName);
            user.setName(userName);
            if(user.getId()==0){
                String password = GetInput.getUserInput("Please enter password to register yourself");
                user.setPassword(password);
                addUser(user);
            }

            filePath = GetInput.getUserInput("Please provide location of JSON file. If not please press ENTER");

            if (filePath != null && filePath.length()!=0 ) {
                jsonFromInput = JSONController.readJSON(filePath);
            }
            HockeyContext context = new HockeyContext(user);
            context.startAction(jsonFromInput);
        }
    }

    private static void addUser(User user) throws Exception {
        IAddUserFactory iAddUserFactory = new AddUserDao();
        iAddUserFactory.addUser(user);
    }






}
