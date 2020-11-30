package simulation.model;

import org.apache.log4j.Logger;
import simulation.state.HockeyContext;

import java.util.*;

public class Shift implements IShift {

    static Logger log = Logger.getLogger(Shift.class);
    String teamName;
    IPlayer goalie;
    List<IPlayer> forward = new ArrayList<>();
    List<IPlayer> defense = new ArrayList<>();
    ;
    HashMap<IPlayer, Integer> penalizedDefensePlayer = new HashMap<>();

    public Shift() {
        penalizedDefensePlayer = new HashMap<>();
    }

    public Shift(persistance.serializers.ModelsForDeserialization.model.Shift shift) {
        this.teamName = shift.teamName;
        this.goalie = new Player(shift.goalie);
        for (persistance.serializers.ModelsForDeserialization.model.Player player : shift.forward) {
            this.forward.add(new Player(player));
        }
        for (persistance.serializers.ModelsForDeserialization.model.Player player : shift.defense) {
            this.defense.add(new Player(player));
        }

        Iterator iterator = shift.penalizedDefensePlayer.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            this.penalizedDefensePlayer.put(new Player((persistance.serializers.ModelsForDeserialization.model.Player) mapEntry.getKey()), (Integer) mapEntry.getValue());
        }
    }

    @Override
    public IPlayer getGoalie() {
        return goalie;
    }

    @Override
    public void setGoalie(IPlayer goalie) {
        this.goalie = goalie;
    }

    @Override
    public List<IPlayer> getForward() {
        return forward;
    }

    @Override
    public void setForward(List<IPlayer> forward) {
        this.forward = forward;
    }

    @Override
    public List<IPlayer> getDefense() {
        return defense;
    }

    @Override
    public void setDefense(List<IPlayer> defense) {
        this.defense = defense;
    }

    @Override
    public HashMap<IPlayer, Integer> getPenalizedDefensePlayer() {
        return penalizedDefensePlayer;
    }

    @Override
    public void setPenalizedDefensePlayer(HashMap<IPlayer, Integer> penalizedDefensePlayer) {
        this.penalizedDefensePlayer = penalizedDefensePlayer;
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public Integer getTeamShiftShootingTotal() {
        Integer teamShootingTotal = 0;
        for (IPlayer player : forward) {
            teamShootingTotal += player.getShooting();
        }
        if (teamShootingTotal < 0) {
            log.error("Team Shift Shooting total is negative.");
            throw new IllegalArgumentException("Team Shift Shooting total is negative.");
        }
        return teamShootingTotal;
    }

    @Override
    public Integer getTeamShiftDefenseTotal() {
        Integer teamDefenseTotal = 0;
        for (IPlayer player : defense) {
            teamDefenseTotal += player.getChecking();
        }
        if (teamDefenseTotal < 0) {
            log.error("Team Shift Defense Total is negative.");
            throw new IllegalArgumentException("Team Shift Defense is negative.");
        }
        return teamDefenseTotal;
    }

    @Override
    public Integer getTeamSkatingTotal() {
        Integer teamSkatingTotal = 0;
        teamSkatingTotal += goalie.getSkating();
        for (IPlayer player : forward) {
            teamSkatingTotal += player.getSkating();
        }
        for (IPlayer player : defense) {
            teamSkatingTotal += player.getSkating();
        }
        if (teamSkatingTotal < 0) {
            log.error("Team Shift Skating total is negative.");
            throw new IllegalArgumentException("Team Shift Skating total is negative.");
        }
        return teamSkatingTotal;
    }

    @Override
    public IShift getShift(ITeam team, HashMap<String, HashMap<Integer, Integer>> teamPlayersCount) {
        IShift shift = HockeyContext.getInstance().getModelFactory().createShift();
        HashMap<Integer, Integer> playersCount = teamPlayersCount.get(team.getName());

        if (goalie == null) {
            IPlayer goalie = getRandomPlayerByPosition(team, "GOALIE");
            while (didPlayerReachShiftCount(playersCount, goalie)) {
                goalie = getRandomPlayerByPosition(team, "GOALIE");
            }
            shift.setGoalie(goalie);
        } else {
            shift.setGoalie(goalie);
        }

        List<IPlayer> forwardList = new ArrayList<>();
        for (int forwards = 0; forwards < 3; forwards++) {
            IPlayer forward = getRandomPlayerByPosition(team, "FORWARD");
            while (didPlayerReachShiftCount(playersCount, forward)) {
                forward = getRandomPlayerByPosition(team, "FORWARD");
            }
            forwardList.add(forward);
        }
        shift.setForward(forwardList);

        List<IPlayer> defenseList = new ArrayList<>();
        for (int defenses = 0; defenses < 2; defenses++) {

            IPlayer defense = getRandomPlayerByPosition(team, "DEFENSE");
            while (didPlayerReachShiftCount(playersCount, defense)) {
                defense = getRandomPlayerByPosition(team, "DEFENSE");
            }
            defenseList.add(defense);
        }
        shift.setDefense(defenseList);
        shift.setTeamName(team.getName());
        return shift;
    }

    @Override
    public void updateGoalie(ITeam team) {
        for (IPlayer player : team.getActivePlayerList()) {
            if (player.getPosition().name().equals("GOALIE")) {
                if (goalie.getId() == player.getId()) {
                    break;
                } else {
                    this.goalie = player;
                }
            }
        }
    }

    @Override
    public boolean didPlayerReachShiftCount(HashMap<Integer, Integer> playersCount, IPlayer player) {
        if (playersCount.get(player.getId()) > 13) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IShift getShiftForPenalizedTeam(ITeam team, HashMap<String, HashMap<Integer, Integer>> teamPlayersCount) {
        IShift shift = HockeyContext.getInstance().getModelFactory().createShift();
        HashMap<Integer, Integer> playersCount = teamPlayersCount.get(team.getName());
        IPlayer goalie = getRandomPlayerByPosition(team, "GOALIE");
        while (didPlayerReachShiftCount(playersCount, goalie)) {
            goalie = getRandomPlayerByPosition(team, "GOALIE");
        }

        shift.setGoalie(goalie);

        List<IPlayer> forwardList = new ArrayList<>();
        for (int forwards = 0; forwards < 3; forwards++) {
            IPlayer forward = getRandomPlayerByPosition(team, "FORWARD");
            while (didPlayerReachShiftCount(playersCount, forward)) {
                forward = getRandomPlayerByPosition(team, "FORWARD");
            }
            forwardList.add(forward);
        }
        shift.setForward(forwardList);

        HashMap<IPlayer, Integer> penalPlayers = this.getPenalizedDefensePlayer();
        int penalBoxSize = penalPlayers.size();

        List<IPlayer> defenseList = new ArrayList<>();
        for (int defenses = 0; defenses < 2 - penalBoxSize; defenses++) {

            IPlayer defense = getRandomPlayerByPosition(team, "DEFENSE");
            while (didPlayerReachShiftCount(playersCount, defense)) {
                defense = getRandomPlayerByPosition(team, "DEFENSE");
            }
            defenseList.add(defense);
        }

        for (IPlayer penalPlayer : penalPlayers.keySet()) {
            defenseList.add(penalPlayer);
        }

        shift.setDefense(defenseList);
        shift.setTeamName(team.getName());
        return shift;
    }

    private IPlayer getRandomPlayerByPosition(ITeam team, String position) {
        Random random = new Random();
        List<IPlayer> positionPlayers = new ArrayList<>();
        for (IPlayer player : team.getActivePlayerList()) {
            if (player.getPosition().name().equals(position)) {
                positionPlayers.add(player);
            }
        }
        return positionPlayers.get(random.nextInt(positionPlayers.size()));
    }

}