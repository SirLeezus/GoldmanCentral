package lee.code.central.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PatrolManager {
  private final ConcurrentHashMap<UUID, Integer> lastChecked = new ConcurrentHashMap<>();

  public Player getNextPlayer(Player player) {
    final List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    final UUID uuid = player.getUniqueId();
    if (lastChecked.containsKey(uuid)) {
      final int next = lastChecked.get(uuid) + 1 > players.size() - 1 ? 0 : lastChecked.get(uuid) + 1;
      lastChecked.put(uuid, next);
      return selfCheck(player, players.get(next));
    }
    lastChecked.put(uuid, 0);
    return selfCheck(player, players.get(lastChecked.get(uuid)));
  }

  private Player selfCheck(Player player, Player target) {
    if (player.equals(target)) return getNextPlayer(player);
    else return target;
  }
}
