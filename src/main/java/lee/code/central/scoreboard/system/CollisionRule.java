package lee.code.central.scoreboard.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CollisionRule {
    ALWAYS("always"),
    NEVER("never"),
    PUSH_OTHER_TEAMS("pushOtherTeams"),
    PUSH_OWN_TEAM("pushOwnTeam"),
    ;
    @Getter private final String settings;
}
