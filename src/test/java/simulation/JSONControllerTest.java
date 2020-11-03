package simulation;

import org.json.simple.JSONObject;
import org.junit.Test;
import simulation.mock.JSONControllerMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class JSONControllerTest {

    @Test
    public void readJSONTest() {
        JSONObject leagueJSON = JSONControllerMock.getJSON(1);
        assertNotNull(leagueJSON);
        assertEquals(leagueJSON.get("leagueName"), "Dalhousie Hockey League");
    }
}