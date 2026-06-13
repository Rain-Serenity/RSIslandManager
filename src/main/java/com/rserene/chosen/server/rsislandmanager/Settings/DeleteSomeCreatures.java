package com.rserene.chosen.server.rsislandmanager.Settings;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class DeleteSomeCreatures implements Listener {
    @EventHandler
    public void on(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType().equals(EntityType.BAT)) {
            entity.remove();
        }
        if (entity.getType().equals(EntityType.PHANTOM)) {
            entity.remove();
        }
        if (entity.getType().equals(EntityType.GLOW_SQUID)) {
            Random random = new Random();
            if (random.nextInt(5) + 1 != 1) {
                entity.remove();
            }
        }
    }
}