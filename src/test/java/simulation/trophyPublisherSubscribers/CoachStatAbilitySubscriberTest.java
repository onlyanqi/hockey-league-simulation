package simulation.trophyPublisherSubscribers;

import org.junit.Test;
import persistance.dao.ICoachDao;
import simulation.mock.CoachMock;
import simulation.model.Coach;
import simulation.model.ICoach;
import simulation.trophyPublisherSubsribers.CoachStatAbilitySubscriber;

import static org.junit.Assert.assertEquals;


public class CoachStatAbilitySubscriberTest {
    @Test
    public void updateTest() throws Exception {
        ICoachDao coachDao = new CoachMock();
        ICoach coach = new Coach(0, coachDao);
        int oldCoachCount = coach.getCoachingEffectiveness();
        CoachStatAbilitySubscriber coachStatAbilitySubscriber = new CoachStatAbilitySubscriber();
        coachStatAbilitySubscriber.update(coach, 1);
        int newCoachCount = coach.getCoachingEffectiveness();
        assertEquals(oldCoachCount + 1, newCoachCount);
    }
}
