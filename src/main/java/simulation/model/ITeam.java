package simulation.model;

import simulation.dao.IPlayerDao;
import simulation.dao.ITeamDao;

import java.util.List;

public interface ITeam {

    List<IPlayer> getPlayerList();

    void setPlayerList(List<IPlayer> playerList) throws IllegalArgumentException;

    List<IPlayer> getActivePlayerList();

    void setActivePlayerList();

    List<IPlayer> getInactivePlayerList();

    void setInactivePlayerList(List<IPlayer> inactivePlayerList);

    String getMascot();

    void setMascot(String mascot);

    int getDivisionId();

    void setDivisionId(int divisionId);

    ICoach getCoach();

    void setCoach(ICoach coach);

    IManager getManager();

    void setManager(IManager manager);

    void addTeam(ITeamDao addTeamFactory) throws Exception;

    void setStrength();

    double getStrength();

    boolean isAiTeam();

    void setAiTeam(boolean aiTeam);

    void loadPlayerListByTeamId(IPlayerDao loadPlayerFactory) throws Exception;

    List<Integer> createChosenPlayerIdList(IFreeAgent freeAgent);

    List<Double> createStrengthList(List<IPlayer> freeAgentList);

    boolean checkNumPlayer(List<IPlayer> playerList);

    int getPlayersTradedCount();

    void setPlayersTradedCount(int playersTradedCount);

    int getLossPoint();

    void setLossPoint(int lossPoint);

    void fixTeamAfterTrading(List<IPlayer> freeAgentList);

    String getName();

    void setName(String name);

    int getId();

    void setId(int id);

    List<String> getDraftPicks();

    void setDraftPicks(List<String> draftPicks);
}
