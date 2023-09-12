package lee.code.central.utils;

import lee.code.central.enums.EntityHead;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;

public class HeadUtil {
  private final static Random random = new Random();

  public static ItemStack getEntityHead(Entity entity, int rng) {
    if (entity instanceof Player || entity.getType().equals(EntityType.ENDER_DRAGON) || entity.getType().equals(EntityType.WARDEN)) {
      return getEntityHead(entity);
    } else if (rng >= 980) {
      return getEntityHead(entity);
    }
    return null;
  }

  private static ItemStack getEntityHead(Entity entity) {
    if (entity instanceof Player player) {
      return getPlayerHead(player);
    } else if (entity instanceof EnderDragon) {
      return new ItemStack(Material.DRAGON_HEAD);
    } else if (entity instanceof Creeper creeper) {
      return getCreeperHead(creeper);
    } else if (entity instanceof WitherSkeleton) {
      return new ItemStack(Material.WITHER_SKELETON_SKULL);
    } else if (entity instanceof Skeleton) {
      return new ItemStack(Material.SKELETON_SKULL);
    } else if (entity.getType() == EntityType.ZOMBIE) {
      return new ItemStack(Material.ZOMBIE_HEAD);
    } else if (entity instanceof Warden) {
      return EntityHead.valueOf(entity.getType().name()).getHead();
    } else if (entity instanceof Piglin) {
      return new ItemStack(Material.PIGLIN_HEAD);
    } else {
      return getCustomEntityHead(entity);
    }
  }

  private static ItemStack getPlayerHead(Player player) {
    final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
    final SkullMeta headMeta = (SkullMeta) head.getItemMeta();
    headMeta.setOwningPlayer(player);
    head.setItemMeta(headMeta);
    return head;
  }

  private static ItemStack getCreeperHead(Creeper creeper) {
    if (creeper.isPowered()) {
      return EntityHead.valueOf("CHARGED_" + creeper.getType().name()).getHead();
    } else {
      return new ItemStack(Material.CREEPER_HEAD);
    }
  }

  private static ItemStack getCustomEntityHead(Entity entity) {
    final EntityType entityType = entity.getType();
    String type = entityType.name();

    if (entity instanceof Sheep sheep) {
      if (sheep.name().equals(Component.text("jeb_"))) {
        type = "RAINBOW_" + type;
      } else {
        final DyeColor color = sheep.getColor();
        if (color != null) {
          type = color.name() + "_" + type;
        } else {
          type = "WHITE_" + type;
        }
      }
    } else if (entity instanceof Axolotl axolotl) {
      final Axolotl.Variant variant = axolotl.getVariant();
      type = variant.name() + "_" + type;
    } else if (entity instanceof Parrot parrot) {
      final Parrot.Variant variant = parrot.getVariant();
      type = variant.name() + "_" + type;
    } else if (entity instanceof Llama llama) {
      if (llama instanceof TraderLlama traderLlama) {
        final Llama.Color color = traderLlama.getColor();
        type = color.name() + "_" + type;
      } else {
        final Llama.Color color = llama.getColor();
        type = color.name() + "_" + type;
      }
    } else if (entity instanceof Villager villager) {
      final Villager.Profession villagerProfession = villager.getProfession();
      final Villager.Type villagerType = villager.getVillagerType();
      if (villagerProfession != Villager.Profession.NONE) {
        type = villagerType.name() + "_" + villagerProfession.name() + "_" + type;
      } else {
        type = villagerType.name() + "_" + type;
      }
    } else if (entity instanceof MushroomCow mushroomCow) {
      final MushroomCow.Variant variant = mushroomCow.getVariant();
      type = variant.name() + "_" + type;
    } else if (entity instanceof Frog frog) {
      final Frog.Variant variant = frog.getVariant();
      type = variant.name() + "_" + type;
    } else if (entity instanceof Horse horse) {
      final Horse.Color color = horse.getColor();
      type = color.name() + "_" + type;
    } else if (entity instanceof Rabbit rabbit) {
      final Rabbit.Type variant = rabbit.getRabbitType();
      type = variant.name() + "_" + type;
    }
    return EntityHead.valueOf(type).getHead();
  }

  public static int headDropRNG() {
    return random.nextInt(1000);
  }
}
