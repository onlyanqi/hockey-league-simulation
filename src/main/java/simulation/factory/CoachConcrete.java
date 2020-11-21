package simulation.factory;

import simulation.model.Coach;
import simulation.model.ICoach;

public class CoachConcrete implements ICoachFactory{

    @Override
    public ICoach newCoach() {
        return new Coach();
    }

     @Override
     public ICoach newCoachWithCoach(ICoach coach) {
         return new Coach(coach);
     }
 }
