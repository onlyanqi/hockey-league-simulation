package simulation.factory;

import simulation.model.GameSchedule;
import simulation.model.IGameSchedule;

public class GameScheduleConcrete implements IGameScheduleFactory {

    @Override
    public IGameSchedule newGameSchedule() {
        return new GameSchedule();
    }

}
