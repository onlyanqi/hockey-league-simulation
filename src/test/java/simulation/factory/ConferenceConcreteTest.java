package simulation.factory;

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


}
