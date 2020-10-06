package state;

import model.HockeyContext;

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
        System.out.println("PlayerChoice State -> entry ");
    }

    @Override
    public void process() {
        System.out.println("PlayerChoice State -> Process ");
        userInput = "1";

    }

    @Override
    public IHockeyState exit() {

        switch (stateName)
        {
            case "importState":
            {
                if(userInput.equals("1")){
                    CreateTeamState createTeamState = new CreateTeamState(hockeyContext);
                    return createTeamState;
                }else if(userInput.equals("2")){
                    LoadTeamState loadTeamState = new LoadTeamState(hockeyContext);
                    return  loadTeamState;
                }
                break;
            }
            case "createOrLoadTeam":
            {
                return null;

            }
            default:
            {

            }
        }


        return null;
    }
}
