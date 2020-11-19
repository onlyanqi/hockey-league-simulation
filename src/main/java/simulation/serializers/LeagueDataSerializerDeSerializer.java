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

public class LeagueDataSerializerDeSerializer {

    public static final String FILENAME = "JsonOutput.txt";
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
        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();

        FileWriter fileWriter = null;
        try {
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

    public League deSerialize() {
        if (consoleOutput == null) {
            consoleOutput = ConsoleOutput.getInstance();
        }
        FileReader fileReader;
        JSONParser jsonParser = new JSONParser();
        League league = null;
        try {
            fileReader = new FileReader(FILENAME);
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
