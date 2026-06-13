package com.rserene.chosen.server.rsislandmanager.Tips;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

public class Synthesis implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(RSIslandManager.class), () -> {
            event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!", NamedTextColor.AQUA, TextDecoration.BOLD));
            event.getPlayer().sendMessage(Component.text("我们新增了一些设定和合成表", NamedTextColor.AQUA, TextDecoration.BOLD));
            event.getPlayer().sendMessage(Component.text("您可以在我们的官网&Wiki找到", NamedTextColor.AQUA, TextDecoration.BOLD));
            event.getPlayer().sendMessage(Component.text("https://www.RSerene.com/", NamedTextColor.AQUA, TextDecoration.BOLD));
            event.getPlayer().sendMessage(Component.text("不过新增的合成表可以通过/rim recipemenu查看", NamedTextColor.AQUA, TextDecoration.BOLD));
            event.getPlayer().sendMessage(Component.text("如果您是新玩家，请输入/plot auto创建属于你自己的岛屿", NamedTextColor.AQUA, TextDecoration.BOLD));
            event.getPlayer().sendMessage(Component.text("等待创建完成后输入/plot home传送到你自己的岛屿", NamedTextColor.AQUA, TextDecoration.BOLD));
            event.getPlayer().sendMessage(Component.text("如果您想要再次显示这个信息，请输入/rim tips", NamedTextColor.AQUA, TextDecoration.BOLD));
        }, 20L);
    }
}