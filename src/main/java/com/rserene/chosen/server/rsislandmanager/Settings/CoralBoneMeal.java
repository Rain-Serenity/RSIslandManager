package com.rserene.chosen.server.rsislandmanager.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoralBoneMeal implements Listener {

    private static final Random RANDOM = new Random();

    private static final List<Material> CORAL_BLOCKS = List.of(
        Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK,
        Material.BUBBLE_CORAL_BLOCK, Material.FIRE_CORAL_BLOCK,
        Material.HORN_CORAL_BLOCK);

    private static final List<Material> CORAL_PLANTS = List.of(
        Material.TUBE_CORAL, Material.BRAIN_CORAL,
        Material.BUBBLE_CORAL, Material.FIRE_CORAL,
        Material.HORN_CORAL);

    private static final List<Material> CORAL_FANS = List.of(
        Material.TUBE_CORAL_FAN, Material.BRAIN_CORAL_FAN,
        Material.BUBBLE_CORAL_FAN, Material.FIRE_CORAL_FAN,
        Material.HORN_CORAL_FAN);

    public CoralBoneMeal() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(RSIslandManager.class));
    }

    @EventHandler
    public void onBoneMeal(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.BONE_MEAL) return;
        Block target = event.getClickedBlock();
        if (target == null) return;
        if (target.getBiome() != Biome.WARM_OCEAN) return;

        Location loc = target.getLocation();
        if (!isUnderWater(loc)) return;

        event.setCancelled(true);
        consumeItem(event.getPlayer(), item);
        generateCoral(loc, event.getPlayer());
    }

    private boolean isUnderWater(Location loc) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 0; dy <= 2; dy++) {
                    Material type = loc.clone().add(dx, dy, dz).getBlock().getType();
                    if (type == Material.WATER || type == Material.SEAGRASS || type == Material.TALL_SEAGRASS) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void consumeItem(Player player, ItemStack item) {
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() <= 0) {
            player.getInventory().setItemInMainHand(null);
        }
    }

    private void generateCoral(Location center, Player player) {
        int range = 3;
        List<Block> candidates = new ArrayList<>();
        for (int dx = -range; dx <= range; dx++) {
            for (int dz = -range; dz <= range; dz++) {
                for (int dy = -1; dy <= 1; dy++) {
                    Block b = center.clone().add(dx, dy, dz).getBlock();
                    if (b.getType() == Material.WATER && b.getY() < center.getWorld().getMaxHeight()) {
                        Block below = b.getRelative(BlockFace.DOWN);
                        if (below.getType().isSolid()) {
                            candidates.add(b);
                        }
                    }
                }
            }
        }

        if (candidates.isEmpty()) return;

        for (Block b : candidates) {
            if (b.getType() != Material.WATER) continue;
            if (RANDOM.nextDouble() > 0.45) continue;

            double roll = RANDOM.nextDouble();
            if (roll < 0.70) {
                Block above = b.getRelative(BlockFace.UP);
                if (above.getType() == Material.WATER && RANDOM.nextBoolean()) {
                    above.setType(Material.TALL_SEAGRASS, false);
                    b.setType(Material.TALL_SEAGRASS);
                } else {
                    b.setType(Material.SEAGRASS);
                }
            } else if (roll < 0.75) {
                b.setType(CORAL_BLOCKS.get(RANDOM.nextInt(CORAL_BLOCKS.size())));
            } else if (roll < 0.90) {
                b.setType(CORAL_PLANTS.get(RANDOM.nextInt(CORAL_PLANTS.size())));
            } else {
                b.setType(CORAL_FANS.get(RANDOM.nextInt(CORAL_FANS.size())));
            }
        }

        player.getWorld().playSound(center, Sound.ITEM_BONE_MEAL_USE, 1.0f, 1.0f);
    }
}
