package com.rserene.chosen.server.rsislandmanager.Settings;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PlayerHeadDrop implements Listener {
    public static final Map<EntityType, String> drops = new HashMap<>();


    public PlayerHeadDrop() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(RSIslandManager.class));


        Properties properties = null;
        try {
            InputStream inputStream = PlayerHeadDrop.class.getClassLoader().getResourceAsStream("Settings.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            drops.put(EntityType.valueOf(key), value);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.CREEPER)) {
            return;
        }
        Creeper creeper = (Creeper) event.getDamager();
        if (!creeper.isPowered()) {
            return;
        }
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;
        if (livingEntity.getHealth() > event.getFinalDamage()) {
            return;
        }
        NamespacedKey headKey = new NamespacedKey(JavaPlugin.getPlugin(RSIslandManager.class), "player-head");
        if (creeper.getPersistentDataContainer().has(headKey)) {
            event.setCancelled(true);
        } else if (livingEntity.getType().equals(EntityType.PLAYER)) {
            ItemStack itemStack = HeadUtils.getSkull((Player) livingEntity);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(null);
            itemStack.setItemMeta(itemMeta);
            livingEntity.getWorld().dropItem(livingEntity.getLocation(), itemStack);
        } else if (drops.containsKey(livingEntity.getType())) {
            String name = LangUtils.get(livingEntity.getType().translationKey());
            ItemStack itemStack = HeadUtils.getSkullFromValue(name, drops.get(livingEntity.getType()));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(null);
            itemStack.setItemMeta(itemMeta);
            livingEntity.getWorld().dropItem(livingEntity.getLocation(), itemStack);

        } else {
            return;
        }
        livingEntity.setHealth(0);
        creeper.getPersistentDataContainer().set(headKey, PersistentDataType.STRING, "true");
    }
}
