package mc.carlton.freetracker;

import mc.carlton.freetracker.commands.FtCommands;
import mc.carlton.freetracker.lang.LanguageManager;
import mc.carlton.freetracker.listeners.TrackerListeners;
import mc.carlton.freetracker.tracking.Tracker;
import mc.carlton.freetracker.tracking.TrackerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Freetracker extends JavaPlugin {
    public static FileConfiguration lang;
    @Override
    public void onEnable() {
        // Plugin startup logic

        //Initiate Files
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        saveResource("lang.yml",false);
        new LanguageManager(); //Initializes lang.yml

        //Register events
        getServer().getPluginManager().registerEvents(new TrackerListeners(), this);
        getServer().getPluginManager().registerEvents(new TrackerManager(), this);

        //Registers commands
        getCommand("ft").setExecutor(new FtCommands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
