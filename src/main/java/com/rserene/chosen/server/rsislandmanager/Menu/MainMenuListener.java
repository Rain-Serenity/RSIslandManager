package com.rserene.chosen.server.rsislandmanager.Menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 合成配方菜单交互 & 自动循环管理
 */
public class MainMenuListener implements Listener {

    private static final Component TITLE_COMP = Component.text(RecipeMenu.TITLE);

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Component titleComp = event.getView().title();
        boolean isMain = titleComp.equals(TITLE_COMP);
        boolean isDetail = !isMain && titleComp.toString().contains("📖");
        if (!isMain && !isDetail) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        if (isMain) {
            if (event.getSlot() == RecipeMenu.SLOT_CLOSE) { player.closeInventory(); return; }
            Inventory detail = RecipeMenu.getDetail(event.getSlot(), player);
            if (detail != null) player.openInventory(detail);
            return;
        }

        if (isDetail) {
            int slot = event.getSlot();
            if (slot == RecipeMenu.SLOT_BACK) {
                RecipeMenu.stopCycling(player);
                player.openInventory(RecipeMenu.buildMain());
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
        // 只有关闭详情页（含 📖）才清理循环任务
        // 不包括主菜单（避免从主菜单点进详情时误杀刚调度的任务）
        if (event.getView().title().toString().contains("📖")) {
            RecipeMenu.clearState(player);
        }
    }
}
