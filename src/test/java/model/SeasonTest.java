package model;

import data.ISeasonFactory;
import data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeasonTest {

    private static ISeasonFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new SeasonMock();
    }

    @Test
    public void defaultConstructorTest() {
        Season season = new Season();
        assertEquals(season.getId(), 0);
    }

    @Test
    public void seasonTest() {
        Season season = new Season(1);
        assertEquals(season.getId(), 1);
    }

    @Test
    public void seasonFactoryTest() throws Exception {
        Season season = new Season(1, factory);
        assertEquals(season.getId(), 1);
        assertEquals(season.getName(), "Season1");

        season = new Season(2, factory);
        assertNull(season.getName());
    }

}
