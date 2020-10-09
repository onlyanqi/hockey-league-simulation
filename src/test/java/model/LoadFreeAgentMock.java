package model;

import data.ILoadFreeAgentFactory;
import data.ILoadPlayerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadFreeAgentMock implements ILoadFreeAgentFactory {

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        ILoadPlayerFactory playerFactory = new LoadPlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        return playerList;
    }

    @Override
    public void loadFreeAgentById(int id, FreeAgent freeAgent) throws Exception {

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                freeAgent.setName("freeAgent1");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setStartDate(new Date(2000, 0, 0));
                freeAgent.setEndDate(new Date(2050, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 2:
                //name null
                freeAgent.setName(null);
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setStartDate(new Date(2000, 0, 0));
                freeAgent.setEndDate(new Date(2050, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 3:
                //end date less than start date
                freeAgent.setName("Invalid Date");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setStartDate(new Date(2010, 0, 0));
                freeAgent.setEndDate(new Date(2000, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 4:
                //invalid position
                freeAgent.setName("Invalid Postion");
                freeAgent.setSeasonId(1);
                freeAgent.setLeagueId(1);
                freeAgent.setStartDate(new Date(2010, 0, 0));
                freeAgent.setEndDate(new Date(2000, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;
        }
    }

    @Override
    public FreeAgent loadFreeAgentByLeagueId(int id) throws Exception {
        FreeAgent freeAgent = new FreeAgent();
        freeAgent.setLeagueId(id);
        freeAgent.setName("FreeAgent1");
        freeAgent.setStartDate(new Date(2010, 0, 0));
        freeAgent.setEndDate(new Date(2000, 0, 0));
        freeAgent.setPlayerList(formPlayerList());
        return freeAgent;
    }

}
