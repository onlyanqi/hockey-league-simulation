package simulation.model;

import java.util.HashMap;

public interface IGameSimulation {
    void initializeGameSimulation();

    void play() throws Exception;

    IShift getTeam1Shift();

    void setTeam1Shift(IShift team1Shift);

    IShift getTeam2Shift();

    void setTeam2Shift(IShift team2Shift);

    ITeam getTeam1();

    void setTeam1(ITeam team1);

    ITeam getTeam2();

    void setTeam2(ITeam team2);

    HashMap<String, HashMap<Integer, Integer>> getTeamPlayersCount();

    void setTeamPlayersCount(HashMap<String, HashMap<Integer, Integer>> teamPlayersCount);

    HashMap<String, Integer> getGoals();

    void setGoals(HashMap<String, Integer> goals);

    HashMap<String, Integer> getPenalties();

    void setPenalties(HashMap<String, Integer> penalties);

    HashMap<String, Integer> getShots();

    void setShots(HashMap<String, Integer> shots);

    HashMap<String, Integer> getSaves();

    void setSaves(HashMap<String, Integer> saves);

    void addToPenaltyBox(IShift teamShift, IPlayer randDefense);

    void removeFromPenaltyBoxAndAddToShift(IShift teamShift, IPlayer player);
}
