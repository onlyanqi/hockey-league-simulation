package org.icehockey;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONController {

    public static JSONObject readJSON(String filePath){

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath))
        {
            JSONObject leagueJSON = (JSONObject)jsonParser.parse(reader);
            return leagueJSON;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Imported JSON is not valid");
            e.printStackTrace();
            System.exit(1);

        }
        return null;
    }
}
