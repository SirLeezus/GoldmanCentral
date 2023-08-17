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
    ADVANCEMENT_PREFIX("&a&lAdvancement &6➔ &r"),
    ON("&2&lON"),
    OFF("&c&lOFF"),
    PLAYER_JOIN("{display-name} &ahas joined the server!"),
    PLAYER_QUIT("{display-name} &7has left the server."),
    TABLIST_HEADER("&#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n&#4dc462&lJourney Towns\n&#228B22▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    TABLIST_FOOTER("\n&#228B22&lOnline&7: &#4dc462{0}"),
    COMMAND_GAMEMODE_SUCCESSFUL("&aYour gamemode was updated to &e{0}&a!"),
    COMMAND_FLY_TOGGLE_SUCCESSFUL("&aYour fly was successfully toggled {0}&a!"),
    COMMAND_SMITE_TARGET_SUCCESSFUL("&aYou successfully summoned lightning on &6{0}&a!"),
    COMMAND_SMITE_BLOCK_SUCCESSFUL("&aYou successfully summoned lightning!"),
    COMMAND_ENCHANT_SUCCESSFUL("&aYou successfully enchanted your hand item with enchantment &3{0} &alevel &3{1}&a."),
    COMMAND_GIVE_TARGET_SUCCESSFUL("&aYou have received &3x{0} {1}&a!"),
    COMMAND_GIVE_SUCCESSFUL("&aThe player &6{0} &ahas been given &3x{1} {2}&a!"),
    COMMAND_HEAL_SUCCESSFUL("&aYou are now fully healed!"),
    COMMAND_HEAL_TARGET_SUCCESSFUL("&aYou have been healed fully by &6{0}&a!"),
    COMMAND_HEAL_PLAYER_TARGET_SUCCESSFUL("&aYou healed the player &6{0} &afully!"),
    COMMAND_GLOW_SUCCESSFUL("&aYour glow was successfully toggled {0}&a."),
    COMMAND_WEATHER_CLEAR("&aThe weather has been &ecleared&a!"),
    COMMAND_WEATHER_RAIN("&aThe weather has been changed to &erain&a."),
    COMMAND_WEATHER_THUNDER("&aThe weather has been changed to &ethunderstorm&a."),
    ERROR_ONE_COMMAND_AT_A_TIME("&cYou're currently processing another town command, please wait for it to finish."),
    ERROR_ENCHANT_NO_HAND_ITEM("&cYou're currently not holding a item to enchant."),
    ERROR_NOT_ENCHANT("&cThe input &3{0} &cis not a enchantment."),
    ERROR_NOT_MATERIAL("&cThe input &3{0} &cis not a material."),
    ERROR_NOT_CONSOLE_COMMAND("&cThis command does not work in console."),
    ERROR_SMITE_NO_BLOCK("&cCould not find a location to summon lightning."),
    ERROR_HEAL_HEALTH_INVALID("&cHealth attribute could not be found."),
    ERROR_PLAYER_NOT_FOUND("&cThe player &6{0} &ccould not be found."),
    ERROR_PLAYER_NOT_ONLINE("&cThe player &6{0} &cis not online."),
    ERROR_VALUE_INVALID("&cThe input &3{0} &cis not an acceptable value."),

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
