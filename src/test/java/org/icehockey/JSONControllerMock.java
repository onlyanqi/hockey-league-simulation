package org.icehockey;

import org.json.simple.JSONObject;

public class JSONControllerMock{

    public static JSONObject getJSON() {

        JSONObject jsonObject;
        jsonObject = JSONController.readJSON("/Static/league.json");

        return jsonObject;
    }

}
