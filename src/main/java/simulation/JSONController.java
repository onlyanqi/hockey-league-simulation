package simulation;


import com.google.gson.Gson;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class JSONController {

    public static JSONObject readJSON(String filePath){

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath))
        {
            JSONObject leagueJSON = (JSONObject)jsonParser.parse(reader);
            return leagueJSON;
        } catch (FileNotFoundException e) {
            System.out.println("File not found. "+e);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("File read failed. "+e);
            System.exit(1);
        } catch (ParseException e) {
            System.out.println("Imported file is not valid. Please make sure it is JSON format or if all fields are either boolean,string,array,object");
            System.exit(1);

        }
        return null;
    }

    public static boolean invalidJSON(String filePath) throws Exception {
        String JSON = readFileAsString(filePath);
        Gson gson = new Gson();
        try {
            gson.fromJson(JSON, Object.class);
            return false;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return true;
        }
    }

    public static String readFileAsString(String filePath)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

}
