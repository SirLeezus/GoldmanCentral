package lee.code.central.utils;

import lee.code.central.Central;
import lee.code.central.lang.Lang;
import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

public class ScheduleUtil {
  private final Central central;

  public ScheduleUtil(Central central) {
    this.central = central;
    startTabListUpdater();
  }

  public void startTabListUpdater() {
    Bukkit.getAsyncScheduler().runAtFixedRate(central, (scheduledTask) -> Bukkit.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(null), Lang.TABLIST_FOOTER.getComponent(new String[]{String.valueOf(CoreUtil.getOnlinePlayers().size())})),
      0,
      5,
      TimeUnit.SECONDS
    );
  }
}
