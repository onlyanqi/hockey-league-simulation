package model;

import state.IHockeyState;
import state.ImportState;

public class HockeyContext {

    private IHockeyState hockeyState;

    private League league;



    public void startAction(){
        hockeyState = new ImportState(this);
        do{
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        }while(hockeyState!=null);
    }

    public void setHockeyState(IHockeyState hockeyState) {
        if(hockeyState != null){
            this.hockeyState = hockeyState;
        }else {
            // fill the following later.  rn used for Unit Test Case to Pass
            this.hockeyState = null;
        }
    }

    public IHockeyState getHockeyState() {
        return hockeyState;
    }
}
