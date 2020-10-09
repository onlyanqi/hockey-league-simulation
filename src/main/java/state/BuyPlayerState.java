package state;

public class BuyPlayerState implements ISimulateState{
    @Override
    public ISimulateState action() {
        System.out.println("Buying players!");
        return null;
    }
}
