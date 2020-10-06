package state;

import model.HockeyContext;

public class InternalState implements IHockeyState {

    private HockeyContext hockeyContext;

    public InternalState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        //Parse JSON
        System.out.println("Internal State -> Entry ");
    }

    @Override
    public void process() {
        //Instantiate and configure LOM
        System.out.println("Internal State -> Process ");
    }

    @Override
    public IHockeyState exit() {
        //Persist to DB and move to next state
        System.out.println("Internal State -> Exit Method ");
        return null;
    }
}
