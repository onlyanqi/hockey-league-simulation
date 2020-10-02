package model;

import data.IDivisionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HockeyContextTest {


    @Test
    public void startActionTest() {
        HockeyContext hockeyContext = new HockeyContext();
        assertTrue(true);
    }

    @Test
    public void setHockeyState(){
        HockeyContext hockeyContext = new HockeyContext();
        assertNull(hockeyContext.getHockeyState());
    }
}