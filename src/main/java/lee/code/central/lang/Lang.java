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
    ERROR_ONE_COMMAND_AT_A_TIME("&cYou're currently processing another town command, please wait for it to finish."),
    ERROR_NOT_CONSOLE_COMMAND("&cThis command does not work in console."),

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
