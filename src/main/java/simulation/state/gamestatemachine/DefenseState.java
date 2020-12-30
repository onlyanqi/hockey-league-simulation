package simulation.state.gamestatemachine;

import org.apache.log4j.Logger;
import simulation.model.IShift;
import simulation.model.ISimulate;
import simulation.state.HockeyContext;

import java.util.Random;

public class DefenseState extends GameState {

    static Logger log = Logger.getLogger(DefenseState.class);
    Random rand;
    GameContext gameContext;
    IShift offensive;
    IShift defensive;
    ISimulate simulateConfig;
    boolean defend;


    public DefenseState(GameContext gameContext) {
        simulateConfig = HockeyContext.getInstance().getUser().getLeague().getGamePlayConfig().getSimulate();
        this.gameContext = gameContext;
        offensive = gameContext.getOffensive();
        defensive = gameContext.getDefensive();
        this.rand = new Random();
    }

    public GameState process() throws Exception {
        if (offensive == null || defensive == null) {
            log.error("Error while simulating game.Offensive or Defensive are not set.");
            throw new IllegalStateException("Offensive or Defensive are null.");
        }
        defend = offensive.getTeamShiftDefenseTotal() <= defensive.getTeamShiftDefenseTotal();
        if (rand.nextDouble() < simulateConfig.getUpset()) {
            reverseDefending();
        }
        defend = defend && (rand.nextDouble() < simulateConfig.getDefendChance());
        return next();
    }

    public GameState next() {
        if (defend) {
            if (rand.nextDouble() < simulateConfig.getPenaltyChance()) {
                return new PenaltyState(gameContext);
            }
        } else {
            return new GoalState(gameContext);
        }
        return new FinalState();
    }

    private boolean reverseDefending() {
        return !defend;
    }
}
