package lee.code.central.listeners;

import lee.code.central.utils.CoreUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SignListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void onSignChange(SignChangeEvent e) {
    if (e.isCancelled()) return;
    final List<String> lines = new ArrayList<>(List.of(e.getLines()));
    if (lines.isEmpty()) return;
    for (int i = 0; i <= lines.size() - 1; i++) {
      e.line(i, CoreUtil.parseColorComponent(lines.get(i)));
    }
  }
}
