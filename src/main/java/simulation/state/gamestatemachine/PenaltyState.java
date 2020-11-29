package simulation.state.gamestatemachine;

import org.apache.log4j.Logger;
import simulation.model.*;
import simulation.trophyPublisherSubsribers.TrophySystemPublisher;
import java.util.Random;

public class PenaltyState extends GameState {

    static Logger log = Logger.getLogger(PenaltyState.class);
    Random rand;
    GameContext gameContext;
    IShift offensive;
    IShift defensive;
    IGameSimulation gameSimulation;

    public PenaltyState(GameContext gameContext) {
        rand = new Random();
        this.gameContext = gameContext;
        this.gameSimulation = gameContext.getGameSimulation();
        offensive = gameContext.getOffensive();
        defensive = gameContext.getDefensive();
    }

    public GameState process() throws Exception {
        if(offensive==null || defensive==null){
            log.error("Error while simulating game.Offensive or Defensive are not set.");
            throw new IllegalStateException("Offensive or Defensive are null.");
        }
        IPlayer defensePlayer = defensive.getDefense().get(rand.nextInt(defensive.getDefense().size()));
        updateTrophyPublisher(defensePlayer);
        gameSimulation.addToPenaltyBox(defensive,defensePlayer);
        gameSimulation.getPenalties().put(defensive.getTeamName(), gameSimulation.getPenalties().get(defensive.getTeamName()) + 1);
        return next();
    }

    private void updateTrophyPublisher(IPlayer randDefense) {
        TrophySystemPublisher.getInstance().notify("penaltyCountUpdate",randDefense,1);
    }

    public GameState next(){
        return new FinalState();
    }
}
