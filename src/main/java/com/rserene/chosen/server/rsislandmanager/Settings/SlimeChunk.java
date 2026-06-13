package com.rserene.chosen.server.rsislandmanager.Settings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

import java.util.Objects;

public class SlimeChunk implements CommandExecutor {
    public SlimeChunk() {
        Objects.requireNonNull(Bukkit.getPluginCommand("Slime")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        long l = System.currentTimeMillis();
        Player player = (Player) commandSender;
        World world = player.getLocation().getWorld();
        final int x = player.getLocation().getChunk().getX();
        final int blockY = player.getLocation().getBlockY();
        final int z = player.getLocation().getChunk().getZ();

        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(RSIslandManager.class), () -> {
            if (!player.isOnline()) {
                return;
            }
            for (int i = x - 4; i < x + 5; i++) {
                for (int j = z - 4; j < z + 5; j++) {
                    Chunk chunk = world.getChunkAt(i, j);
                    if (chunk.isSlimeChunk()) {
                        for (int a = 0; a < 16; a++) {
                            for (int b = 0; b < 16; b++) {
                                Block block = chunk.getBlock(a, blockY, b);
                                player.sendBlockChange(block.getLocation(), block.getBlockData());
                            }
                        }
                    }
                }
            }
        }, 30 * 20L);
        BlockData blockData = Bukkit.createBlockData(Material.SLIME_BLOCK);
        for (int i = x - 4; i < x + 5; i++) {
            for (int j = z - 4; j < z + 5; j++) {
                Chunk chunk = world.getChunkAt(i, j);
                if (chunk.isSlimeChunk()) {
                    for (int a = 0; a < 16; a++) {
                        for (int b = 0; b < 16; b++) {
                            Location location = chunk.getBlock(a, blockY, b).getLocation();
                            player.sendBlockChange(location, blockData);
                        }
                    }
                }
            }
        }
        commandSender.sendMessage(Component.text("史莱姆区块寻找完毕" + " 共耗时" + (System.currentTimeMillis() - l) + "ms", NamedTextColor.GREEN, TextDecoration.BOLD));
        return true;
    }
}
