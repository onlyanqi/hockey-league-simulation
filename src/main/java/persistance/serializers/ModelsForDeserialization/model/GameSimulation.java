package persistance.serializers.ModelsForDeserialization.model;

import java.util.HashMap;

public class GameSimulation {

    public Shift team1Shift;
    public Shift team2Shift;
    public Team team1;
    public Team team2;
    public HashMap<String, HashMap<Integer, Integer>> teamPlayersCount;
    public HashMap<String, Integer> goals;
    public HashMap<String, Integer> penalties;
    public HashMap<String, Integer> shots;
    public HashMap<String, Integer> saves;
}
