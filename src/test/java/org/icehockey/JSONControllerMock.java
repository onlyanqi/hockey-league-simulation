package org.icehockey;

import org.json.simple.JSONObject;

import java.io.File;

public class JSONControllerMock{

    public static JSONObject getJSON(int id) {

        String filePath = new File("").getAbsolutePath();

        JSONObject jsonObject;
        if(id == 1){
            jsonObject = JSONController.readJSON(filePath+"\\src\\test\\java\\Static\\league.json");
        }else jsonObject = JSONController.readJSON(filePath+"\\src\\test\\java\\Static\\league2.json");


        return jsonObject;
    }

}
