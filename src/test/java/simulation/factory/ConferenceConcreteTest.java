package simulation.factory;

import db.dao.ConferenceDao;
import org.junit.Before;
import org.junit.Test;
import simulation.model.Conference;

import static org.junit.Assert.assertTrue;

public class ConferenceConcreteTest {

    private ConferenceConcrete conferenceConcrete;

    @Before
    public void init() {
        conferenceConcrete = new ConferenceConcrete();
    }

    @Test
    public void newConferenceTest() {
        assertTrue(conferenceConcrete.newConference() instanceof Conference);
    }

    @Test
    public void newLoadConferenceFactoryTest() {
        assertTrue(conferenceConcrete.newLoadConferenceFactory() instanceof ConferenceDao);
    }

    @Test
    public void newAddConferenceFactoryTest() {
        assertTrue(conferenceConcrete.newAddConferenceFactory() instanceof ConferenceDao);
    }

}
