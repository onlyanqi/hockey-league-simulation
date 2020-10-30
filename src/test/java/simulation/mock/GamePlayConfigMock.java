package simulation.mock;

import db.data.IGamePlayConfigFactory;
import db.data.ITradingFactory;
import simulation.model.*;

public class GamePlayConfigMock implements IGamePlayConfigFactory {

    public void loadGamePlayConfigByLeagueId(int leagueId, GamePlayConfig gamePlayConfig) throws Exception {

        ITradingFactory tradingFactory = new TradingMock();
        Trading trading = new Trading(1, tradingFactory);

        switch (leagueId) {
            case 1:
                gamePlayConfig.setTrading(trading);
                break;
        }
    }

}
