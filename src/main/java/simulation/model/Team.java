package simulation.model;

import config.AppConfig;
import simulation.dao.IPlayerDao;
import simulation.dao.ITeamDao;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import simulation.serializers.ModelsForDeserialization.model.Player;

import java.util.*;

public class Team extends SharedAttributes implements ITeam {

    private String mascot;
    private int divisionId;
    private double strength;
    private boolean aiTeam;
    private ICoach coach;
    private IManager manager;
    private List<IPlayer> playerList = new ArrayList<>();
    private List<IPlayer> activePlayerList = new ArrayList<>();
    private List<IPlayer> inactivePlayerList = new ArrayList<>();
    private int playersTradedCount;
    private int lossPoint;
    private List<String> draftPicks = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null));

    public Team() {
        setId(System.identityHashCode(this));
    }

    public Team(int id) {
        setId(id);
    }

    public Team(int id, ITeamDao factory) throws Exception {
        setId(id);
        factory.loadTeamById(id, this);
    }

    public Team(String name, ITeamDao factory) throws Exception {
        factory.loadTeamByName(name, this);
    }

    public Team(simulation.serializers.ModelsForDeserialization.model.Team team){
        for(Player player : team.activePlayerList){
            this.activePlayerList.add(new simulation.model.Player(player));
        }
        this.aiTeam = team.aiTeam;
        this.coach = new Coach(team.coach);
        this.divisionId = team.divisionId;
        this.draftPicks = team.draftPicks;
        for(Player player : team.inactivePlayerList){
            this.inactivePlayerList.add(new simulation.model.Player(player));
        }
        this.lossPoint = team.lossPoint;
        this.manager = new Manager(team.manager);
        this.mascot = team.mascot;
        for(Player player : team.playerList){
            this.playerList.add(new simulation.model.Player(player));
        }
        this.playersTradedCount = team.playersTradedCount;
        this.strength = team.strength;
        this.setName(team.name);
        this.setId(team.id);
    }

    public List<IPlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<IPlayer> playerList) throws IllegalArgumentException {
        if (checkNumPlayer(playerList)) {
            this.playerList = playerList;
        } else {
            throw new IllegalArgumentException("Please make sure team: " + this.getName() + " has 30 players with 16 forwards, 10 defense, 4 goalies!");
        }
    }

    public List<IPlayer> getActivePlayerList() {
        return activePlayerList;
    }

    public void setActivePlayerList() {
        int noOfGoalies = 0;
        int noOfSkaters = 0;
        List<IPlayer> teamPlayerList = this.getPlayerList();
        List<IPlayer> activePlayerList = new ArrayList<>();
        Collections.sort(teamPlayerList, Collections.reverseOrder());
        for (IPlayer thisPlayer : teamPlayerList) {
            if (thisPlayer.getInjured()) {
                continue;
            }
            if (thisPlayer.getPosition() == Position.GOALIE && noOfGoalies < 2) {
                activePlayerList.add(thisPlayer);
                noOfGoalies++;
            } else if (noOfSkaters < 18) {
                activePlayerList.add(thisPlayer);
                noOfSkaters++;
            }
        }
        if (noOfGoalies < 2 || noOfSkaters < 18) {
            for (IPlayer thisPlayer : teamPlayerList) {
                if (thisPlayer.getPosition() == Position.GOALIE && noOfGoalies < 2 && thisPlayer.getInjured()) {
                    activePlayerList.add(thisPlayer);
                    noOfGoalies++;
                } else if (noOfSkaters < 18 && thisPlayer.getInjured()) {
                    activePlayerList.add(thisPlayer);
                    noOfSkaters++;
                }
            }
        }
        this.activePlayerList = activePlayerList;
        List<IPlayer> inactivePlayers = new ArrayList<>(teamPlayerList);
        inactivePlayers.removeAll(activePlayerList);
        setInactivePlayerList(inactivePlayers);
    }

    public List<IPlayer> getInactivePlayerList() {
        return inactivePlayerList;
    }

    public void setInactivePlayerList(List<IPlayer> inactivePlayerList) {
        this.inactivePlayerList = inactivePlayerList;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public ICoach getCoach() {
        return coach;
    }

    public void setCoach(ICoach coach) {
        this.coach = coach;
    }

    public IManager getManager() {
        return manager;
    }

    public void setManager(IManager manager) {
        this.manager = manager;
    }

    public void addTeam(ITeamDao addTeamFactory) throws Exception {
        addTeamFactory.addTeam(this);
    }

    public void setStrength() {
        for (IPlayer player : getPlayerList()) {
            if (player.getInjured()) {
                this.strength += 0.5 * player.getStrength();
            } else {
                this.strength += player.getStrength();
            }
        }
    }

    public double getStrength() {
        return strength;
    }

    public boolean isAiTeam() {
        return aiTeam;
    }

    public void setAiTeam(boolean aiTeam) {
        this.aiTeam = aiTeam;
    }

    public void loadPlayerListByTeamId(IPlayerDao loadPlayerFactory) throws Exception {
        this.playerList = loadPlayerFactory.loadPlayerListByTeamId(getId());
    }

    public List<Integer> createChosenPlayerIdList(IFreeAgent freeAgent) {
        IUserInputForTeamCreation teamCreationInput = AppConfig.getInstance().getInputForTeamCreation();
        IConsoleOutputForTeamCreation teamCreationOutput = AppConfig.getInstance().getOutputForTeamCreation();
        int numberOfForward = 0;
        int numberOfGoalie = 0;
        int numberOfDefense = 0;

        List<IPlayer> freeAgentList = freeAgent.getPlayerList();
        List<Double> strengthList = createStrengthList(freeAgentList);

        List<Integer> goodFreeAgentsIdList = freeAgent.getGoodFreeAgentsList(strengthList);
        teamCreationOutput.showInstructionsForTeamCreation();
        teamCreationOutput.showGoodFreeAgentList(freeAgentList, goodFreeAgentsIdList);
        teamCreationOutput.showBelowAverageFreeAgentList(freeAgentList, goodFreeAgentsIdList);
        List<Integer> chosenPlayersIdList = new ArrayList<>();
        selectPlayers(teamCreationInput, teamCreationOutput, numberOfForward, numberOfGoalie, numberOfDefense, freeAgentList, chosenPlayersIdList);
        return chosenPlayersIdList;
    }

    public List<Double> createStrengthList(List<IPlayer> freeAgentList) {
        List<Double> strengthList = new ArrayList<>();
        for (int i = 0; i < freeAgentList.size(); i++) {
            IPlayer freeAgentPlayer = freeAgentList.get(i);
            freeAgentPlayer.setStrength();
            Double playerStrength = freeAgentPlayer.getStrength();
            strengthList.add(i, playerStrength);
        }
        return strengthList;
    }

    private void selectPlayers(IUserInputForTeamCreation teamCreationInput,
                               IConsoleOutputForTeamCreation teamCreationOutput, int numberOfForward,
                               int numberOfGoalie, int numberOfDefense, List<IPlayer> freeAgentList,
                               List<Integer> chosenPlayersIdList) {
        int playerId;
        while (numberOfGoalie < 4 || numberOfForward < 16 || numberOfDefense < 10) {
            playerId = teamCreationInput.getPlayerId(freeAgentList.size() - 1);
            if (playerId < 0 || playerId >= freeAgentList.size()) {
                continue;
            } else if (chosenPlayersIdList.contains(playerId)) {
                teamCreationOutput.playerIdAlreadyChosenMessage(chosenPlayersIdList);
                continue;
            }
            IPlayer player = freeAgentList.get(playerId);
            if (numberOfGoalie < 4 && player.getPosition() == Position.GOALIE) {
                chosenPlayersIdList.add(playerId);
                numberOfGoalie = numberOfGoalie + 1;
                teamCreationOutput.showCountOfNeededPlayers(4 - numberOfGoalie, 16 - numberOfForward, 10 - numberOfDefense);
            } else if (numberOfForward < 16 && player.getPosition() == Position.FORWARD) {
                chosenPlayersIdList.add(playerId);
                numberOfForward = numberOfForward + 1;
                teamCreationOutput.showCountOfNeededPlayers(4 - numberOfGoalie, 16 - numberOfForward, 10 - numberOfDefense);
            } else if (numberOfDefense < 10 && player.getPosition() == Position.DEFENSE) {
                chosenPlayersIdList.add(playerId);
                numberOfDefense = numberOfDefense + 1;
                teamCreationOutput.showCountOfNeededPlayers(4 - numberOfGoalie, 16 - numberOfForward, 10 - numberOfDefense);
            }
        }
    }

    public boolean checkNumPlayer(List<IPlayer> playerList) {
        boolean isValid = false;
        int goalieNum = 0;
        int forwardNum = 0;
        int defenseNum = 0;
        int playerNum = 0;
        int maxPlayers = 30;
        int maxGoalies = 4;
        int maxForwards = 16;
        int maxDefences = 10;

        for (IPlayer player : playerList) {
            if (player.getPosition() == Position.GOALIE) {
                goalieNum++;
            } else if (player.getPosition() == Position.FORWARD) {
                forwardNum++;
            } else {
                defenseNum++;
            }
            playerNum++;
        }
        if (playerNum == maxPlayers && goalieNum == maxGoalies
                && forwardNum == maxForwards && defenseNum == maxDefences) {

            isValid = true;
        }
        return isValid;
    }

    public int getPlayersTradedCount() {
        return playersTradedCount;
    }

    public void setPlayersTradedCount(int playersTradedCount) {
        this.playersTradedCount = playersTradedCount;
    }

    public int getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(int lossPoint) {
        this.lossPoint = lossPoint;
    }

    public void fixTeamAfterTrading(List<IPlayer> freeAgentList){
        int goalieNum = 0;
        int forwardNum = 0;
        int defenseNum = 0;
        int maxGoalies = 4;
        int maxForwards = 16;
        int maxDefences = 10;

        for (IPlayer player : playerList) {
            if (player.getPosition() == Position.GOALIE) {
                goalieNum++;
            } else if (player.getPosition() == Position.FORWARD) {
                forwardNum++;
            } else {
                defenseNum++;
            }
        }

        Collections.sort(freeAgentList, Collections.reverseOrder());

        if (goalieNum < maxGoalies) {
            addPlayer(freeAgentList, Position.GOALIE.toString(), goalieNum, maxGoalies);
        } else if(goalieNum > maxGoalies){
            dropPlayer(freeAgentList, Position.GOALIE.toString(), goalieNum, maxGoalies);
        }

        if (forwardNum < maxForwards) {
            addPlayer(freeAgentList, Position.FORWARD.toString(), forwardNum, maxForwards);
        } else if(forwardNum > maxForwards){
            dropPlayer(freeAgentList, Position.FORWARD.toString(), forwardNum, maxForwards);
        }

        if (defenseNum < maxDefences) {
            addPlayer(freeAgentList, Position.DEFENSE.toString(), defenseNum, maxDefences);
        } else if(defenseNum > maxDefences){
            dropPlayer(freeAgentList, Position.DEFENSE.toString(), defenseNum, maxDefences);
        }
    }

    private void addPlayer(List<IPlayer> freeAgentList, String position, int noOfPlayers, int maxPlayers){
        List<IPlayer> removedList = new ArrayList<>();
        for(IPlayer player : freeAgentList){
            if(player.getPosition().toString().equals(position) && noOfPlayers < maxPlayers){
                removedList.add(player);
                getPlayerList().add(player);
                noOfPlayers++;
            } else if(noOfPlayers == maxPlayers){
                break;
            }
        }
        for(IPlayer player : removedList){
            freeAgentList.remove(player);
        }
    }

    private void dropPlayer(List<IPlayer> freeAgentList, String position, int noOfPlayers, int maxPlayers){
        Collections.sort(getPlayerList());
        for(int i = 0; noOfPlayers > maxPlayers; i++){
            IPlayer player = getPlayerList().get(i);
            if(player.getPosition().toString().equals(position) && maxPlayers < noOfPlayers){
                freeAgentList.add(player);
                getPlayerList().remove(player);
                noOfPlayers--;
            }
        }
    }

    public List<String> getDraftPicks() {
        return draftPicks;
    }

    public void setDraftPicks(List<String> draftPicks) {
        this.draftPicks = draftPicks;
    }

    private void removeObjectFromList(List<IPlayer> list, IPlayer toRemove) {
        Iterator<IPlayer> itr = list.iterator();
        while (itr.hasNext()) {
            IPlayer player = itr.next();
            if (player.getName().equals(toRemove.getName())
                    && player.getPosition().equals(toRemove.getPosition())
                    && player.getStrength() == toRemove.getStrength()
                    && player.getSkating() == toRemove.getSkating()) {

                itr.remove();
                break;
            }
        }
    }
}
