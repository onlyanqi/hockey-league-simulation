package simulation.serializers.ModelsForDeserialization.model;

import java.util.HashMap;
import java.util.List;

public class Shift {

    public String teamName;
    public Player goalie;
    public List<Player> forward;
    public List<Player> defense;
    public HashMap<Player, Integer> penalizedDefensePlayer;
}