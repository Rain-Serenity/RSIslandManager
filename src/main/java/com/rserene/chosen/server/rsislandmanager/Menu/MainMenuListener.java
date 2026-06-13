package com.rserene.chosen.server.rsislandmanager.Menu;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class MainMenuListener implements Listener {
    public static final Random RANDOM = new Random();
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (player.getOpenInventory().title().equals(Component.text(RecipeMenu.TITLE))) {
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null) {
                return;
            }
            if (clickedItem.getItemMeta().displayName().equals(Component.text(RecipeMenu.INGOT))) {
                player.closeInventory();
            }
        }
    }
}
