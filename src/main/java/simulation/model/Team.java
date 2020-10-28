package simulation.model;

import com.google.gson.annotations.SerializedName;
import config.AppConfig;
import db.data.IPlayerFactory;
import db.data.ITeamFactory;
import userIO.IConsoleOutputForTeamCreation;
import userIO.IUserInputForTeamCreation;

import java.util.ArrayList;
import java.util.List;

public class Team extends SharedAttributes {
    public static final String GOALIE = "goalie";
    public static final String DEFENSE = "defense";
    public static final String FORWARD = "forward";

    private String hometown;
    private String mascot;
    private int divisionId;
    private double strength;
    private Coach coach;
    private Boolean aiTeam;

    private Manager manager;
    private String generalManagerName;
    private List<Player> playerList;

    public Team() {
    }

    public Team(int id) {
        setId(id);
    }

    public Team(int id, ITeamFactory factory) throws Exception {
        setId(id);
        factory.loadTeamById(id, this);
    }

    public Team(String name, ITeamFactory factory) throws Exception {
        factory.loadTeamByName(name, this);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
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

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
        this.generalManagerName = manager.getName();
    }

    public void addTeam(ITeamFactory addTeamFactory) throws Exception {
        addTeamFactory.addTeam(this);
    }

    public void setStrength() {
        for (Player player : getPlayerList()) {
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

    public Boolean getAiTeam() {
        return aiTeam;
    }

    public void setAiTeam(Boolean aiTeam) {
        this.aiTeam = aiTeam;
    }

    public void loadPlayerListByTeamId(IPlayerFactory loadPlayerFactory) throws Exception {
        this.playerList = loadPlayerFactory.loadPlayerListByTeamId(getId());
    }

    public List<Integer> createChosenPlayerIdList(FreeAgent freeAgent) {
        IUserInputForTeamCreation teamCreationInput = AppConfig.getInstance().getInputForTeamCreation();
        IConsoleOutputForTeamCreation teamCreationOutput = AppConfig.getInstance().getOutputForTeamCreation();
        int numberOfSkaters = 0;
        int numberOfGoalies = 0;
        List<Player> freeAgentList = freeAgent.getPlayerList();
        List<Double> strengthList = createStrengthList(freeAgentList);

        List<Integer> goodFreeAgentsIdList = freeAgent.getGoodFreeAgentsList(strengthList);
        teamCreationOutput.showInstructionsForTeamCreation();
        teamCreationOutput.showGoodFreeAgentList(freeAgentList, goodFreeAgentsIdList);
        teamCreationOutput.showBelowAverageFreeAgentList(freeAgentList, goodFreeAgentsIdList);
        List<Integer> chosenPlayersIdList = new ArrayList<>();
        selectPlayers(teamCreationInput, teamCreationOutput, numberOfSkaters, numberOfGoalies, freeAgentList, chosenPlayersIdList);
        return chosenPlayersIdList;
    }

    private List<Double> createStrengthList(List<Player> freeAgentList) {
        List<Double> strengthList = new ArrayList<>();
        for (int i = 0; i < freeAgentList.size(); i++) {
            Player freeAgentPlayer = freeAgentList.get(i);
            freeAgentPlayer.setStrength();
            Double playerStrength = freeAgentPlayer.getStrength();
            strengthList.add(i, playerStrength);
        }
        return strengthList;
    }

    private void selectPlayers(IUserInputForTeamCreation teamCreationInput,
                               IConsoleOutputForTeamCreation teamCreationOutput, int numberOfSkaters,
                               int numberOfGoalies, List<Player> freeAgentList, List<Integer> chosenPlayersIdList) {
        int playerId;
        while (numberOfGoalies < 2 || numberOfSkaters < 18) {
            playerId = teamCreationInput.getPlayerId(freeAgentList.size() - 1);
            if (playerId < 0 || playerId >= freeAgentList.size()) {
                continue;
            } else if (chosenPlayersIdList.contains(playerId)) {
                teamCreationOutput.playerIdAlreadyChosenMessage(chosenPlayersIdList);
                continue;
            }
            Player player = freeAgentList.get(playerId);
            if (numberOfGoalies < 2 && player.getPosition().toString().equals(GOALIE)) {
                chosenPlayersIdList.add(playerId);
                numberOfGoalies = numberOfGoalies + 1;
                teamCreationOutput.showCountOfNeededPlayers(2 - numberOfGoalies, 18 - numberOfSkaters);
            } else if (numberOfSkaters < 18 && (player.getPosition().toString().equals(DEFENSE) || player.getPosition().toString().equals(FORWARD))) {
                chosenPlayersIdList.add(playerId);
                numberOfSkaters = numberOfSkaters + 1;
                teamCreationOutput.showCountOfNeededPlayers(2 - numberOfGoalies, 18 - numberOfSkaters);
            }
        }
    }


    private int pendingTradeOfferCount;

    public int getPendingTradeOfferCount() {
        return pendingTradeOfferCount;
    }

    public void setPendingTradeOfferCount(int pendingTradeOfferCount) {
        this.pendingTradeOfferCount = pendingTradeOfferCount;
    }

    private int lossPoint;

    public int getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(int lossPoint) {
        this.lossPoint = lossPoint;
    }
}
