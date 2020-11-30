package simulation.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import simulation.dao.ILeagueDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.model.ILeague;
import simulation.model.League;
import simulation.serializers.ModelsForDeserialization.model.LeagueDeserializationModel;
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
        LeagueDataSerializerDeSerializer leagueDataSerializerDeSerializer = new LeagueDataSerializerDeSerializer();

        leagueDataSerializerDeSerializer.serialize(oldLeague);
        LeagueDeserializationModel newLeagueTest = leagueDataSerializerDeSerializer.deSerialize("JsonFiles/" + oldLeague.getUserCreatedTeamName());

        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();
        String oldLeagueJSONString = "";
        String newLeagueJSONString = "";

        oldLeagueJSONString = gson.toJson(oldLeague);
        newLeagueJSONString = gson.toJson(newLeagueTest);
        assertEquals(oldLeagueJSONString, newLeagueJSONString);
        assertNotEquals(oldLeagueJSONString, null);
        assertNotEquals(newLeagueJSONString, null);
    }
}
