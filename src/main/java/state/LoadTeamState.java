package state;

import model.HockeyContext;

public class LoadTeamState implements IHockeyState {

    private String input;
    private HockeyContext hockeyContext;
    private String teamName;


    public LoadTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        //prompt team name
        process();
    }

    @Override
    public void process() {
        //Load Team Data from DB
        exit();
    }

    @Override
    public void exit() {
        //Instantiate Model Objects and transition state
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        hockeyContext.setHockeyState(playerChoiceState);
        playerChoiceState.entry();
    }
}
