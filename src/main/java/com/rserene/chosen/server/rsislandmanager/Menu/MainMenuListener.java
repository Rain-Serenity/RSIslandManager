package com.rserene.chosen.server.rsislandmanager.Menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class MainMenuListener implements Listener {

    private static final Component TITLE_COMP = Component.text(RecipeMenu.TITLE);
    private static final int SLOT_PREV = 45;
    private static final int SLOT_NEXT = 52;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Component titleComp = event.getView().title();
        boolean isMain = titleComp.equals(TITLE_COMP);
        boolean isDetail = !isMain && titleComp.toString().contains("§8[");
        if (!isMain && !isDetail) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        if (isMain) {
            int slot = event.getSlot();
            if (slot == RecipeMenu.SLOT_CLOSE) { player.closeInventory(); return; }
            if (slot == SLOT_PREV) {
                int pg = RecipeMenu.getPage(player) - 1;
                if (pg >= 0) { RecipeMenu.setPage(player, pg); player.openInventory(RecipeMenu.buildMain(pg)); }
                return;
            }
            if (slot == SLOT_NEXT) {
                int pg = RecipeMenu.getPage(player) + 1;
                int last = (RecipeMenu.ALL_SIZE + RecipeMenu.ITEMS_PER_PAGE - 1) / RecipeMenu.ITEMS_PER_PAGE - 1;
                if (pg <= last) { RecipeMenu.setPage(player, pg); player.openInventory(RecipeMenu.buildMain(pg)); }
                return;
            }
            Inventory detail = RecipeMenu.getDetail(slot, player, RecipeMenu.getPage(player));
            if (detail != null) player.openInventory(detail);
            return;
        }

        if (isDetail) {
            int slot = event.getSlot();
            if (slot == RecipeMenu.SLOT_BACK) {
                RecipeMenu.stopCycling(player);
                player.openInventory(RecipeMenu.buildMain(RecipeMenu.getPage(player)));
                return;
            }
            if (slot == RecipeMenu.SLOT_CLOSE) {
                RecipeMenu.stopCycling(player);
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (event.getView().title().toString().contains("§8[")) {
            RecipeMenu.clearState(player);
        }
    }
}
