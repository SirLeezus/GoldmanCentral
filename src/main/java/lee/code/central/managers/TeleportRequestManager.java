package lee.code.central.managers;

import lee.code.central.Central;
import lee.code.central.lang.Lang;
import lee.code.colors.ColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class TeleportRequestManager {

    private final Central central;

    public TeleportRequestManager(Central central) {
        this.central = central;
    }

    private final ConcurrentHashMap<UUID, Set<UUID>> teleportRequests = new ConcurrentHashMap<>();

    private void setTeleportRequest(UUID playerID, UUID targetID) {
        if (teleportRequests.containsKey(playerID)) {
            teleportRequests.get(playerID).add(targetID);
        } else {
            final Set<UUID> requests = ConcurrentHashMap.newKeySet();
            requests.add(targetID);
            teleportRequests.put(playerID, requests);
        }
    }

    public boolean hasActiveRequest(UUID playerID, UUID targetID) {
        if (!teleportRequests.containsKey(playerID)) return false;
        return teleportRequests.get(playerID).contains(targetID);
    }

    public void setActiveRequest(UUID playerID, UUID targetID) {
        setTeleportRequest(playerID, targetID);
        requestTimeoutTimer(playerID, targetID);
    }

    public void removeActiveRequest(UUID playerID, UUID targetID) {
        teleportRequests.get(playerID).remove(targetID);
        if (teleportRequests.get(playerID).isEmpty()) teleportRequests.remove(playerID);
    }

    public void requestTimeoutTimer(UUID playerID, UUID targetID) {
        Bukkit.getServer().getAsyncScheduler().runDelayed(central, scheduledTask -> {
            if (hasActiveRequest(playerID, targetID)) {
                final OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(playerID);
                final OfflinePlayer oTarget = Bukkit.getOfflinePlayer(targetID);
                if (oPlayer.isOnline()) {
                    final Player player = oPlayer.getPlayer();
                    if (player != null) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_TELEPORT_ASK_REQUEST_TIMEOUT_PLAYER.getComponent(new String[] { ColorAPI.getNameColor(targetID, oTarget.getName()) })));
                }
                if (oTarget.isOnline()) {
                    final Player target = oTarget.getPlayer();
                    if (target != null) target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_TELEPORT_ASK_REQUEST_TIMEOUT_TARGET.getComponent(new String[] { ColorAPI.getNameColor(playerID, oPlayer.getName()) })));
                }
                removeActiveRequest(playerID, targetID);
            }
        }, 60, TimeUnit.SECONDS);
    }
}
