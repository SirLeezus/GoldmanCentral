package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.lang.Lang;
import lee.code.central.scoreboard.system.BoardBuilder;
import lee.code.central.scoreboard.system.CollisionRule;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.VariableUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final Central central;

    public JoinListener(Central central) {
        this.central = central;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        //Set Player Name Color
        new BoardBuilder(central, player).collisionRule(CollisionRule.NEVER).nameColor(ChatColor.YELLOW).priority("a").create();
        //Set Attack Speed
        final AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) attribute.setBaseValue(23.4);
        //Set Join Message
        e.joinMessage(VariableUtil.parseVariables(player, Lang.PLAYER_JOIN.getComponent(null)));
        //Send Tab List
        Bukkit.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(null), Lang.TABLIST_FOOTER.getComponent(new String[] { String.valueOf( CoreUtil.getOnlinePlayers().size()) }));
    }
}
