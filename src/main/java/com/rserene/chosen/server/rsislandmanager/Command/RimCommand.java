package com.rserene.chosen.server.rsislandmanager.Command;

import com.rserene.chosen.server.rsislandmanager.Menu.RecipeMenu;
import com.rserene.chosen.server.rsislandmanager.Settings.SlimeChunk;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * RimCommand —— 处理 /rim 命令及其子命令
 *
 * 子命令：
 *   recipemenu  - 打开自定义合成配方菜单
 *   tips        - 显示服务器小提示
 *   slime       - 检测附近的史莱姆区块
 *
 * 支持 Tab 补全。
 */
public class RimCommand implements CommandExecutor, TabCompleter {

    /** 所有子命令列表 */
    private static final List<String> SUB_COMMANDS = List.of("recipemenu", "tips", "slime");

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 无参数时显示帮助
        if (args.length == 0) {
            sender.sendMessage(Component.text("§e§l/rim §7- §fRSIslandManager 管理命令"));
            sender.sendMessage(Component.text("§6/rim recipemenu §7- §f打开自定义合成配方菜单"));
            sender.sendMessage(Component.text("§6/rim tips §7- §f显示服务器小提示"));
            sender.sendMessage(Component.text("§6/rim slime §7- §f检测附近史莱姆区块"));
            return true;
        }

        // 处理子命令
        switch (args[0].toLowerCase()) {
            case "recipemenu" -> {
                // 只有玩家才能打开菜单
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Component.text("§c只有玩家才能打开合成菜单！"));
                    return true;
                }
                RecipeMenu.open(player);
                return true;
            }

            case "tips" -> {
                sender.sendMessage(Component.text("§b§l=================================="));
                sender.sendMessage(Component.text(""));
                sender.sendMessage(Component.text("§b欢迎来到服务器！", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("§f我们新增了一些自定义合成表", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("§f输入 §b/rim recipemenu §f查看所有自定义配方", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("§f您也可以在 Wiki 找到更多信息", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("§b§nhttps://www.RSerene.com/", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("§f如果您是新玩家，请输入 §b/plot auto §f创建属于你自己的岛屿", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text(""));
                sender.sendMessage(Component.text("§b§l=================================="));
                return true;
            }

            case "slime" -> {
                // 仅玩家可用，且需要权限
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Component.text("§c只有玩家才能使用此命令！"));
                    return true;
                }
                if (!player.hasPermission("rsislandmanager.slime")) {
                    player.sendMessage(Component.text("§c你没有权限使用此命令！"));
                    return true;
                }
                SlimeChunk.execute(player);
                return true;
            }

            default -> {
                sender.sendMessage(Component.text("§c未知子命令: " + args[0]));
                sender.sendMessage(Component.text("§e可用命令: /rim recipemenu, /rim tips, /rim slime"));
                return true;
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                       @NotNull String alias, String[] args) {
        // 第一级参数：补全子命令
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            String partial = args[0].toLowerCase();
            for (String sub : SUB_COMMANDS) {
                if (sub.startsWith(partial)) {
                    completions.add(sub);
                }
            }
            return completions;
        }
        // 第二级以后没有补全
        return List.of();
    }
}
