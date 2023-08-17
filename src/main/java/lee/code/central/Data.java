package lee.code.central;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
    @Getter private final Set<String> enchantments = ConcurrentHashMap.newKeySet();
    @Getter private final Set<String> materials = ConcurrentHashMap.newKeySet();

    public Data() {
        loadData();
    }

    public void loadData() {
        //enchantments
        for (Enchantment enchantment : Enchantment.values()) enchantments.add(enchantment.getKey().value().toUpperCase());
        //materials
        for (Material material : Material.values()) materials.add(material.name());
    }
}