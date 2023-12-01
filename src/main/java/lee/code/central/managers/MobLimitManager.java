package lee.code.central.managers;

import lee.code.central.Central;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MobLimitManager {
  private final Central central;
  private final int maxMobPerChunk = 25;

  public MobLimitManager(Central central) {
    this.central = central;
    startMobLimiter();
  }

  private int countMobsInChunk(Chunk chunk, EntityType type) {
    int count = 0;
    for (Entity target : chunk.getEntities()) if (type.equals(target.getType())) count++;
    return count;
  }

  private int getTotalMobs(Chunk chunk) {
    int amount = 0;
    for (Entity target : chunk.getEntities()) {
      if (target.getType().equals(EntityType.DROPPED_ITEM)) continue;
      if (target.getType().equals(EntityType.ARMOR_STAND)) continue;
      if (target.getType().equals(EntityType.ITEM_FRAME)) continue;
      if (target.getType().equals(EntityType.GLOW_ITEM_FRAME)) continue;
      if (target.getType().equals(EntityType.BOAT)) continue;
      if (target.getType().equals(EntityType.CHEST_BOAT)) continue;
      if (target.getType().equals(EntityType.PLAYER)) continue;
      if (target.customName() != null) continue;
      amount++;
    }
    return amount;
  }

  public boolean hasReachedTotalMobLimit(Chunk chunk, EntityType type) {
    return countMobsInChunk(chunk, type) >= maxMobPerChunk || getTotalMobs(chunk) >= 100;
  }

  public boolean hasReachedMobLimit(Chunk chunk, EntityType type) {
    return countMobsInChunk(chunk, type) >= maxMobPerChunk;
  }

  private void startMobLimiter() {
    Bukkit.getAsyncScheduler().runAtFixedRate(central, (scheduledTask) -> {
        for (World world : Bukkit.getWorlds()) {
          for (Chunk chunk : world.getLoadedChunks()) {
            final HashMap<EntityType, Integer> count = new HashMap<>();
            for (Entity entity : chunk.getEntities()) {
              if (entity.getType().equals(EntityType.DROPPED_ITEM) || entity.customName() != null) continue;
              if (count.getOrDefault(entity.getType(), 0) > maxMobPerChunk) entity.getScheduler().run(central, task -> entity.remove(), null);
              else count.put(entity.getType(), count.getOrDefault(entity.getType(), 0) + 1);
            }
          }
        }
      },
      0,
      60,
      TimeUnit.SECONDS
    );
  }

  public void sendPlayerLimitMessage(Player player, EntityType entityType) {
    player.sendActionBar(Lang.ERROR_MOB_LIMIT_REACHED.getComponent(new String[]{String.valueOf(maxMobPerChunk), CoreUtil.capitalize(entityType.name())}));
  }
}
