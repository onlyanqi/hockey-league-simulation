package state;

import model.HockeyContext;

import java.util.Scanner;

public class CreateTeamState implements IHockeyState {

    private HockeyContext hockeyContext;


    public CreateTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        //Prompt Team Data
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter conference name the team belongs to");
        String conferenceName  = scanner.nextLine();

        System.out.println("Please enter division name the team belongs to");
        String divisionName  = scanner.nextLine();

        System.out.println("Please enter team name");
        String teamName  = scanner.nextLine();

        System.out.println("Please enter name of general manager");
        String generalManagerName  = scanner.nextLine();

        System.out.println("Please enter name of head coach ");
        String headCoachName  = scanner.nextLine();


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
