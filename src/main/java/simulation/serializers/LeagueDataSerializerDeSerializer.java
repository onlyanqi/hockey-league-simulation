package simulation.serializers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import presentation.ConsoleOutput;
import simulation.model.Aging;
import simulation.model.IAging;
import simulation.model.League;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.io.*;

public class LeagueDataSerializerDeSerializer {

    public static String FILENAME = "JsonOutput.txt";
    public static final String JSONCREATIONERROR = "Json could not be created";
    public static final String DESERIALIZATIONERROR = "Could not deserialize";
    private ConsoleOutput consoleOutput = null;

    public void serialize(League league) {
        if (consoleOutput == null) {
            consoleOutput = ConsoleOutput.getInstance();
        }

        if (league == null) {
            return;
        }

        //Source for creating folder through java program: https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java

        File jsonDir = new File("/JsonFiles");
        if (!jsonDir.exists()){
            jsonDir.mkdirs();
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();

        FileWriter fileWriter = null;
        try {
            FILENAME = "JsonFiles"+"/"+league.getUserCreatedTeamName();
            fileWriter = new FileWriter(FILENAME);
            gson.toJson(league, fileWriter);

        } catch (Exception e) {
            consoleOutput.printMsgToConsole(JSONCREATIONERROR);
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                consoleOutput.printMsgToConsole(JSONCREATIONERROR);
            }

        }
    }

    public League deSerialize(String filename) {
        System.out.println(filename);
        if (consoleOutput == null) {
            consoleOutput = ConsoleOutput.getInstance();
        }
        FileReader fileReader;
        JSONParser jsonParser = new JSONParser();
        League league = null;
        try {
            fileReader = new FileReader(filename);
            Gson gson = new Gson();

            /*GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeHierarchyAdapter(IAging.class, new AgingCreator());
            Gson gson = gsonBuilder.create();*/

            league = gson.fromJson(jsonParser.parse(fileReader).toString(), League.class);
            fileReader.close();

        } catch (FileNotFoundException e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        } catch (ParseException e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        } catch (IOException e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        } catch (Exception e) {
            consoleOutput.printMsgToConsole(DESERIALIZATIONERROR);
        }
        return league;
    }

}


/*
class AgingCreator implements InstanceCreator {

    @Override
    public Aging createInstance(Type type) {
        return new Aging();
    }

}*/
