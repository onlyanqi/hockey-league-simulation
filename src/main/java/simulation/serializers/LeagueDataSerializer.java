package simulation.serializers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import simulation.model.League;
import simulation.model.Player;
import userIO.ConsoleOutput;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LeagueDataSerializer {

    public void serialize(League league) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("JsonOutput.txt");
            gson.toJson(league, fileWriter);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            ConsoleOutput.printToConsole("Json could not be created");
        }
    }

    public League deSerialize(){
        FileReader fileReader;
        JSONParser jsonParser = new JSONParser();
        League league = null;
        try{
            fileReader = new FileReader("JsonOutput.txt");
            Gson gson = new Gson();
            league = gson.fromJson(jsonParser.parse(fileReader).toString(),League.class);
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return league;
    }

}
