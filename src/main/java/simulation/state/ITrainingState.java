package simulation.state;

import simulation.model.*;

public interface ITrainingState {
    void statIncreaseCheck(ILeague league);

    void statIncreaseCheckForPlayer(IPlayer player, ICoach headCoach);

    boolean isStrengthInRangeAfterIncrease(int strengthAfterIncrease);
}
