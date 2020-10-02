package state;

import model.HockeyContext;
import model.Player;

public class PlayerChoiceState implements IHockeyState{

    private String input;
    private String stateName;
    private HockeyContext hockeyContext;
    private String userInput;

    public PlayerChoiceState(HockeyContext hockeyContext,String input,String stateName){
        this.input = input;
        this.stateName = stateName;
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        process();
    }

    @Override
    public void process() {

        userInput = "1";
        exit();
    }

    @Override
    public void exit() {

        switch (stateName)
        {
            case "importState":
            {
                if(userInput.equals("1")){
                    CreateTeamState createTeamState = new CreateTeamState(hockeyContext);
                    hockeyContext.setHockeyState(createTeamState);
                    createTeamState.entry();
                }else if(userInput.equals("2")){
                    LoadTeamState loadTeamState = new LoadTeamState(hockeyContext);
                    hockeyContext.setHockeyState(loadTeamState);
                    loadTeamState.entry();
                }
                break;
            }
            case "createOrLoadTeam":
            {
                hockeyContext.setHockeyState(null);
                break;
            }
            default:
            {

            }
        }



    }
}
