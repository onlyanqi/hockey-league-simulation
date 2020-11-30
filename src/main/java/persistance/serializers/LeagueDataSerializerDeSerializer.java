package persistance.serializers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import persistance.serializers.ModelsForDeserialization.model.LeagueDeserializationModel;
import presentation.ConsoleOutput;
import simulation.model.ILeague;

import java.io.*;

public class LeagueDataSerializerDeSerializer {

    public static final String JSONCREATIONERROR = "Json could not be created";
    public static final String DESERIALIZATIONERROR = "Could not deserialize";
    public static String FILENAME = "JsonOutput.txt";
    private ConsoleOutput consoleOutput = null;

    public void serialize(ILeague league) {
        if (consoleOutput == null) {
            consoleOutput = ConsoleOutput.getInstance();
        }

        if (league == null) {
            return;
        }

        //Source for creating folder through java program: https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java

        File jsonDir = new File("/JsonFiles");
        if (!jsonDir.exists()) {
            jsonDir.mkdirs();
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();

        FileWriter fileWriter = null;
        try {
            FILENAME = "JsonFiles" + "/" + league.getUserCreatedTeamName();
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

    public LeagueDeserializationModel deSerialize(String filename) {
        System.out.println(filename);
        if (consoleOutput == null) {
            consoleOutput = ConsoleOutput.getInstance();
        }
        FileReader fileReader;
        JSONParser jsonParser = new JSONParser();
        LeagueDeserializationModel league = null;
        try {
            fileReader = new FileReader(filename);
            Gson gson = new Gson();
            league = gson.fromJson(jsonParser.parse(fileReader).toString(), LeagueDeserializationModel.class);
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

