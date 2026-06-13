package com.rserene.chosen.server.rsislandmanager.Menu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;


public class MainMenuCommand implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 判断发送者是否为玩家
        if (sender instanceof Player) {
            // 打开主菜单
            RecipeMenu.open(((Player) sender));
        }
        return true;
    }
}
