package simulation.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.data.ILeagueFactory;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.model.League;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LeagueDataSerializerDeSerializerTest {


    @Test
    public void serializeTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        League oldLeague = new League(4, leagueFactory);
        League newLeague = oldLeague;
        LeagueDataSerializerDeSerializer leagueDataSerializerDeSerializer = new LeagueDataSerializerDeSerializer();

        leagueDataSerializerDeSerializer.serialize(oldLeague);


        newLeague = leagueDataSerializerDeSerializer.deSerialize();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();
        String oldLeagueJSONString = new String();
        String newLeagueJSONString = new String();

        oldLeagueJSONString = gson.toJson(oldLeague);
        newLeagueJSONString = gson.toJson(newLeague);
        /*assertEquals(oldLeagueJSONString, newLeagueJSONString);
        assertNotEquals(oldLeagueJSONString, null);
        assertNotEquals(newLeagueJSONString, null);*/
    }
}
