package model;

import org.json.simple.JSONObject;
import state.CreateTeamState;
import state.IHockeyState;
import state.ImportState;
import state.LoadTeamState;

public class HockeyContext {

    private IHockeyState hockeyState;
    private User user;
    private League league;

    public void setHockeyState(IHockeyState hockeyState) {
        this.hockeyState = hockeyState;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HockeyContext(User user){
        league = new League();
        this.user = user;
    }

    public void startAction(JSONObject jsonFromInput) throws Exception {

        if(jsonFromInput!= null){
            hockeyState = new ImportState(this,jsonFromInput);
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
