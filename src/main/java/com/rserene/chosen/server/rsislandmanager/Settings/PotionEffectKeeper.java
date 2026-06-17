package com.rserene.chosen.server.rsislandmanager.Settings;

import com.rserene.chosen.server.rsislandmanager.RSIslandManager;
import com.rserene.chosen.server.rsislandmanager.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class PotionEffectKeeper implements Listener {
    private static final Map<UUID, Collection<PotionEffect>> effect = new HashMap<>();

    public PotionEffectKeeper() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(RSIslandManager.class));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        effect.put(player.getUniqueId(), player.getActivePotionEffects());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        Scheduler.playerLater(player, p -> {
            Collection<PotionEffect> potionEffects = effect.getOrDefault(p.getUniqueId(), List.of());
            for (PotionEffect potionEffect : potionEffects) {
                p.addPotionEffect(potionEffect);
            }
        }, 3L);
    }
}
