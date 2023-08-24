package lee.code.central.managers;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ReplyManager {

    private final ConcurrentHashMap<UUID, UUID> lastMessage = new ConcurrentHashMap<>();

    public void setLastMessage(UUID playerID, UUID targetID) {
        lastMessage.put(playerID, targetID);
        lastMessage.put(targetID, playerID);
    }

    public boolean hasLastMessage(UUID playerID) {
        return lastMessage.containsKey(playerID);
    }

    public void removeLastMessage(UUID playerID) {
        lastMessage.remove(playerID);
    }

    public UUID getLastMessage(UUID playerID) {
        return lastMessage.get(playerID);
    }

}
