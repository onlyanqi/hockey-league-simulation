package simulation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Shift{

    String teamName;
    IPlayer goalie;
    List<IPlayer> forward;
    List<IPlayer> defense;
    HashMap<IPlayer,Integer> penalizedDefensePlayer;


    public Shift(){
        penalizedDefensePlayer = new HashMap<>();
    }

    public IPlayer getGoalie() {
        return goalie;
    }

    public void setGoalie(IPlayer goalie) {
        this.goalie = goalie;
    }

    public List<IPlayer> getForward() {
        return forward;
    }

    public void setForward(List<IPlayer> forward) {
        this.forward = forward;
    }

    public List<IPlayer> getDefense() {
        return defense;
    }

    public void setDefense(List<IPlayer> defense) {
        this.defense = defense;
    }

    public HashMap<IPlayer, Integer> getPenalizedDefensePlayer() {
        return penalizedDefensePlayer;
    }

    public void setPenalizedDefensePlayer(HashMap<IPlayer, Integer> penalizedDefensePlayer) {
        this.penalizedDefensePlayer = penalizedDefensePlayer;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamShiftShootingTotal(){
        Integer teamShootingTotal = 0;
        teamShootingTotal += goalie.getShooting();
        for(IPlayer player: forward){
            teamShootingTotal += player.getShooting();
        }
        for(IPlayer player: defense){
            teamShootingTotal += player.getShooting();
        }
        return  teamShootingTotal;
    }

    public Integer getTeamShiftDefenseTotal(){
        Integer teamDefenseTotal = 0;
        teamDefenseTotal += goalie.getChecking();
        for(IPlayer player: forward){
            teamDefenseTotal += player.getChecking();
        }
        for(IPlayer player: defense){
            teamDefenseTotal += player.getChecking();
        }
        return teamDefenseTotal;
    }
    public Integer getTeamSkatingTotal(){
        Integer teamDefenseTotal = 0;
        teamDefenseTotal += goalie.getSkating();
        for(IPlayer player: forward){
            teamDefenseTotal += player.getSkating();
        }
        for(IPlayer player: defense){
            teamDefenseTotal += player.getSkating();
        }
        return teamDefenseTotal;
    }

    public Shift getShift(ITeam team, HashMap<String, HashMap<String, Integer>> teamPlayersCount) {
        Shift shift = new Shift();
        HashMap<String,Integer> playersCount  = teamPlayersCount.get(team.getName());
        IPlayer goalie = getRandomPlayerByPosition(team,"GOALIE");
        while(didPlayerReachShiftCount(playersCount,goalie)){
            goalie = getRandomPlayerByPosition(team,"GOALIE");
        }
        //set Goalie to shift
        shift.setGoalie(goalie);

        List<IPlayer> forwardList = new ArrayList<>();
        for(int forwards =0; forwards<3;forwards++){
            IPlayer forward = getRandomPlayerByPosition(team,"FORWARD");
            while(didPlayerReachShiftCount(playersCount,forward)){
                forward = getRandomPlayerByPosition(team,"FORWARD");
            }
            forwardList.add(forward);
        }
        //set forwards list to shift
        shift.setForward(forwardList);

        List<IPlayer> defenseList = new ArrayList<>();
        for(int defenses =0; defenses<2;defenses++){

            IPlayer defense = getRandomPlayerByPosition(team,"DEFENSE");
            while(didPlayerReachShiftCount(playersCount,defense)){
                defense = getRandomPlayerByPosition(team,"DEFENSE");
            }
            defenseList.add(defense);
        }
        shift.setDefense(defenseList);
        shift.setTeamName(team.getName());
        return shift;
    }

    public boolean didPlayerReachShiftCount(HashMap<String,Integer> playersCount,IPlayer player){
        if(playersCount.get(player.getName())>13){
            return true;
        }else{
            return false;
        }
    }

    public Shift getShiftForPenalizedTeam(ITeam team, HashMap<String, HashMap<String, Integer>> teamPlayersCount) {
        Shift shift = new Shift();
        HashMap<String,Integer> playersCount  = teamPlayersCount.get(team.getName());
        IPlayer goalie = getRandomPlayerByPosition(team,"GOALIE");
        while(didPlayerReachShiftCount(playersCount,goalie)){
            goalie = getRandomPlayerByPosition(team,"GOALIE");
        }
        //set Goalie to shift
        shift.setGoalie(goalie);

        List<IPlayer> forwardList = new ArrayList<>();
        for(int forwards =0; forwards<3;forwards++){
            IPlayer forward = getRandomPlayerByPosition(team,"FORWARD");
            while(didPlayerReachShiftCount(playersCount,forward)){
                forward = getRandomPlayerByPosition(team,"FORWARD");
            }
            forwardList.add(forward);
        }
        //set forwards list to shift
        shift.setForward(forwardList);

        //getPenalizedPlayers
        HashMap<IPlayer,Integer> penalPlayers = this.getPenalizedDefensePlayer();
        int penalBoxSize = penalPlayers.size();

        //add other players except penalized ones
        List<IPlayer> defenseList = new ArrayList<>();
        for(int defenses =0; defenses<2-penalBoxSize;defenses++){

            IPlayer defense = getRandomPlayerByPosition(team,"DEFENSE");
            while(didPlayerReachShiftCount(playersCount,defense)){
                defense = getRandomPlayerByPosition(team,"DEFENSE");
            }
            defenseList.add(defense);
        }

        //add penalized players to the defense list
        for(IPlayer penalPlayer: penalPlayers.keySet()){
            defenseList.add(penalPlayer);
        }

        shift.setDefense(defenseList);
        shift.setTeamName(team.getName());
        return shift;
    }

    private IPlayer getRandomPlayerByPosition(ITeam team, String position){
        Random random = new Random();
        List<IPlayer> positionPlayers = new ArrayList<>();
        for(IPlayer player: team.getActivePlayerList()){
            if(player.getPosition().name().equals(position)){
                positionPlayers.add(player);
            }
        }
        return positionPlayers.get(random.nextInt(positionPlayers.size()));
    }

}