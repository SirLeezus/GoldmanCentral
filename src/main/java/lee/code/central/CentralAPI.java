package lee.code.central;

import java.util.UUID;

public class CentralAPI {

    public static double getBalance(UUID uuid) {
        if (!Central.getInstance().getCacheManager().getCachePlayers().hasPlayerData(uuid)) return 0;
        else return Central.getInstance().getCacheManager().getCachePlayers().getBalance(uuid);
    }

    public static void addBalance(UUID uuid, double amount) {
        if (!Central.getInstance().getCacheManager().getCachePlayers().hasPlayerData(uuid)) return;
        Central.getInstance().getCacheManager().getCachePlayers().addBalance(uuid, amount);
    }

    public static void setBalance(UUID uuid, double amount) {
        if (!Central.getInstance().getCacheManager().getCachePlayers().hasPlayerData(uuid)) return;
        Central.getInstance().getCacheManager().getCachePlayers().setBalance(uuid, amount);
    }

    public static void removeBalance(UUID uuid, double amount) {
        if (!Central.getInstance().getCacheManager().getCachePlayers().hasPlayerData(uuid)) return;
        Central.getInstance().getCacheManager().getCachePlayers().removeBalance(uuid, amount);
    }

}
