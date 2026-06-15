package com.rserene.chosen.server.rsislandmanager.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

public class DeepOceanGuardian implements Listener {

    public DeepOceanGuardian() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(RSIslandManager.class));
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(CreatureSpawnEvent event) {
        EntityType type = event.getEntityType();
        if (type != EntityType.COD && type != EntityType.SALMON
            && type != EntityType.PUFFERFISH && type != EntityType.TROPICAL_FISH
            && type != EntityType.SQUID) return;

        Location loc = event.getLocation();
        if (!"deep_cold_ocean".equals(loc.getBlock().getBiome().getKey().getKey())) return;

        event.setCancelled(true);
        loc.getWorld().spawnEntity(loc, EntityType.GUARDIAN);
    }
}
