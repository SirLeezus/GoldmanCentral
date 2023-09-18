package lee.code.central.managers;

import lee.code.central.Central;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishManager {
  private final Central central;
  final Set<UUID> vanishedPlayers = new HashSet<>();

  public VanishManager(Central central) {
    this.central = central;
  }

  public void addVanished(Player player, boolean updateDatabase) {
    if (updateDatabase) central.getCacheManager().getCachePlayers().setVanished(player.getUniqueId(), true);
    vanishedPlayers.add(player.getUniqueId());
    hideFromAllPlayers(player);
  }

  public void removeVanished(Player player) {
    central.getCacheManager().getCachePlayers().setVanished(player.getUniqueId(), false);
    vanishedPlayers.remove(player.getUniqueId());
    showToAllPlayers(player);
  }

  public void removeVanishData(UUID uuid) {
    vanishedPlayers.remove(uuid);
  }

  public boolean isVanished(Player player) {
    return central.getCacheManager().getCachePlayers().isVanished(player.getUniqueId());
  }

  private void hideFromAllPlayers(Player player) {
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (!onlinePlayer.hasPermission("central.command.vanish")) onlinePlayer.hidePlayer(central, player);
    }
  }

  private void showToAllPlayers(Player player) {
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.showPlayer(central, player);
  }

  public void hideVanishedPlayersFromPlayer(Player player) {
    if (player.hasPermission("central.command.vanish")) return;
    for (UUID vanishedID : vanishedPlayers) {
      final Player vanished = PlayerDataAPI.getOnlinePlayer(vanishedID);
      if (vanished != null) player.hidePlayer(central, vanished);
    }
  }
}
