package simulation.model;

import db.data.IFreeAgentFactory;
import db.data.IPlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class FreeAgent extends ParentObj{

    transient String name;

    public FreeAgent(){}

    public FreeAgent(int id){
        setId(id);
    }

    public FreeAgent(int id, IFreeAgentFactory loadFreeAgentFactory) throws Exception {
        loadFreeAgentFactory.loadFreeAgentById(id, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private transient int seasonId;

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    private transient int leagueId;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    private List<Player> playerList;

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void addFreeAgent(IFreeAgentFactory addFreeAgentFactory) throws Exception {
        addFreeAgentFactory.addFreeAgent(this);
    }

    public void loadPlayerListByFreeAgentId(IPlayerFactory loadPlayerFactory) throws Exception {
        this.playerList = loadPlayerFactory.loadPlayerListByFreeAgentId(getId());
    }

    public List<Player> removeFreeAgentFromList(List<Player> playerList, int indexOfPlayerObject){
        for(int i=indexOfPlayerObject; i<playerList.size()-1;i++){
            Player player = new Player(playerList.get(i+1));
            playerList.remove(i);
            playerList.set(i, player);
        }
        playerList.remove(playerList.size()-1);
        return playerList;
    }

    public List<Integer> getGoodFreeAgentsList(List<Double> strengthList){

        Double thresholdPointForGoodPlayer=calculateAverage(strengthList);
        List<Integer> goodFreeAgentsIdList=new ArrayList<>();
        for(int i=0;i<strengthList.size();i++){
            if(strengthList.get(i)>=thresholdPointForGoodPlayer){
                goodFreeAgentsIdList.add(i);
            }
        }
        return goodFreeAgentsIdList;
    }

    public Double calculateAverage(List<Double> strengthList){
        Double average =0.0;
        for(int i=0;i<strengthList.size();i++){
            average=average+strengthList.get(i);
        }
        average=average/strengthList.size();
        return average;
    }
}
