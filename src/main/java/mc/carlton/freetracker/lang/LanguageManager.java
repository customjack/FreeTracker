package mc.carlton.freetracker.lang;

import mc.carlton.freetracker.Freetracker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class LanguageManager {
    private final static String PREFIX = "enUS";

    private static FileConfiguration lang;
    private static boolean isLangFileLoaded;

    public LanguageManager() {
        if (!isLangFileLoaded) {
            initializeLang();
        }
    }

    private void initializeLang() {
        Plugin plugin = Freetracker.getPlugin(Freetracker.class);
        File f = new File(plugin.getDataFolder(),"lang.yml");
        f.setReadable(true,false);
        f.setWritable(true,false);
        this.lang = YamlConfiguration.loadConfiguration(f);
        this.isLangFileLoaded = true;
    }

    public String getString(String id) {
        return lang.getString(PREFIX + id);
    }
}
