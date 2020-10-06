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
        System.out.println("Import State -> Entry ");
    }

    @Override
    public void process() {
        //Instantiate and configure LOM
        System.out.println("Import State -> Process ");
    }

    @Override
    public IHockeyState exit() {
        //Persist to DB and move to next state
        System.out.println("Import State -> Exit ");
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"Do you want to create a team or Load a team?","importState");
        return playerChoiceState;
    }
}
