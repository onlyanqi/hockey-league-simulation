package simulation.model;

import config.AppConfig;
import db.data.IPlayerFactory;
import db.data.ITeamFactory;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import simulation.factory.ValidationConcrete;
import validator.IValidation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team extends SharedAttributes {

    private String mascot;
    private int divisionId;
    private double strength;
    private boolean aiTeam;
    private Coach coach;
    private Manager manager;
    private List<Player> playerList;
    private List<Player> activePlayerList;
    private List<Player> inactivePlayerList;
    private int tradeOfferCountOfSeason;
    private int lossPoint;

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

    public void setPlayerList(List<Player> playerList) throws IllegalArgumentException {
        if (checkNumPlayer(playerList)) {
            this.playerList = playerList;
        } else {
            throw new IllegalArgumentException("Please make sure team: " + this.getName() + " has 30 players with 16 forwards, 10 defense, 4 goalies!");
        }
    }

    public List<Player> getActivePlayerList() {
        return activePlayerList;
    }

    public void setActivePlayerList() {
        int noOfGoalies = 0;
        int noOfSkaters = 0;
        List<Player> teamPlayerList = this.getPlayerList();
        List<Player> activePlayerList = new ArrayList<>();
        Collections.sort(teamPlayerList, Collections.reverseOrder());
        for (Player thisPlayer : teamPlayerList) {
            if (thisPlayer.getInjured()) {
                continue;
            }
            if (thisPlayer.getPosition() == Player.Position.GOALIE && noOfGoalies < 2) {
                activePlayerList.add(thisPlayer);
                noOfGoalies++;
            } else if (noOfSkaters < 18) {
                activePlayerList.add(thisPlayer);
                noOfSkaters++;
            }
        }
        if (noOfGoalies < 2 || noOfSkaters < 18) {
            for (Player thisPlayer : teamPlayerList) {
                if (thisPlayer.getPosition() == Player.Position.GOALIE && noOfGoalies < 2 && thisPlayer.getInjured()) {
                    activePlayerList.add(thisPlayer);
                    noOfGoalies++;
                } else if (noOfSkaters < 18 && thisPlayer.getInjured()) {
                    activePlayerList.add(thisPlayer);
                    noOfSkaters++;
                }
            }
        }
        this.activePlayerList = activePlayerList;
        List<Player> inactivePlayers = new ArrayList<>(teamPlayerList);
        inactivePlayers.removeAll(activePlayerList);
        setInactivePlayerList(inactivePlayers);
    }

    public List<Player> getInactivePlayerList() {
        return inactivePlayerList;
    }

    public void setInactivePlayerList(List<Player> inactivePlayerList) {
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

    public boolean isAiTeam() {
        return aiTeam;
    }

    public void setAiTeam(boolean aiTeam) {
        this.aiTeam = aiTeam;
    }

    public void loadPlayerListByTeamId(IPlayerFactory loadPlayerFactory) throws Exception {
        this.playerList = loadPlayerFactory.loadPlayerListByTeamId(getId());
    }

    public List<Integer> createChosenPlayerIdList(FreeAgent freeAgent) {
        IUserInputForTeamCreation teamCreationInput = AppConfig.getInstance().getInputForTeamCreation();
        IConsoleOutputForTeamCreation teamCreationOutput = AppConfig.getInstance().getOutputForTeamCreation();
        int numberOfForward = 0;
        int numberOfGoalie = 0;
        int numberOfDefense = 0;

        List<Player> freeAgentList = freeAgent.getPlayerList();
        List<Double> strengthList = createStrengthList(freeAgentList);

        List<Integer> goodFreeAgentsIdList = freeAgent.getGoodFreeAgentsList(strengthList);
        teamCreationOutput.showInstructionsForTeamCreation();
        teamCreationOutput.showGoodFreeAgentList(freeAgentList, goodFreeAgentsIdList);
        teamCreationOutput.showBelowAverageFreeAgentList(freeAgentList, goodFreeAgentsIdList);
        List<Integer> chosenPlayersIdList = new ArrayList<>();
        selectPlayers(teamCreationInput, teamCreationOutput, numberOfForward, numberOfGoalie, numberOfDefense, freeAgentList, chosenPlayersIdList);
        return chosenPlayersIdList;
    }

    public List<Double> createStrengthList(List<Player> freeAgentList) {
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
                               IConsoleOutputForTeamCreation teamCreationOutput, int numberOfForward,
                               int numberOfGoalie, int numberOfDefense, List<Player> freeAgentList, List<Integer> chosenPlayersIdList) {
        int playerId;
        while (numberOfGoalie < 4 || numberOfForward < 16 || numberOfDefense < 10) {
            playerId = teamCreationInput.getPlayerId(freeAgentList.size() - 1);
            if (playerId < 0 || playerId >= freeAgentList.size()) {
                continue;
            } else if (chosenPlayersIdList.contains(playerId)) {
                teamCreationOutput.playerIdAlreadyChosenMessage(chosenPlayersIdList);
                continue;
            }
            Player player = freeAgentList.get(playerId);
            if (numberOfGoalie < 4 && player.getPosition() == Player.Position.GOALIE) {
                chosenPlayersIdList.add(playerId);
                numberOfGoalie = numberOfGoalie + 1;
                teamCreationOutput.showCountOfNeededPlayers(4 - numberOfGoalie, 16 - numberOfForward, 10 - numberOfDefense);
            } else if (numberOfForward < 16 && player.getPosition() == Player.Position.FORWARD) {
                chosenPlayersIdList.add(playerId);
                numberOfForward = numberOfForward + 1;
                teamCreationOutput.showCountOfNeededPlayers(4 - numberOfGoalie, 16 - numberOfForward, 10 - numberOfDefense);
            } else if (numberOfDefense < 10 && player.getPosition() == Player.Position.DEFENSE) {
                chosenPlayersIdList.add(playerId);
                numberOfDefense = numberOfDefense + 1;
                teamCreationOutput.showCountOfNeededPlayers(4 - numberOfGoalie, 16 - numberOfForward, 10 - numberOfDefense);
            }
        }
    }

    public boolean validTeam() {
        boolean isValid = false;
        int noOfGoalies = 0;
        int noOfSkaters = 0;
        ValidationConcrete validationConcrete = new ValidationConcrete();
        IValidation validation = validationConcrete.newValidation();
        if (validation.isListNotEmpty(playerList)) {
            for (Player player : playerList) {
                if (player.getPosition() == Player.Position.GOALIE) {
                    noOfGoalies = noOfGoalies + 1;
                } else {
                    noOfSkaters = noOfSkaters + 1;
                }
            }
        }

        if (noOfGoalies == 2 && noOfSkaters == 18) {
            isValid = true;
        }

        return isValid;
    }

    public boolean checkNumPlayer(List<Player> playerList) {
        boolean isValid = false;
        int goalieNum = 0;
        int forwardNum = 0;
        int defenseNum = 0;
        int playerNum = 0;
        for (Player player : playerList) {
            if (player.getPosition() == Player.Position.GOALIE) {
                goalieNum++;
            } else if (player.getPosition() == Player.Position.FORWARD) {
                forwardNum++;
            } else {
                defenseNum++;
            }
            playerNum++;
        }
        if (playerNum == 30 && goalieNum == 4 && forwardNum == 16 && defenseNum == 10) {
            isValid = true;
        }
        return isValid;
    }

    public int getTradeOfferCountOfSeason() {
        return tradeOfferCountOfSeason;
    }

    public void setTradeOfferCountOfSeason(int tradeOfferCountOfSeason) {
        this.tradeOfferCountOfSeason = tradeOfferCountOfSeason;
    }

    public int getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(int lossPoint) {
        this.lossPoint = lossPoint;
    }

}
