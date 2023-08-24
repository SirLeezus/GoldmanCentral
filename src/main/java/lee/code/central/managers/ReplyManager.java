package lee.code.central.managers;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ReplyManager {

    private final ConcurrentHashMap<UUID, UUID> lastMessage = new ConcurrentHashMap<>();

    public void setLastMessage(UUID owner, UUID target) {
        lastMessage.put(owner, target);
        lastMessage.put(target, owner);
    }

    public boolean hasLastMessage(UUID owner) {
        return lastMessage.containsKey(owner);
    }

    public void removeLastMessage(UUID owner) {
        lastMessage.remove(owner);
    }

    public UUID getLastMessage(UUID owner) {
        return lastMessage.get(owner);
    }

}
