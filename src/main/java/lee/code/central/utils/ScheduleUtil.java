package lee.code.central.utils;

import lee.code.central.Central;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.concurrent.TimeUnit;

public class ScheduleUtil {
  public static void startTabListUpdater(Central central) {
    Bukkit.getAsyncScheduler().runAtFixedRate(central, (scheduledTask) -> {

      },
      0,
      10,
      TimeUnit.SECONDS
    );
  }
}
