package simulation.mock;

import db.data.*;
import simulation.model.*;

public class GamePlayConfigMock implements IGamePlayConfigFactory {

    public Trading getTrading() throws Exception {
        ITradingFactory tradingFactory = new TradingMock();
        Trading trading = new Trading(1, tradingFactory);
        return trading;
    }

    public Injury getInjury() throws Exception {
        IInjuryFactory injuryFactory = new InjuryMock();
        Injury injury = new Injury(1, injuryFactory);
        return injury;
    }

    public Aging getAging() throws Exception {
        IAgingDao agingFactory = new AgingMock();
        Aging aging = new Aging(1, agingFactory);
        return aging;
    }

    public Training getTraining() throws Exception {
        ITrainingFactory trainingFactory = new TrainingMock();
        Training training = new Training(1, trainingFactory);
        return training;
    }

    @Override
    public void loadGamePlayConfigByLeagueId(int leagueId, GamePlayConfig gamePlayConfig) throws Exception {
        switch (leagueId) {
            case 1:
                gamePlayConfig.setTrading(getTrading());
                gamePlayConfig.setTraining(getTraining());
                gamePlayConfig.setAging(getAging());
                gamePlayConfig.setInjury(getInjury());
                break;
        }
    }

}
