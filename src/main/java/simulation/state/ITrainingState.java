package simulation.state;

import simulation.model.ICoach;
import simulation.model.ILeague;
import simulation.model.IPlayer;

public interface ITrainingState {
    void statIncreaseCheck(ILeague league);

    void statIncreaseCheckForPlayer(IPlayer player, ICoach headCoach);

    boolean isStrengthInRangeAfterIncrease(int strengthAfterIncrease);
}
