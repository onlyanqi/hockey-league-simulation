package state;

public class TradeState implements ISimulateState{

    @Override
    public ISimulateState action() {
        System.out.println("Trading Players!");
        return new BuyPlayerState();
    }
}
