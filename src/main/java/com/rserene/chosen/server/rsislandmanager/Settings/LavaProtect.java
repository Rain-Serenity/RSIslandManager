package com.rserene.chosen.server.rsislandmanager.Settings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

public class LavaProtect implements Listener {
    public LavaProtect() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(RSIslandManager.class));
    }

    //allow bucket obsidian to lava
    @EventHandler(ignoreCancelled = true)
    public void onBucketFillObsidian(PlayerInteractEvent event) {
        if (Action.RIGHT_CLICK_BLOCK != event.getAction())
            return;
        if (!Material.BUCKET.equals(event.getMaterial()))
            return;
        assert event.getClickedBlock() != null;
        if (!Material.OBSIDIAN.equals(event.getClickedBlock().getType()))
            return;

        event.getClickedBlock().setType(Material.LAVA);
        event.setCancelled(true);
        event.getPlayer().sendMessage(Component.text("已经将黑曜石变成岩浆，下次小心点！", NamedTextColor.AQUA, TextDecoration.BOLD));
    }
}
