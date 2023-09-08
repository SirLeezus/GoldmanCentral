package lee.code.central.listeners;

import lee.code.central.utils.CoreUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class BookListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBookSign(PlayerEditBookEvent e) {
    if (e.isCancelled()) return;
    final List<Component> lines = e.getNewBookMeta().pages();
    if (lines.isEmpty()) return;
    final BookMeta meta = e.getNewBookMeta();
    for (int i = 0; i <= lines.size() - 1; i++) {
      meta.page(i + 1, CoreUtil.parseColorComponent(PlainTextComponentSerializer.plainText().serialize(lines.get(i))));
    }
    if (meta.hasTitle()) meta.title(CoreUtil.parseColorComponent(PlainTextComponentSerializer.plainText().serialize(meta.title())));
    e.setNewBookMeta(meta);
  }
}
