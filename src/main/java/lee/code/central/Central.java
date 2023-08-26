package lee.code.central;

import com.mojang.brigadier.tree.LiteralCommandNode;
import lee.code.central.commands.CustomCommand;
import lee.code.central.commands.CommandManager;
import lee.code.central.commands.TabCompletion;
import lee.code.central.database.CacheManager;
import lee.code.central.database.DatabaseManager;
import lee.code.central.listeners.*;
import lee.code.central.managers.*;
import lee.code.central.menus.system.MenuListener;
import lee.code.central.menus.system.MenuManager;
import lombok.Getter;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Central extends JavaPlugin {

    @Getter private MOTDManager motdManager;
    @Getter private DelayManager delayManager;
    @Getter private ArmorStandManager armorStandManager;
    @Getter private MenuManager menuManager;
    @Getter private TeleportRequestManager teleportRequestManager;
    @Getter private ReplyManager replyManager;
    @Getter private CacheManager cacheManager;
    @Getter private CommandManager commandManager;
    @Getter private Data data;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        this.databaseManager = new DatabaseManager(this);
        this.cacheManager = new CacheManager(this, databaseManager);
        this.commandManager = new CommandManager(this);
        this.replyManager = new ReplyManager();
        this.teleportRequestManager = new TeleportRequestManager(this);
        this.menuManager = new MenuManager();
        this.armorStandManager = new ArmorStandManager();
        this.delayManager = new DelayManager(this);
        this.motdManager = new MOTDManager(this);
        this.data = new Data();

        databaseManager.initialize(false);
        registerCommands();
        registerListeners();
        startSchedules();
    }

    @Override
    public void onDisable() {
        databaseManager.closeConnection();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new AdvancementListener(), this);
        getServer().getPluginManager().registerEvents(new HeadDropListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);
        getServer().getPluginManager().registerEvents(new BookListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new HopperFilterListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(menuManager), this);
        getServer().getPluginManager().registerEvents(new ArmorStandEditorListener(this), this);
        getServer().getPluginManager().registerEvents(new GodListener(this), this);
    }

    private void startSchedules() {

    }

    private void registerCommands() {
        for (CustomCommand command : commandManager.getCommands()) {
            getCommand(command.getName()).setExecutor(command);
            getCommand(command.getName()).setTabCompleter(new TabCompletion(command));
            loadCommodoreData(getCommand(command.getName()));
        }
    }

    private void loadCommodoreData(Command command) {
        try {
            final LiteralCommandNode<?> targetCommand = CommodoreFileReader.INSTANCE.parse(getResource("commodore/" + command.getName() + ".commodore"));
            CommodoreProvider.getCommodore(this).register(command, targetCommand);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
