package simulation.model;

import db.data.IFreeAgentDao;
import db.data.IPlayerDao;

import java.util.List;

public interface IFreeAgent {

    int getSeasonId();

    void setSeasonId(int seasonId);

    int getLeagueId();

    void setLeagueId(int leagueId);

    List<IPlayer> getPlayerList();

    void setPlayerList(List<IPlayer> playerList);

    void addFreeAgent(IFreeAgentDao addFreeAgentFactory) throws Exception;

    void loadPlayerListByFreeAgentId(IPlayerDao loadPlayerFactory) throws Exception;

    List<Integer> getGoodFreeAgentsList(List<Double> strengthList);

    Double calculateStrengthAverage(List<Double> strengthList);

    int getId();

    void setName(String name);

}
