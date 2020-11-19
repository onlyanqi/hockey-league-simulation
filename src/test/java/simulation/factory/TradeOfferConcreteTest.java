package simulation.factory;

import db.data.ITradeOfferFactory;
import org.junit.Test;
import simulation.model.SharedAttributes;
import simulation.model.TradeOffer;

import static org.junit.Assert.assertTrue;

public class TradeOfferConcreteTest {

    @Test
    public void newTradeOfferTest() {
        TradeOfferConcrete tradeOfferConcrete = new TradeOfferConcrete();
        assertTrue(tradeOfferConcrete.newTradeOffer() instanceof TradeOffer);
        assertTrue(tradeOfferConcrete.newTradeOffer() instanceof SharedAttributes);
    }


}
