package model;

import state.CreateTeamState;
import state.IHockeyState;
import state.ImportState;

public class HockeyContext {

    private IHockeyState hockeyState;

    private League league;



    public void startAction(boolean filePathProvided){
        hockeyState = new ImportState(this);

        hockeyState.entry();
        hockeyState.process();

        if(filePathProvided){
            hockeyState = new CreateTeamState(this);
        }else{
            hockeyState = hockeyState.exit();
        }



        do{
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        }while(hockeyState!=null);
    }


    public IHockeyState getHockeyState() {
        return hockeyState;
    }
}
