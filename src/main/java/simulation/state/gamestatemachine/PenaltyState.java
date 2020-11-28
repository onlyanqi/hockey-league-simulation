package simulation.state.gamestatemachine;

import simulation.model.GameSimulation;
import simulation.model.IPlayer;
import simulation.model.Shift;
import simulation.trophyPublisherSubsribers.TrophySystemPublisher;
import java.util.Random;

public class PenaltyState extends GameState {

    Random rand;
    GameContext gameContext;
    Shift offensive;
    Shift defensive;
    GameSimulation gameSimulation;

    public PenaltyState(GameContext gameContext) {
        rand = new Random();
        this.gameContext = gameContext;
        this.gameSimulation = gameContext.getGameSimulation();
        offensive = gameContext.getOffensive();
        defensive = gameContext.getDefensive();
    }

    public GameState process() throws Exception {
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
