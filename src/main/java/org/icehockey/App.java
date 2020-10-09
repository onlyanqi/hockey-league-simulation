package org.icehockey;

import dao.AddUserDao;
import dao.LoadUserDao;
import data.IAddUserFactory;
import data.ILoadUserFactory;
import factory.UserConcrete;
import model.HockeyContext;
import model.User;
import org.json.simple.JSONObject;
import util.CommonUtil;

import java.io.FileNotFoundException;

import static common.Constants.addUser;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) {

        String filePath = "";
        JSONObject jsonFromInput = null;

        String userName = GetInput.getUserInput("Please enter username");

        CommonUtil util = new CommonUtil();

        try {
            if (userName != null && util.isNotEmpty(userName)) {
                UserConcrete userConcrete = new UserConcrete();
                ILoadUserFactory factory = userConcrete.newLoadUserFactory();
                User user = userConcrete.newUserByName(userName, factory);

                user.setName(userName);
                if (user.getId() == 0) {
                    String password = GetInput.getUserInput("Please enter password to register yourself");
                    user.setPassword(password);
                    addUser(user);
                }

                filePath = GetInput.getUserInput("Please provide location of JSON file. If not please press ENTER");

                if (filePath != null && filePath.length() != 0) {
                    jsonFromInput = JSONController.readJSON(filePath);
                }
                HockeyContext context = new HockeyContext(user);
                context.startAction(jsonFromInput);

            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not found. " + e);
        } catch (Exception e) {
            System.out.println("System faced unexpected exception. Please contact team. " + e);
        }


    }

    private static void addUser(User user) throws Exception {
        UserConcrete userConcrete = new UserConcrete();
        IAddUserFactory addUserFactory = userConcrete.newAddUserFactory();
        user.addUser(addUserFactory);
    }






}
