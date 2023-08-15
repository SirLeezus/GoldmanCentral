package lee.code.central;

import lee.code.central.listeners.*;
import lee.code.central.scoreboard.ScoreboardManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Central extends JavaPlugin {

    @Getter private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        this.scoreboardManager = new ScoreboardManager();

        registerCommands();
        registerListeners();
        startSchedules();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {

    }

    private void startSchedules() {

    }

    private void registerCommands() {
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new HealthListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new AdvancementListener(), this);
        getServer().getPluginManager().registerEvents(new HeadDropListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
    }
}
