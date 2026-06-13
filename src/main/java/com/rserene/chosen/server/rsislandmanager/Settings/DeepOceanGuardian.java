package com.rserene.chosen.server.rsislandmanager.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

public class DeepOceanGuardian implements Listener {

    private static final Biome TARGET_BIOME = Biome.DEEP_COLD_OCEAN;

    public DeepOceanGuardian() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(RSIslandManager.class));
    }

    @EventHandler(ignoreCancelled = true)
    public void onNaturalSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;
        EntityType type = event.getEntityType();
        if (type != EntityType.SQUID && type != EntityType.COD
            && type != EntityType.SALMON && type != EntityType.PUFFERFISH
            && type != EntityType.TROPICAL_FISH) return;

        Location loc = event.getLocation();
        if (loc.getBlock().getBiome() != TARGET_BIOME) return;

        event.setCancelled(true);
        loc.getWorld().spawnEntity(loc, EntityType.GUARDIAN, CreatureSpawnEvent.SpawnReason.NATURAL);
    }
}
