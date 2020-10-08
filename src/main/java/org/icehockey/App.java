package org.icehockey;

import dao.LoadUserDao;
import data.ILoadUserFactory;
import model.HockeyContext;
import model.User;
import org.json.simple.JSONObject;
import util.CommonUtil;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {

        String filePath = "";
        JSONObject jsonFromInput = null;


        String userName = GetInput.getUserInput("Please enter username:");

        CommonUtil util = new CommonUtil();

        if(userName != null && util.isNotEmpty(userName)) {
            ILoadUserFactory factory = new LoadUserDao();
            User user = factory.loadUserByName(userName);

            filePath = GetInput.getUserInput("Please provide location of JSON file. If not please press ENTER");

            if (filePath != null) {
                jsonFromInput = JSONController.readJSON(filePath);
            }

            HockeyContext context = new HockeyContext(user);
            context.startAction(jsonFromInput);
        }
    }




}
