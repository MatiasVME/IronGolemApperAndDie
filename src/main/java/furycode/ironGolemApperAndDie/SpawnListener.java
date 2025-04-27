package furycode.ironGolemApperAndDie;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnListener implements Listener {
    private final JavaPlugin plugin;

    public SpawnListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        // Filtramos únicamente golems de hierro y nieve
        if (entity.getType() == EntityType.IRON_GOLEM) {
            // Tarea repetitiva para las partículas cada 0.5s
            new BukkitRunnable() {
                int ticksPassed = 0;
                @Override
                public void run() {
                    if (ticksPassed >= 60) { // 60 ticks = 3 segundos
                        entity.remove();
                        cancel();
                        return;
                    }
                    // Partículas alrededor del golem
                    entity.getWorld().spawnParticle(
                            Particle.CLOUD,
                            entity.getLocation().add(0, 1, 0),
                            10,  // cantidad
                            0.5, 0.5, 0.5, // offset XYZ
                            0.01 // velocidad
                    );
                    ticksPassed += 10;
                }
            }.runTaskTimer(plugin, 0L, 10L); // se ejecuta cada 10 ticks (0.5s)
        }
    }
}