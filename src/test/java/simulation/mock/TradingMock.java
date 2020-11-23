package simulation.mock;

import db.data.ITradingDao;
import simulation.model.ITrading;
import simulation.model.Trading;

import java.util.Arrays;
import java.util.Date;

public class TradingMock implements ITradingDao {

    private ITrading getTrading(int leagueId, int tradingId, ITrading trading) {
        trading.setId(1);
        trading.setLeagueId(1);
        trading.setLossPoint(2);
        trading.setMaxPlayersPerTrade(3);
        trading.setRandomAcceptanceChance(0.05f);
        trading.setRandomTradeOfferChance(0.05f);
        trading.setTradingPeriod(true);
        trading.setCurrentYearSeasonMonths(Arrays.asList(9, 10, 11));
        trading.setNextYearSeasonMonths(Arrays.asList(0, 1));
        trading.setTradeStartDate(new Date((2020 - 1900), 9, 1));
        trading.setTradeEndDate(new Date((2021 - 1900), 1, 22));
        return trading;
    }

    @Override
    public int addTradingDetails(ITrading trading) {
        trading = getTrading(1, 1, trading);
        return trading.getId();
    }

    @Override
    public ITrading loadTradingDetailsByLeagueId(int leagueId) {
        ITrading trading = new Trading();
        trading = getTrading(leagueId, 1, trading);

        switch (leagueId) {
            case 2:
                trading.setId(2);
                trading.setTradingPeriod(false);
                break;
            case 3:
                trading.setId(3);
                trading.setRandomAcceptanceChance(1.0f);
                trading.setRandomTradeOfferChance(1.0f);
                break;
            case 4:
                trading.setId(4);
                trading.setRandomAcceptanceChance(1.0f);
                trading.setRandomTradeOfferChance(1.0f);
                trading.setTradingPeriod(false);
                break;
            case 5:
                trading.setId(5);
                trading.setRandomAcceptanceChance(0.0f);
                trading.setRandomTradeOfferChance(0.0f);
                break;
        }
        return trading;
    }

    @Override
    public void loadTradingDetailsByTradingId(int tradingId, ITrading trading) {

        trading = getTrading(1, tradingId, trading);

        switch (tradingId) {
            case 2:
                trading.setLeagueId(2);
                trading.setTradingPeriod(false);
                break;
            case 3:
                trading.setLeagueId(3);
                trading.setRandomAcceptanceChance(1.0f);
                trading.setRandomTradeOfferChance(1.0f);
                break;
            case 4:
                trading.setLeagueId(4);
                trading.setRandomAcceptanceChance(1.0f);
                trading.setRandomTradeOfferChance(1.0f);
                trading.setTradingPeriod(false);
                break;
            case 5:
                trading.setLeagueId(5);
                trading.setRandomAcceptanceChance(0.0f);
                trading.setRandomTradeOfferChance(0.0f);
                break;
        }
    }
}
