package com.rserene.chosen.server.rsislandmanager.Menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeMenu {
    public static final Inventory MainMenu;
    public static final String TITLE = "额外合成表";
    public static final String INGOT = "锭类";
    static {
        MainMenu = Bukkit.createInventory(null, 54, Component.text(TITLE));
        ItemStack Iron = new ItemStack(Material.IRON_INGOT);
        ItemMeta IronMeta = Iron.getItemMeta();
        IronMeta.displayName(Component.text(INGOT));
        Iron.setItemMeta(IronMeta);
        MainMenu.setItem(0, Iron);
    }

    public static void open(Player player) {
        player.openInventory(MainMenu);
    }
}