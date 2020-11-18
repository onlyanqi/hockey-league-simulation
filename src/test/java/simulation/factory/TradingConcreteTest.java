package simulation.factory;

import db.data.ITradingFactory;
import org.junit.Test;
import simulation.model.SharedAttributes;
import simulation.model.Trading;

import static org.junit.Assert.assertTrue;

public class TradingConcreteTest {

    @Test
    public void newTradingTest() {
        TradingConcrete tradingConcrete = new TradingConcrete();
        assertTrue(tradingConcrete.newTrading() instanceof Trading);
        assertTrue(tradingConcrete.newTrading() instanceof SharedAttributes);
    }

}
