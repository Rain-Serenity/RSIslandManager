package com.rserene.chosen.server.rsislandmanager.Settings;

import com.rserene.chosen.server.rsislandmanager.RSIslandManager;
import com.rserene.chosen.server.rsislandmanager.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SunflowerBoneMeal implements Listener {

    private static final Random RANDOM = new Random();

    public SunflowerBoneMeal() {
        Bukkit.getPluginManager().registerEvents(this, RSIslandManager.getPlugin());
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

        // 等原版催熟完成后，替换 30% 的花草为向日葵
        Location origin = target.getLocation();
        Scheduler.later(() -> {
            List<Block> flowers = new ArrayList<>();
            for (int x = -4; x <= 4; x++) {
                for (int z = -4; z <= 4; z++) {
                    Block b = origin.clone().add(x, 1, z).getBlock();
                    Material t = b.getType();
                    if (t == Material.SHORT_GRASS || t == Material.FERN
                        || t == Material.DANDELION || t == Material.POPPY
                        || t == Material.BLUE_ORCHID || t == Material.ALLIUM
                        || t == Material.AZURE_BLUET || t == Material.ORANGE_TULIP
                        || t == Material.PINK_TULIP || t == Material.RED_TULIP
                        || t == Material.WHITE_TULIP || t == Material.OXEYE_DAISY
                        || t == Material.CORNFLOWER || t == Material.LILY_OF_THE_VALLEY) {
                        flowers.add(b);
                    }
                }
            }
            // 随机选 30% 替换成向日葵
            int count = Math.max(1, flowers.size() * 20 / 100);
            for (int i = 0; i < count && !flowers.isEmpty(); i++) {
                Block b = flowers.remove(RANDOM.nextInt(flowers.size()));
                Block top = b.getRelative(BlockFace.UP);
                if (!top.getType().isAir()) continue;
                b.setType(Material.SUNFLOWER);
                BlockData upper = Bukkit.createBlockData("minecraft:sunflower[half=upper]");
                top.setBlockData(upper);
            }
        }, 1);
    }
}
