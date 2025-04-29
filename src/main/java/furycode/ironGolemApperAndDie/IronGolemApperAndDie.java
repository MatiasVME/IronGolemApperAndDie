package furycode.ironGolemApperAndDie;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class IronGolemApperAndDie extends JavaPlugin {
    private List<String> ignoredWorlds;

    @Override
    public void onEnable() {
        // Guarda el config por defecto si no existe
        saveDefaultConfig();
        FileConfiguration cfg = getConfig();
        // Leemos la lista de mundos a ignorar
        ignoredWorlds = cfg.getStringList("ignored-worlds");

        // Registramos listener
        getServer().getPluginManager().registerEvents(new SpawnListener(this, ignoredWorlds), this);
        getLogger().info("GolemLifePlugin habilitado. Ignorando mundos: " + ignoredWorlds);
    }

    @Override
    public void onDisable() {
        getLogger().info("GolemLifePlugin deshabilitado.");
    }
}
