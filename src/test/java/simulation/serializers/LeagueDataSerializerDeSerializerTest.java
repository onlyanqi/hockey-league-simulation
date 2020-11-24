package simulation.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import simulation.dao.ILeagueDao;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.model.ILeague;
import simulation.model.League;
import simulation.state.IHockeyContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LeagueDataSerializerDeSerializerTest {


    @Test
    public void serializeTest() throws Exception {
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        IHockeyContext hockeyContext = hockeyContextFactory.newHockeyContext();
        ILeagueDao leagueFactory = new LeagueMock();
        ILeague oldLeague = new League(4, leagueFactory);
        ILeague newLeague = oldLeague;
        LeagueDataSerializerDeSerializer leagueDataSerializerDeSerializer = new LeagueDataSerializerDeSerializer();

        leagueDataSerializerDeSerializer.serialize(oldLeague);
        newLeague = leagueDataSerializerDeSerializer.deSerialize("JsonFiles/"+oldLeague.getUserCreatedTeamName(), hockeyContext);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();
        String oldLeagueJSONString = new String();
        String newLeagueJSONString = new String();

        oldLeagueJSONString = gson.toJson(oldLeague);
        newLeagueJSONString = gson.toJson(newLeague);
        assertEquals(oldLeagueJSONString, newLeagueJSONString);
        assertNotEquals(oldLeagueJSONString, null);
        assertNotEquals(newLeagueJSONString, null);
    }
}
