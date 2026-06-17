package com.rserene.chosen.server.rsislandmanager.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

import java.util.Random;

public class SunflowerBoneMeal implements Listener {

    private static final Random RANDOM = new Random();

    public SunflowerBoneMeal() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(RSIslandManager.class));
    }

    @EventHandler
    public void onBoneMeal(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.BONE_MEAL) return;
        Block target = event.getClickedBlock();
        if (target == null || target.getType() != Material.GRASS_BLOCK) return;
        if (target.getBiome() != Biome.SUNFLOWER_PLAINS) return;
        if (RANDOM.nextInt(10) != 0) return;

        Block bottom = target.getRelative(BlockFace.UP);
        Block top = bottom.getRelative(BlockFace.UP);
        if (!bottom.getType().isAir() || !top.getType().isAir()) return;
        event.setCancelled(true);
        consumeItem(event.getPlayer(), item);
        bottom.setType(Material.SUNFLOWER);
        top.setBlockData(Bukkit.createBlockData("minecraft:sunflower[half=upper]"));
    }

    private void consumeItem(Player player, ItemStack item) {
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() <= 0) {
            player.getInventory().setItemInMainHand(null);
        }
    }
}
