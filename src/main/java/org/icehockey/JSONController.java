package org.icehockey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
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

    private static InputStream inputStreamFromClasspath(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
    public static void validateJSON(String filePath) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

        try (
                InputStream jsonStream = new FileInputStream(filePath);
                InputStream schemaStream = inputStreamFromClasspath("json-import-schema.json")
        ){
            JsonNode json = objectMapper.readTree(jsonStream);
            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            Set<ValidationMessage> validationResult = schema.validate(json);

            // print validation errors
            if (validationResult.isEmpty()) {
                System.out.println("no validation errors :-)");
            } else {
                for(ValidationMessage a : validationResult){
                    System.out.println(a.getMessage());
                }
            }
        }

    }

}
