package lee.code.central.managers;

import lee.code.central.Central;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DelayManager {

    private final Central central;

    private final ConcurrentHashMap<UUID, Long> playersOnDelay = new ConcurrentHashMap<>();

    public DelayManager(Central central) {
        this.central = central;
    }

    public boolean isOnDelay(UUID uuid) {
        return playersOnDelay.containsKey(uuid);
    }

    public void setOnDelay(UUID uuid, long delay) {
        playersOnDelay.put(uuid, System.currentTimeMillis() + delay);
        scheduleDelay(uuid, delay);
    }

    public String getRemainingTime(UUID uuid) {
        return CoreUtil.parseTime(playersOnDelay.get(uuid) - System.currentTimeMillis());
    }

    private void scheduleDelay(UUID uuid, long delay) {
        Bukkit.getServer().getAsyncScheduler().runDelayed(central, scheduledTask ->
                playersOnDelay.remove(uuid), delay, TimeUnit.MILLISECONDS);
    }

}
