package state;

import model.HockeyContext;

public class InternalState implements IHockeyState {

    private HockeyContext hockeyContext;

    public InternalState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {

        System.out.println("Simulating Season");
    }

    @Override
    public void process() {


    }

    @Override
    public IHockeyState exit() {

        return null;
    }
}
