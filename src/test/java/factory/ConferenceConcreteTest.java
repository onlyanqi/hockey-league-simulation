package factory;

import dao.AddConferenceDao;
import dao.LoadConferenceDao;
import model.Conference;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ConferenceConcreteTest {

    private ConferenceConcrete conferenceConcrete;

    @Before
    public void init(){
        conferenceConcrete = new ConferenceConcrete();
    }

    @Test
    public void newConferenceTest(){
        assertTrue(conferenceConcrete.newConference() instanceof Conference);
    }

    @Test
    public void newLoadConferenceFactoryTest(){
        assertTrue(conferenceConcrete.newLoadConferenceFactory() instanceof  LoadConferenceDao);
    }

    @Test
    public void newAddConferenceFactoryTest() {
        assertTrue(conferenceConcrete.newAddConferenceFactory() instanceof AddConferenceDao);
    }

}
