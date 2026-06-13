package com.rserene.chosen.server.rsislandmanager.Tips;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Synthesis implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        try {
            Thread.currentThread().sleep(1); // 等待1毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!", NamedTextColor.AQUA, TextDecoration.BOLD));
        event.getPlayer().sendMessage(Component.text("欢迎加入服务器，我们新增了一些自定义合成表", NamedTextColor.AQUA, TextDecoration.BOLD));
        event.getPlayer().sendMessage(Component.text("您可以在我们的Wiki找到", NamedTextColor.AQUA, TextDecoration.BOLD));
        event.getPlayer().sendMessage(Component.text("https://Wiki.Skydom.org/        点击链接打开", NamedTextColor.AQUA, TextDecoration.BOLD));
        event.getPlayer().sendMessage(Component.text("如果您是新玩家，请输入/is创建属于你自己的岛屿", NamedTextColor.AQUA, TextDecoration.BOLD));
        event.getPlayer().sendMessage(Component.text("如果您想要再次显示这个信息，请输入/Tips 1", NamedTextColor.AQUA, TextDecoration.BOLD));
    }
}