package model;

import data.IConferenceFactory;
import data.IDivisionFactory;
import data.ILeagueFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeagueTest {

    private static ILeagueFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new LeagueMock();
    }

    @Test
    public void defaultConstructorTest() {
        League league = new League();
        assertEquals(league.getId(), 0);
    }

    @Test
    public void leagueTest() {
        League league = new League(1);
        assertEquals(league.getId(), 1);
    }

    @Test
    public void leagueFactoryTest(){
        League league = new League(1, factory);
        assertEquals(league.getId(), 1);
        assertEquals(league.getName(), "League1");

        league = new League(2, factory);
        assertNull(league.getName());
    }

    @Test
    public void getCountryTest(){
        League league = new League(1, factory);
        assertTrue(league.getCountry().equals("Canada"));
    }

    @Test
    public void setCountryTest(){
        League league = new League();
        String country = "Canada";
        league.setCountry(country);
        assertTrue(league.getCountry().equals(country));
    }

    @Test
    public void getConferenceListTest(){
        League league = new League(1, factory);
        List<Conference> conferenceList = league.getConferenceList();
        assertNotNull(conferenceList);

        assertTrue(conferenceList.get(0).getId() == (1));
        assertTrue(conferenceList.get(1).getId() == (2));
        assertTrue(conferenceList.get(0).getName().equals("Conference1"));
        assertNull(conferenceList.get(1).getName());
    }

    @Test
    public void setConferenceListTest(){
        IConferenceFactory conferenceFactory = new ConferenceMock();
        List<Conference> conferenceList = new ArrayList<>();
        Conference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);
        conference = new Conference(2, conferenceFactory);
        conferenceList.add(conference);

        League league = new League();
        league.setConferenceList(conferenceList);

        assertTrue(league.getConferenceList().get(0).getId() == (1));
        assertTrue(league.getConferenceList().get(1).getId() == (2));
        assertTrue(league.getConferenceList().get(0).getName().equals("Conference1"));
        assertNull(league.getConferenceList().get(1).getName());
    }


}
