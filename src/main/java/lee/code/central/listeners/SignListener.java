package lee.code.central.listeners;

import lee.code.central.utils.CoreUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.List;

public class SignListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST)
  public void onSignChange(SignChangeEvent e) {
    final List<Component> lines = e.lines();
    if (lines.isEmpty()) return;
    final String firstLine = PlainTextComponentSerializer.plainText().serialize(lines.get(0));
    if (firstLine.equalsIgnoreCase("[shop]") || firstLine.equalsIgnoreCase("[lock]")) return;
    for (int i = 0; i <= lines.size() - 1; i++) {
      e.line(i, CoreUtil.parseColorComponent(PlainTextComponentSerializer.plainText().serialize(lines.get(i))));
    }
  }
}
