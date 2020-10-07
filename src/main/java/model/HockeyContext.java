package model;

import state.CreateTeamState;
import state.IHockeyState;
import state.ImportState;
import state.LoadTeamState;

public class HockeyContext {

    private IHockeyState hockeyState;



    private League league;

    public HockeyContext(){
        league = new League();
    }


    public void startAction(String filePath){

        if(filePath.length() > 0){
            hockeyState = new ImportState(this,filePath);
            hockeyState.entry();
            hockeyState.process();
            hockeyState = new CreateTeamState(this);
        }else{
            hockeyState = new LoadTeamState(this);
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        }

        do{
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        }while(hockeyState!=null);

    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }


    public IHockeyState getHockeyState() {
        return hockeyState;
    }
}
