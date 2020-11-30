package persistance.serializers.ModelsForDeserialization.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team extends SharedAttributes {

    public String mascot;
    public int divisionId;
    public double strength;
    public boolean aiTeam;
    public Coach coach;
    public Manager manager;
    public List<Player> playerList;
    public List<Player> activePlayerList;
    public List<Player> inactivePlayerList;
    public int playersTradedCount;
    public int lossPoint;
    public List<String> draftPicks = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null));

}
