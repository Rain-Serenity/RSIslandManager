package com.rserene.chosen.server.rsislandmanager.Settings;

import com.rserene.chosen.server.rsislandmanager.Scheduler;
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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SlimeChunk {
    public SlimeChunk() {
        // 构造时不再自注册命令，改为由 RimCommand 统一调用
    }

    /**
     * 执行史莱姆区块检测（供 RimCommand 调用）
     * @param player 执行命令的玩家
     */
    public static void execute(Player player) {
        long startTime = System.currentTimeMillis();
        World world = player.getLocation().getWorld();
        final int x = player.getLocation().getChunk().getX();
        final int blockY = player.getLocation().getBlockY();
        final int z = player.getLocation().getChunk().getZ();

        // 延迟 30 秒后恢复原样
        Scheduler.later(() -> {
            if (!player.isOnline()) return;
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

        // 立即将史莱姆区块标记为绿色
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
        player.sendMessage(Component.text("史莱姆区块寻找完毕" + " 共耗时" + (System.currentTimeMillis() - startTime) + "ms", NamedTextColor.GREEN, TextDecoration.BOLD));
    }
}
