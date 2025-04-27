package furycode.ironGolemApperAndDie;

import org.bukkit.plugin.java.JavaPlugin;

public final class IronGolemApperAndDie extends JavaPlugin {

    @Override
    public void onEnable() {
        // Registramos nuestro listener de spawn
        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
        getLogger().info("GolemLifePlugin habilitado.");
    }

    @Override
    public void onDisable() {
        getLogger().info("GolemLifePlugin deshabilitado.");
    }
}
