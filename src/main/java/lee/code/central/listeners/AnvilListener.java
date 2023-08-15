package lee.code.central.listeners;

import lee.code.central.utils.CoreUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvilRename(PrepareAnvilEvent e) {
        final ItemStack[] contents = e.getInventory().getContents();
        final ItemStack firstSlot = contents[0];
        final ItemStack secondSlot = contents[1];
        final ItemStack resultStack = e.getResult();
        if (firstSlot == null || secondSlot != null || resultStack == null) return;
        final ItemStack dupe = firstSlot.clone();
        final ItemMeta dupeMeta = dupe.getItemMeta();
        final String name = CoreUtil.convertComponentToString(resultStack.displayName());
        if (!name.contains("&")) return;
        dupeMeta.displayName(CoreUtil.parseColorComponent(name));
        dupe.setItemMeta(dupeMeta);
        e.setResult(dupe);
    }

    @EventHandler
    public void onDenyAnvilUse(InventoryClickEvent e) {
        if (e.getInventory().getType() != InventoryType.ANVIL) return;
        if (e.getSlotType() != InventoryType.SlotType.RESULT) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        final ItemStack clicked = e.getInventory().getItem(0);
        if (clicked == null) return;
        if (clicked.getType().equals(Material.SPAWNER)) e.setCancelled(true);
        if (clicked.getType().equals(Material.PLAYER_HEAD)) e.setCancelled(true);
    }
}
