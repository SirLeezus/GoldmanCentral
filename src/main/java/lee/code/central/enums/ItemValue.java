package lee.code.central.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
public enum ItemValue {

    GRASS_BLOCK(Material.GRASS_BLOCK, 1, 1),
    DIRT(Material.DIRT, 1, 1),
    SAND(Material.SAND, 1, 1),
    RED_SAND(Material.RED_SAND, 1, 1),
    COBBLESTONE(Material.COBBLESTONE, 1, 1),
    END_STONE(Material.END_STONE, 1, 1),
    GRAVEL(Material.GRAVEL, 1, 1),

    ;
    private final Material material;
    @Getter private final int amount;
    @Getter private final double value;
}
