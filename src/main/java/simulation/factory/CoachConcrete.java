package simulation.factory;

import simulation.model.Coach;

public class CoachConcrete implements ICoachFactory{

    @Override
    public Coach newCoach() {
        return new Coach();
    }

     @Override
     public Coach newCoachWithCoach(Coach coach) {
         return new Coach(coach);
     }
 }
