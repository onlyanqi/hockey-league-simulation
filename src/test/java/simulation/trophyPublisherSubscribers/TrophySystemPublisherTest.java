package simulation.trophyPublisherSubscribers;

import org.junit.Test;
import simulation.dao.ICoachDao;
import simulation.mock.CoachMock;
import simulation.model.Coach;
import simulation.model.ICoach;
import simulation.trophyPublisherSubsribers.CoachStatAbilitySubscriber;
import simulation.trophyPublisherSubsribers.ITrophyEventListeners;
import simulation.trophyPublisherSubsribers.TrophySystemPublisher;
import static org.junit.Assert.*;

public class TrophySystemPublisherTest {

    @Test
    public void subscribeTest(){
        String eventType = "coachStatAbilityUpdate";
        ITrophyEventListeners coachStatAbilitySubscriber = new CoachStatAbilitySubscriber();
        TrophySystemPublisher trophySystemPublisher = new TrophySystemPublisher();
        int oldSubscribersLength = trophySystemPublisher.getListeners().size();
        trophySystemPublisher.subscribe(eventType,coachStatAbilitySubscriber);
        int newSubscribersLength = trophySystemPublisher.getListeners().size();
        assertEquals(oldSubscribersLength+1,newSubscribersLength);
    }

    @Test
    public void unsubscribeTest(){
        String eventType = "coachStatAbilityUpdate";
        ITrophyEventListeners coachStatAbilitySubscriber = new CoachStatAbilitySubscriber();
        TrophySystemPublisher trophySystemPublisher = new TrophySystemPublisher();
        trophySystemPublisher.subscribe(eventType,coachStatAbilitySubscriber);
        int oldSubscribersLength = trophySystemPublisher.getListeners().size();
        trophySystemPublisher.unsubscribe(eventType,coachStatAbilitySubscriber);
        int newSubscribersLength = trophySystemPublisher.getListeners().size();
        assertEquals(oldSubscribersLength-1,newSubscribersLength);
    }

    @Test
    public void notifyTest() throws Exception {
        String eventType = "coachStatAbilityUpdate";
        ITrophyEventListeners coachStatAbilitySubscriber = new CoachStatAbilitySubscriber();
        TrophySystemPublisher trophySystemPublisher = new TrophySystemPublisher();
        ICoachDao coachDao = new CoachMock();
        ICoach coach = new Coach(0,coachDao);
        int oldCoachCount = coach.getCoachingEffectiveness();
        trophySystemPublisher.subscribe(eventType,coachStatAbilitySubscriber);
        trophySystemPublisher.notify(eventType,coach,1);
        int newCoachCount = coach.getCoachingEffectiveness();
        assertEquals(oldCoachCount+1,newCoachCount);
    }
}
