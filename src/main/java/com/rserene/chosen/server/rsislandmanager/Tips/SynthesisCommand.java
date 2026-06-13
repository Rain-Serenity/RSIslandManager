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
                sender.sendMessage(Component.text("我们新增了一些自定义合成表", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("您可以在我们的Wiki找到", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("https://Wiki.Skydom.org/        点击链接打开", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("如果您是新玩家，请输入/is创建属于你自己的岛屿", NamedTextColor.AQUA, TextDecoration.BOLD));
                sender.sendMessage(Component.text("如果您想要再次显示这个信息，请输入/Tips 1", NamedTextColor.AQUA, TextDecoration.BOLD));
            } else if (arg_to_string.equals("help")){
                sender.sendMessage(Component.text("/Tips 1 显示小提示", NamedTextColor.AQUA, TextDecoration.BOLD));
            }
            else {
                sender.sendMessage(Component.text("你输入的指令好像有问题哎，快快输入/Tips help查看帮助吧！", NamedTextColor.AQUA));
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