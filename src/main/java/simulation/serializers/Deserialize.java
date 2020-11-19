/*
package simulation.serializers;

import com.google.gson.*;
import simulation.model.Aging;
import simulation.model.IAging;
import simulation.model.League;

import java.lang.reflect.Type;

public class Deserialize implements JsonDeserializer<League> {

    @Override
    public League deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jObject = jsonElement.getAsJsonObject();
        try {
            return new League();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
*/
