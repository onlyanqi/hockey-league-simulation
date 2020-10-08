package model;

import data.ILoadFreeAgentFactory;
import data.ILoadPlayerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadFreeAgentMock implements ILoadFreeAgentFactory {

    private List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        ILoadPlayerFactory playerFactory = new LoadPlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        return playerList;
    }

    @Override
    public void loadFreeAgentByLeagueId(int leagueId, FreeAgent freeAgent) throws Exception {

        switch (new Long(leagueId).intValue()){
            case 1:
                //all correct data
                freeAgent.setName("freeAgent1");
                freeAgent.setStartDate(new Date(2000, 0, 0));
                freeAgent.setEndDate(new Date(2050, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 2:
                //name null
                freeAgent.setName(null);
                freeAgent.setStartDate(new Date(2000, 0, 0));
                freeAgent.setEndDate(new Date(2050, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 3:
                //end date less than start date
                freeAgent.setName("Invalid Date");
                freeAgent.setStartDate(new Date(2010, 0, 0));
                freeAgent.setEndDate(new Date(2000, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;

            case 4:
                //invalid position
                freeAgent.setName("Invalid Postion");
                freeAgent.setStartDate(new Date(2010, 0, 0));
                freeAgent.setEndDate(new Date(2000, 0, 0));
                freeAgent.setPlayerList(formPlayerList());
                break;
        }

    }

}
