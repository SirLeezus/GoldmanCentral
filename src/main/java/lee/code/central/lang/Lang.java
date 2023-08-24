package lee.code.central.lang;

import lee.code.central.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
    PREFIX("&2&lJourney &6➔ "),
    USAGE("&6&lUsage: &e{0}"),
    DEATH_PREFIX("&c&lDeath &6➔ &r"),
    SPAWNER_NAME("&e{0} Spawner"),
    DEATH_HOVER("&2&lPlayer&7: {display-name}\n&2&lTotal Deaths&7: &c{1}"),
    ADVANCEMENT_PREFIX("&a&lAdvancement &6➔ &r"),
    VALUE_FORMAT("&6${0}"),
    ON("&2&lON"),
    OFF("&c&lOFF"),
    ACCEPT("&2[&a&lAccept&2]"),
    CONFIRM("&2[&a&lConfirm&2]"),
    DENY("&4[&c&lDeny&4]"),
    CLICK("&6[&6&l!&6] &ePlease confirm by clicking&7: "),
    PLAYER_JOIN("{display-name} &ahas joined the server!"),
    PLAYER_QUIT("{display-name} &7has left the server."),
    NEXT_PAGE_TEXT("&2&lNext &a&l>>--------"),
    PREVIOUS_PAGE_TEXT("&a&l--------<< &2&lPrev"),
    PAGE_SPACER_TEXT(" &e| "),
    NEXT_PAGE_HOVER("&6&lNext Page"),
    PREVIOUS_PAGE_HOVER("&6&lPrevious Page"),
    TABLIST_HEADER("&#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n&#4dc462&lJourney Towns\n&#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    TABLIST_FOOTER("\n&#228B22&lOnline&7: &#4dc462{0}"),
    COMMAND_TELEPORT_ASK_SUCCESS("&aYou successfully sent &6{0} &aa teleport request."),
    COMMAND_TELEPORT_ASK_TARGET_SUCCESS("&aCan &6{0} &ateleport to you?"),
    COMMAND_TELEPORT_ASK_ACCEPT_HOVER("&aClick to accept &6{0}'s &ateleport request!"),
    COMMAND_TELEPORT_ASK_DENY_HOVER("&cClick to deny &6{0}'s &cteleport request!"),
    COMMAND_MESSAGE_SENT_SUCCESSFUL("&9[&eYou &9-> {0}&9] &#00DCFF{1}"),
    COMMAND_MESSAGE_RECEIVED_SUCCESSFUL("&9[{0} &9-> &eYou&9] &#00DCFF{1}"),
    COMMAND_BALANCE_TOP_LINE("&3{0}&7. {1} &7| {2}"),
    COMMAND_STAT_TOP_LINE("&3{0}&7. {1} &7| &2{2}"),
    COMMAND_BALANCE_TOP_TITLE("&a----- &e[ &2&lBalance Leaderboard &e] &a-----"),
    COMMAND_STAT_TOP_TITLE("&a------ &e[ &2&lStats Leaderboard &e] &a------"),
    COMMAND_STAT_TOP_STAT("&e&lStat&7: &6{0}"),
    COMMAND_BALANCE_SUCCESSFUL("&aYour balance&7: {0}"),
    COMMAND_STAT_SUCCESSFUL("&aChecking stat &3&l{0}&7: &2{1}"),
    COMMAND_STAT_TARGET_SUCCESSFUL("&aChecking stat &3&l{0} {1}&7: &2{2}"),
    COMMAND_BALANCE_TARGET_SUCCESSFUL("&6{0}'s &abalance&7: {1}"),
    COMMAND_FLYSPEED_SUCCESSFUL("&aYour fly speed is now set to &3{0}&a!"),
    COMMAND_GAMEMODE_SUCCESSFUL("&aYour gamemode was updated to &e{0}&a!"),
    COMMAND_FLY_TOGGLE_SUCCESSFUL("&aYour fly was successfully toggled {0}&a!"),
    COMMAND_SMITE_TARGET_SUCCESSFUL("&aYou successfully summoned lightning on &6{0}&a!"),
    COMMAND_SMITE_BLOCK_SUCCESSFUL("&aYou successfully summoned lightning!"),
    COMMAND_ENCHANT_SUCCESSFUL("&aYou successfully enchanted your hand item with enchantment &3{0} &alevel &3{1}&a."),
    COMMAND_GIVE_TARGET_SUCCESSFUL("&aYou have received &3x{0} {1}&a!"),
    COMMAND_GIVE_SUCCESSFUL("&aThe player &6{0} &ahas been given &3x{1} {2}&a!"),
    COMMAND_HEAL_SUCCESSFUL("&aYou are now fully healed!"),
    COMMAND_HEAL_TARGET_SUCCESSFUL("&aYou have been healed fully by &6{0}&a!"),
    COMMAND_FEED_SUCCESSFUL("&aYou are no longer hungry."),
    COMMAND_FEED_TARGET_SUCCESSFUL("&aYou are no longer hungry thanks to player &6{0}&a!"),
    COMMAND_FEED_PLAYER_TARGET_SUCCESSFUL("&aYou fully fed the player &6{0}&a!"),
    COMMAND_SPAWN_SUCCESSFUL("&aYou successfully teleported to the server spawn."),
    COMMAND_SPAWN_FAILED("&cFailed to teleport to server spawn."),
    COMMAND_TELEPORT_ASK_ACCEPT_FAILED("&cFailed to teleport to player &6{0}&c."),
    COMMAND_TELEPORT_ASK_ACCEPT_SUCCESSFUL("&aYou successfully teleported to &6{0}&a!"),
    COMMAND_TELEPORT_ASK_ACCEPT_PLAYER_SUCCESSFUL("&aYou successfully accepted &6{0}'s &ateleport request!"),
    COMMAND_TELEPORT_ASK_DENY_TARGET_SUCCESSFUL("&7The player &6{0} &7has denied your teleport request."),
    COMMAND_TELEPORT_ASK_DENY_PLAYER_SUCCESSFUL("&aYou successfully denied &6{0}'s &ateleport request!"),
    COMMAND_WORLD_SUCCESSFUL("&aYou successfully teleported to world &3{0}&a!"),
    COMMAND_WORLD_FAILED("&cFailed to teleport to world."),
    COMMAND_SET_SPAWN_SUCCESSFUL("&aYou successfully set the server spawn to your location."),
    COMMAND_COLOR_SUCCESSFUL("&aYou successfully set your color to {0}&a!"),
    COMMAND_CLEAR_SUCCESSFUL("&aYou successfully cleared your inventory."),
    COMMAND_TIME_SUCCESSFUL("&aYou successfully set the time to &3{0}&a!"),
    COMMAND_HEAL_PLAYER_TARGET_SUCCESSFUL("&aYou healed the player &6{0} &afully!"),
    COMMAND_GLOW_SUCCESSFUL("&aYour glow was successfully toggled {0}&a."),
    COMMAND_PAY_SUCCESSFUL("&aYou successfully sent {0} &ato the player &6{1}&a!"),
    COMMAND_PAY_TARGET_SUCCESSFUL("&aYou have been sent {0} &afrom the player &6{1}&a!"),
    COMMAND_WEATHER_CLEAR("&aThe weather has been &3cleared&a!"),
    COMMAND_WEATHER_RAIN("&aThe weather has been changed to &3rain&a."),
    COMMAND_WEATHER_THUNDER("&aThe weather has been changed to &3thunderstorm&a."),
    COMMAND_SEEN_SUCCESSFUL("&aThe last time the player &6{0} &ajoined was &3{1}&a."),
    COMMAND_MONEY_SET_SUCCESSFUL("&aYou successfully set &6{0}'s &abalance to {1}&a!"),
    COMMAND_MONEY_ADDED_SUCCESSFUL("&aYou successfully added {0} &ato &6{1}'s &abalance!"),
    COMMAND_MONEY_REMOVED_SUCCESSFUL("&aYou successfully removed {0} &afrom &6{1}'s &abalance!"),
    COMMAND_WORTH_HEADER("&a----- &e[ &2&lWorth &e] &a-----"),
    COMMAND_WORTH_ITEM("&3&lItem&7: &b{0}"),
    COMMAND_WORTH_WORTH("&3&lWorth&7: {0}"),
    COMMAND_WORTH_INVENTORY("&3&lInventory&7: {0}"),
    COMMAND_WORTH_HAND("&3&lHand&7: {0}"),
    COMMAND_WORTH_SPLITTER("&a--------------------"),
    COMMAND_SELL_SUCCESSFUL("&aYou successfully sold &3x{0} {1} &afor {2}&a!"),
    COMMAND_COLORS_TITLE("&a---- &e[ &2&lColors &e] &a----"),
    COMMAND_COLORS_SPLITTER("&a--------------------"),
    ERROR_WORTH_NO_ITEM("&cYou need to be holding the item you want to check the value of."),
    ERROR_SELL_NO_ITEM("&cYou need to be holding the item you want to sell."),
    ERROR_NO_VALUE("&cThe item &3{0} &ccannot be sold to the server."),
    ERROR_SELL_NOT_ENOUGH("&cYou need at least &3x{0} {1} to sell this item."),
    ERROR_ONE_COMMAND_AT_A_TIME("&cYou're currently processing another town command, please wait for it to finish."),
    ERROR_ENCHANT_NO_HAND_ITEM("&cYou're currently not holding a item to enchant."),
    ERROR_NOT_ENCHANT("&cThe input &3{0} &cis not a enchantment."),
    ERROR_PAY_INSUFFICIENT_FUNDS("&cYou only have {0} &cso you can't pay someone {1}&c."),
    ERROR_PAY_BELOW_ZERO("&cYou can't send a player money below the amount of {0}&c."),
    ERROR_PAY_SELF("&cYou can't pay yourself."),
    ERROR_FLYSPEED_LIMIT("&cYou can only adjust your fly speed between 1 and 10."),
    ERROR_NOT_MATERIAL("&cThe input &3{0} &cis not a material."),
    ERROR_NOT_ENTITY_TYPE("&cThe input &3{0} &cis not a entity type."),
    ERROR_NOT_CONSOLE_COMMAND("&cThis command does not work in console."),
    ERROR_SMITE_NO_BLOCK("&cCould not find a location to summon lightning."),
    ERROR_HEAL_HEALTH_INVALID("&cHealth attribute could not be found."),
    ERROR_MESSAGE_SELF("&cYou can't message yourself."),
    ERROR_REPLY_NONE("&cYou don't have any recent private messages."),
    ERROR_PLAYER_NOT_FOUND("&cThe player &6{0} &ccould not be found."),
    ERROR_NO_PLAYER_DATA("&cCould not find any player data for &6{0}&c."),
    ERROR_WORLD_DOES_NOT_EXIST("&cThe world &3{0} &ccould not be found."),
    ERROR_PLAYER_NOT_ONLINE("&cThe player &6{0} &cis not online."),
    ERROR_TELEPORT_ASK_ALREADY_REQUESTED("&cYou already have a teleport request active for the player &6{0}&c."),
    ERROR_TELEPORT_ASK_REQUEST_TIMEOUT_PLAYER("&7Your teleport request to player &6{0} &7has expired."),
    ERROR_TELEPORT_ASK_REQUEST_TIMEOUT_TARGET("&7The teleport request from the player &6{0} &7has expired."),
    ERROR_TELEPORT_ASK_NO_REQUEST("&cYou don't currently have a teleport request from the player &6{0}&c."),
    ERROR_TELEPORT_ASK_SELF("&cYou can't ask to teleport to yourself."),
    ERROR_STAT_NO_OPTION_SELECTED("&cYou need to select a target option with the stat &3{0}&c."),
    ERROR_STAT_DOES_NOT_EXIST("&cThe stat &3{0} &ccould not be found."),
    ERROR_SPAWN_NOT_SET("&cCurrently there is no server spawn set."),
    ERROR_VALUE_INVALID("&cThe input &3{0} &cis not an acceptable value."),
    ERROR_COLOR_INVALID("&cThe input &3{0} &cis not an acceptable color."),
    ERROR_NO_INVENTORY_SPACE("&cThe player &6{0} &cdoes not have enough inventory space."),

    ;
    @Getter
    private final String string;

    public String getString(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return value;
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return value;
    }

    public Component getComponent(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return CoreUtil.parseColorComponent(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return CoreUtil.parseColorComponent(value);
    }
}
