package lee.code.central.listeners;

import lee.code.central.utils.CoreUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class BookListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onBookSign(PlayerEditBookEvent e) {
        if (e.isCancelled()) return;
        final List<String> lines = new ArrayList<>(e.getNewBookMeta().getPages());
        if (lines.isEmpty()) return;
        final BookMeta meta = e.getNewBookMeta();
        for (int i = 0; i <= lines.size() - 1; i++) {
            meta.page(i + 1, CoreUtil.parseColorComponent(lines.get(i)));
        }
        meta.title(CoreUtil.parseColorComponent(meta.getTitle()));
        e.setNewBookMeta(meta);
    }
}
