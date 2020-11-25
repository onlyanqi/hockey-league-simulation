package simulation.TrophyPublisherSubsribers;

import simulation.model.Coach;

public class CoachStatAbilitySubscriber implements ITrophyEventListeners{

    @Override
    public void update(Object object, Integer count) {
        Coach coach = (Coach) object;
        coach.setCoachingEffectiveness(coach.getCoachingEffectiveness()+count);
    }
}