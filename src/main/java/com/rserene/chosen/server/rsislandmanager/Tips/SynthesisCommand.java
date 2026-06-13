package com.rserene.chosen.server.rsislandmanager.Tips;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class SynthesisCommand implements CommandExecutor, TabCompleter {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0){
            sender.sendMessage(Component.text("你输入的指令好像有问题哎，快快输入/Tips help查看帮助吧！", NamedTextColor.AQUA));
            return true;
        }
        if(args.length==1){
            String arg_to_string=args[0];
            if (arg_to_string.equals("1")){
                sender.sendMessage(Component.text("Hello, " + sender.getName() + "!", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("我们新增了一些设定和合成表", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("您可以在我们的官网&Wiki找到", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("https://www.RSerene.com/", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("不过新增的合成表可以通过/rim recipemenu查看", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("如果您是新玩家，请输入/plot auto创建属于你自己的岛屿", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("等待创建完成后输入/plot home传送到你自己的岛屿", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("如果您想要再次显示这个信息，请输入/rim tips", NamedTextColor.AQUA, TextDecoration.BOLD));
            } else if (arg_to_string.equals("help")){
                sender.sendMessage(Component.text("/rim tips 显示小提示", NamedTextColor.AQUA, TextDecoration.BOLD));
            }
            else {
                sender.sendMessage(Component.text("你输入的指令好像有问题哎，快快输入/rim查看帮助吧！", NamedTextColor.AQUA));
            }
            return true;
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if(args.length==1)
        {
            List<String> list = new ArrayList<>();
            list.add ("1");
            list.add ("help");
            return list;
        }
        return null;
    }
}
// Tips