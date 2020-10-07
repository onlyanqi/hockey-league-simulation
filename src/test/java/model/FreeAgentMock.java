package model;

import data.IFreeAgentFactory;
import data.IPlayerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreeAgentMock implements IFreeAgentFactory {

    private List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        IPlayerFactory playerFactory = new PlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        return playerList;
    }

    @Override
    public int addFreeAgent(FreeAgent freeAgent) throws Exception {
        freeAgent = new FreeAgent();
        freeAgent.setId(1);
        freeAgent.setName("FreeAgent1");
        return freeAgent.getId();
    }

    @Override
    public void loadFreeAgentByLeagueId(int id, FreeAgent freeAgent) throws Exception {

        switch (new Long(id).intValue()){
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
