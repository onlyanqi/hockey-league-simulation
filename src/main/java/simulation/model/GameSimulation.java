package simulation.model;

import org.apache.log4j.Logger;
import simulation.state.gamestatemachine.GameContext;

import java.util.HashMap;
import java.util.Map;

public class GameSimulation {

    private IShift team1Shift;
    private IShift team2Shift;
    private ITeam team1;
    private ITeam team2;
    private HashMap<String,HashMap<Integer,Integer>> teamPlayersCount = new HashMap<>();
    private HashMap<String,Integer> goals = new HashMap<>();
    private HashMap<String,Integer> penalties = new HashMap<>();
    private HashMap<String,Integer> shots = new HashMap<>();
    private HashMap<String,Integer> saves = new HashMap<>();
    Logger log = Logger.getLogger(GameSimulation.class);

    public GameSimulation(ITeam team1, ITeam team2) {
        this.team1 = team1;
        this.team2 = team2;
        initializeGameSimulation();
    }

    public GameSimulation(simulation.serializers.ModelsForDeserialization.model.GameSimulation gameSimulation){
        this.team1Shift = new Shift(gameSimulation.team1Shift);
        this.team2Shift = new Shift(gameSimulation.team2Shift);
        this.team1 = new Team(gameSimulation.team1);
        this.team2 = new Team(gameSimulation.team2);
        this.teamPlayersCount = gameSimulation.teamPlayersCount;
        this.goals = gameSimulation.goals;
        this.penalties = gameSimulation.penalties;
        this.shots = gameSimulation.shots;
        this.saves = gameSimulation.saves;
    }

    public void initializeGameSimulation(){
        goals = new HashMap<>();
        penalties = new HashMap<>();
        saves = new HashMap<>();
        shots = new HashMap<>();
        team1Shift = new Shift();
        team2Shift = new Shift();
        teamPlayersCount = new HashMap<>();
        initializeGameStats();
        initializeTeamPlayerShiftCount(team1);
        initializeTeamPlayerShiftCount(team2);
    }

    private void initializeGameStats() {
        goals.put(team1.getName(),0);
        goals.put(team2.getName(),0);

        penalties.put(team1.getName(),0);
        penalties.put(team2.getName(),0);

        saves.put(team1.getName(),0);
        saves.put(team2.getName(),0);

        shots.put(team1.getName(),0);
        shots.put(team2.getName(),0);
    }

    public void play() throws Exception {
        for(int timeIn10Seconds = 0 ; timeIn10Seconds < (60*60)/10 ;timeIn10Seconds ++){
            if(team1Shift.getPenalizedDefensePlayer().size() >0){
                for (Map.Entry<IPlayer, Integer> penalPlayer : team1Shift.getPenalizedDefensePlayer().entrySet()) {
                    if(penalPlayer.getValue().equals(0)){
                        removeFromPenaltyBoxAndAddToShift(team1Shift,penalPlayer.getKey());
                    }
                    penalPlayer.setValue(penalPlayer.getValue()-1);
                }
            }
            if(team2Shift.getPenalizedDefensePlayer().size() >0){
                for (Map.Entry<IPlayer, Integer> penalPlayer : team2Shift.getPenalizedDefensePlayer().entrySet()) {
                    if(penalPlayer.getValue().equals(0)){
                        removeFromPenaltyBoxAndAddToShift(team2Shift,penalPlayer.getKey());
                    }
                    penalPlayer.setValue(penalPlayer.getValue()-1);
                }
            }
            if(timeIn10Seconds == 240){
                team1Shift.updateGoalie(team1);
                team2Shift.updateGoalie(team2);
            }
            if(timeIn10Seconds % 9 ==0){
                if(team1Shift.getPenalizedDefensePlayer().size() >0){
                    team1Shift = team1Shift.getShiftForPenalizedTeam(team1,teamPlayersCount);
                }else{
                    team1Shift = team1Shift.getShift(team1,teamPlayersCount);
                }

                if(team2Shift.getPenalizedDefensePlayer().size() > 0){
                    team2Shift = team2Shift.getShiftForPenalizedTeam(team2,teamPlayersCount);
                }else{
                    team2Shift = team2Shift.getShift(team2,teamPlayersCount);
                }
            }
            if(timeIn10Seconds % 5 ==0) {
                GameContext gameContext = new GameContext(this);
                gameContext.start();
            }
        }
    }

    public IShift getTeam1Shift() {
        return team1Shift;
    }

    public void setTeam1Shift(IShift team1Shift) {
        this.team1Shift = team1Shift;
    }

    public IShift getTeam2Shift() {
        return team2Shift;
    }

    public void setTeam2Shift(IShift team2Shift) {
        this.team2Shift = team2Shift;
    }

    public ITeam getTeam1() {
        return team1;
    }

    public void setTeam1(ITeam team1) {
        this.team1 = team1;
    }

    public ITeam getTeam2() {
        return team2;
    }

    public void setTeam2(ITeam team2) {
        this.team2 = team2;
    }

    public HashMap<String, HashMap<Integer, Integer>> getTeamPlayersCount() {
        return teamPlayersCount;
    }

    public void setTeamPlayersCount(HashMap<String, HashMap<Integer, Integer>> teamPlayersCount) {
        this.teamPlayersCount = teamPlayersCount;
    }

    public HashMap<String, Integer> getGoals() {
        return goals;
    }

    public void setGoals(HashMap<String, Integer> goals) {
        this.goals = goals;
    }

    public HashMap<String, Integer> getPenalties() {
        return penalties;
    }

    public void setPenalties(HashMap<String, Integer> penalties) {
        this.penalties = penalties;
    }

    public HashMap<String, Integer> getShots() {
        return shots;
    }

    public void setShots(HashMap<String, Integer> shots) {
        this.shots = shots;
    }

    public HashMap<String, Integer> getSaves() {
        return saves;
    }

    public void setSaves(HashMap<String, Integer> saves) {
        this.saves = saves;
    }

    public void addToPenaltyBox(IShift teamShift, IPlayer randDefense) {
        if(teamShift==null){
            log.error("Team Shift is null while adding to penalty box");
            throw new IllegalArgumentException("Team Shift is null while adding to penalty box");
        }
        if(randDefense==null){
            log.error("Defense Player is null while adding to penalty box");
            throw new IllegalArgumentException("Defense Player is null while adding to penalty box");
        }
        log.debug("Added "+ randDefense.getName() + "to penalty box");
        teamShift.getPenalizedDefensePlayer().put(randDefense,12);
        teamShift.getDefense().remove(randDefense);
    }

    public void removeFromPenaltyBoxAndAddToShift(IShift teamShift, IPlayer player) {
        teamShift.getPenalizedDefensePlayer().remove(player);
        teamShift.getDefense().add(player);
    }

    private void initializeTeamPlayerShiftCount(ITeam team) {
        if(team==null){
            log.error("Provided team is null while initializing player shift count");
            throw new IllegalArgumentException("provided team is null");
        }
        HashMap<Integer,Integer> playersCount  = new HashMap<>();
        for(IPlayer player : team.getActivePlayerList()){
            playersCount.put(player.getId(),0);
        }
        teamPlayersCount.put(team.getName(),playersCount);
    }
}
