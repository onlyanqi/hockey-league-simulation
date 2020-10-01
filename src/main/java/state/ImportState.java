package state;

import model.HockeyContext;

public class ImportState implements IHockeyState {

    private HockeyContext hockeyContext;

    public ImportState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        //Parse JSON
        process();
    }

    @Override
    public void process() {
        //Instantiate and configure LOM
        exit();
    }

    @Override
    public void exit() {
        //Persist to DB and move to next state
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"Do you want to create a team or Load a team?","importState");
        hockeyContext.setHockeyState(playerChoiceState);
        playerChoiceState.entry();
    }
}
