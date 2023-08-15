package lee.code.central;

import lee.code.central.commands.CustomCommand;
import lee.code.central.commands.CommandManager;
import lee.code.central.commands.TabCompletion;
import lee.code.central.listeners.*;
import lee.code.central.scoreboard.ScoreboardManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Central extends JavaPlugin {

    @Getter private ScoreboardManager scoreboardManager;
    @Getter private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.scoreboardManager = new ScoreboardManager();
        this.commandManager = new CommandManager(this);

        registerCommands();
        registerListeners();
        startSchedules();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new HealthListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new AdvancementListener(), this);
        getServer().getPluginManager().registerEvents(new HeadDropListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);
        getServer().getPluginManager().registerEvents(new BookListener(), this);
    }

    private void startSchedules() {

    }

    private void registerCommands() {
        for (CustomCommand command : commandManager.getCommands()) {
            getCommand(command.getName()).setExecutor(command);
            getCommand(command.getName()).setTabCompleter(new TabCompletion(command));
        }
    }
}
