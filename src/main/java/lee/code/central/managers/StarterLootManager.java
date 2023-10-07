package lee.code.central.managers;

import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

public class StarterLootManager {

  public void giveItems(Player player) {
    final PlayerInventory inventory = player.getInventory();
    inventory.setHelmet(ItemUtil.createArmorWithTrim(new ItemStack(Material.LEATHER_HELMET), TrimPattern.SHAPER, TrimMaterial.EMERALD));
    inventory.setChestplate(ItemUtil.createArmorWithTrim(new ItemStack(Material.LEATHER_CHESTPLATE), TrimPattern.SHAPER, TrimMaterial.EMERALD));
    inventory.setLeggings(ItemUtil.createArmorWithTrim(new ItemStack(Material.LEATHER_LEGGINGS), TrimPattern.SHAPER, TrimMaterial.EMERALD));
    inventory.setBoots(ItemUtil.createArmorWithTrim(new ItemStack(Material.LEATHER_BOOTS), TrimPattern.SHAPER, TrimMaterial.EMERALD));
    inventory.addItem(new ItemStack(Material.STONE_SWORD));
    inventory.addItem(new ItemStack(Material.STONE_PICKAXE));
    inventory.addItem(new ItemStack(Material.COOKED_PORKCHOP, 15));
    inventory.setItemInOffHand(createHelpBook());
  }

  private ItemStack createHelpBook() {
    final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    final BookMeta bookMeta = (BookMeta) book.getItemMeta();
    bookMeta.title(CoreUtil.parseColorComponent("&2&lJourney Towns"));
    bookMeta.author(CoreUtil.parseColorComponent("&6SirGoldman"));
    final List<Component> pages = new ArrayList<>();
    pages.add(CoreUtil.parseColorComponent(
      "&2&lWelcome!\n\n" +
        "&0This is a custom survival server that allows you to create a town which will protect your land. " +
        "To create your own town use the command &6/rtp &0to teleport to the wild and after run the command &6/t create <name>&0."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lRules\n\n" +
        "&6- &0No griefing players\n\n" +
        "&6- &0Be respectful to other players\n\n" +
        "&6- &0Do not create server lag\n\n" +
        "&6- &0No hacked clients\n\n" +
        "&cAny rule broken can result in a ban."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lVanilla Changes\n\n" +
        "&6- &0Weapon/Tool swing delay removed\n\n" +
        "&6- &0Armor stands can be modified by sneak-clicking them\n\n" +
        "&6- &0Item frames will act as filters on hoppers\n\n" +
        "&6- &0Max unique entity is 25 per chunk"
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lServer Settings\n\n" +
        "&6- &0Keep Inventory&7: &a&lON\n\n" +
        "&6- &0Explosions&7: &a&lON\n\n" +
        "&6- &0Fire Spread&7: &a&lON\n\n" +
        "&6- &0PvP&7: &a&lON\n\n" +
        "&6- &0Difficulty&7: &c&lHARD"
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lTowns\n\n" +
        "&0To view a full list of town commands run the command &6/t help&0. " +
        "This plugin allows you to create a town in which will protect your land. " +
        "The more citizens and playtime you have the more chunks your town can claim."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lEconomy\n\n" +
        "&0This server has an item economy, almost all items in the server can be sold using &6/sell &0or &6/sellall&0. " +
        "You can use that money to purchase items from &6/shop &0or &6/shop playershops&0. " +
        "If you want to check a items worth hold it and run the command &6/worth&0."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lSpawners\n\n" +
        "&0You can pickup spawners with the enchantment silk touch! " +
        "The server also allows you to buy them through the command &6/shop&0."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lVault\n\n" +
        "&0If you run &6/vault &0you'll open your own personal vault. " +
        "Vaults allow you to store an unlimited amount of the same item. " +
        "The default rank has two vault pages."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lPets\n\n" +
        "&0Pets can be captured by killing a mob with a lead. " +
        "Your pet will follow you when called and is rideable. " +
        "To learn more run the command &6/pets&0."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lTrails\n\n" +
        "&0Trails are particles that follow you around. " +
        "Every hour you play you earn one trail spin. " +
        "When you use a trail spin you can earn a new trail or trail style. " +
        "Use the command &6/trails &0to learn more."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lColors\n\n" +
        "&0Color codes work in signs, anvils and books for all players! " +
        "If you want to see all color codes run the command &6/colors&0."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lHomes\n\n" +
        "&0All players have the ability to set homes. " +
        "To create a new home run &6/home add <name>&0. " +
        "You'll be able to set a max of 5 homes."
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lLeaderboards\n\n" +
        "&0List of leaderboard commands:\n\n" +
        "&0- &6/baltop\n\n" +
        "&0- &6/stattop\n\n" +
        "&0- &6/t top\n\n"
    ));
    pages.add(CoreUtil.parseColorComponent(
      "&2&lPremium Ranks\n\n" +
        "&0You can subscribe to a rank with the command &6/buy&0. " +
        "All subs help keep the server running! <3\n\n" +
        "&6&lLegend\n" +
        "&2$4.99USD/Month\n\n" +
        "&5&lElite\n" +
        "&2$9.99USD/Month"
    ));
    bookMeta.pages(pages);
    book.setItemMeta(bookMeta);
    return book;
  }
}
