package state;

import model.HockeyContext;

import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter team name");
        String teamName = scanner.nextLine();

    }

    @Override
    public void process() {
        //Load Team Data from DB
        System.out.println("LoadTeam State -> Process ");
    }

    @Override
    public IHockeyState exit() {
        //Instantiate Model Objects and transition state
        System.out.println("LoadTeam State -> Exit ");
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        return playerChoiceState;
    }
}
