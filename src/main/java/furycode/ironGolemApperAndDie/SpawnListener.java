package furycode.ironGolemApperAndDie;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SpawnListener implements Listener {
    private final JavaPlugin plugin;
    private final List<String> ignoredWorlds;

    public SpawnListener(JavaPlugin plugin, List<String> ignoredWorlds) {
        this.plugin = plugin;
        this.ignoredWorlds = ignoredWorlds;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // 1. Ignorar mundos configurados
        Entity entity = event.getEntity();

        String worldName = entity.getWorld().getName();
        if (ignoredWorlds.contains(worldName)) {
            return;
        }

        // Filtramos únicamente golems de hierro
        if (entity.getType() == EntityType.IRON_GOLEM) {
            // Tarea repetitiva para las partículas cada 0.5s
            new BukkitRunnable() {
                int ticksPassed = 0;

                @Override
                public void run() {
                    if (ticksPassed >= 60) { // 60 ticks = 3 segundos
                        if (entity instanceof LivingEntity) {
                            LivingEntity le = (LivingEntity) entity;
                            // Inflige daño muy alto para asegurarte de que muera
                            le.damage(1000.0);
                        }
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