package simulation.model;

import java.util.HashMap;
import java.util.List;

public interface IShift {
    IPlayer getGoalie();

    void setGoalie(IPlayer goalie);

    List<IPlayer> getForward();

    void setForward(List<IPlayer> forward);

    List<IPlayer> getDefense();

    void setDefense(List<IPlayer> defense);

    HashMap<IPlayer, Integer> getPenalizedDefensePlayer();

    void setPenalizedDefensePlayer(HashMap<IPlayer, Integer> penalizedDefensePlayer);

    String getTeamName();

    void setTeamName(String teamName);

    Integer getTeamShiftShootingTotal();

    Integer getTeamShiftDefenseTotal();

    Integer getTeamSkatingTotal();

    IShift getShift(ITeam team, HashMap<String, HashMap<Integer, Integer>> teamPlayersCount);

    void updateGoalie(ITeam team);

    boolean didPlayerReachShiftCount(HashMap<Integer, Integer> playersCount, IPlayer player);

    IShift getShiftForPenalizedTeam(ITeam team, HashMap<String, HashMap<Integer, Integer>> teamPlayersCount);
}
