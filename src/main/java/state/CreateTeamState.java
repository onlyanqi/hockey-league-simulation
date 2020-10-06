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
        System.out.println("CreateTeam State -> Entry ");
    }

    @Override
    public void process() {
        //Instantiate Model Objects
        System.out.println("CreateTeam State -> Process ");
    }

    @Override
    public IHockeyState exit() {
        //Persist to DB and transition to next state
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        System.out.println("CreateTeam State -> Exit ");
        return playerChoiceState;
    }
}
