package simulation.serializers;

import com.google.gson.*;
import simulation.model.Player;

import java.lang.reflect.Type;

public class PlayerSerializer implements JsonSerializer<Player> {

    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObjectPlayer = (JsonObject) new GsonBuilder().create().toJsonTree(player);

        if (player.isFreeAgent()) {
            jsonObjectPlayer.remove("captain");
        }

        return jsonObjectPlayer;
    }
}
