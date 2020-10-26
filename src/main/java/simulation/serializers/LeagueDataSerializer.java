package simulation.serializers;


import simulation.model.Conference;
import simulation.model.Division;
import simulation.model.League;
import simulation.model.Player;
import userIO.ConsoleOutput;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;

public class LeagueDataSerializer {

    public void serialize(League league){
        Gson gson = new GsonBuilder().registerTypeAdapter(Player.class, new PlayerSerializer()).setPrettyPrinting().create();

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("JsonOutput.txt");
            gson.toJson(league,fileWriter);
            fileWriter.close();
        }
        catch (IOException e) {
            ConsoleOutput.printToConsole("Json could not be created");
        }

    }

}
