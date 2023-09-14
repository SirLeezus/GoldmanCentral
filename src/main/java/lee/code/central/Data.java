package lee.code.central;

import lee.code.central.enums.PlaceLimitMaterial;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
  @Getter private final Set<String> enchantments = ConcurrentHashMap.newKeySet();
  @Getter private final Set<String> materials = ConcurrentHashMap.newKeySet();
  @Getter private final Set<String> entityTypes = ConcurrentHashMap.newKeySet();
  @Getter private final Set<String> worlds = ConcurrentHashMap.newKeySet();
  @Getter private final Set<String> statistics = ConcurrentHashMap.newKeySet();
  @Getter private final Set<String> playerColors = ConcurrentHashMap.newKeySet();
  @Getter private final Set<String> entityPlaceLimitMaterials = ConcurrentHashMap.newKeySet();

  public Data() {
    loadData();
  }

  public void loadData() {
    //enchantments
    for (Enchantment enchantment : Enchantment.values()) enchantments.add(enchantment.getKey().value());
    //materials
    for (Material material : Material.values()) materials.add(material.name());
    //entity types
    for (EntityType entityType : EntityType.values()) entityTypes.add(entityType.name());
    //worlds
    for (World world : Bukkit.getWorlds()) worlds.add(world.getName());
    //stats
    for (Statistic statistic : Statistic.values()) statistics.add(statistic.name());
    //entity place limit materials
    for (PlaceLimitMaterial placeLimitMaterial : PlaceLimitMaterial.values()) entityPlaceLimitMaterials.add(placeLimitMaterial.name());
    //colors
    playerColors.addAll(Arrays.asList("AQUA", "BLUE", "DARK_AQUA", "DARK_BLUE", "DARK_GRAY", "DARK_GREEN", "DARK_PURPLE", "DARK_RED", "GOLD", "GRAY", "GREEN", "YELLOW", "RED", "LIGHT_PURPLE"));
  }
}
