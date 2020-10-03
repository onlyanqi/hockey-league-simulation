package state;

import model.HockeyContext;

public class CreateTeamState implements IHockeyState {

    private HockeyContext hockeyContext;


    public CreateTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        //Prompt Team Data
        process();
    }

    @Override
    public void process() {
        //Instantiate Model Objects
        exit();
    }

    @Override
    public void exit() {
        //Persist to DB and transition to next state
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        hockeyContext.setHockeyState(playerChoiceState);
        playerChoiceState.entry();
    }
}
