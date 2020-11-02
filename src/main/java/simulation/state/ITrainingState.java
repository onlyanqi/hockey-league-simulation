package simulation.state;

import simulation.model.Coach;
import simulation.model.League;
import simulation.model.Player;

public interface ITrainingState {
    void statIncreaseCheck(League league);

    void statIncreaseCheckForPlayer(Player player, Coach headCoach);

    boolean isStrengthInRangeAfterIncrease(int strengthAfterIncrease);
}
