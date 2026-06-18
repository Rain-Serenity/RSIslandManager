package com.rserene.chosen.server.rsislandmanager.Settings;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.entity.EntityDismountEvent;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;
import com.rserene.chosen.server.rsislandmanager.Scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MoreChairs implements Listener {

    public MoreChairs() {
        Bukkit.getPluginManager().registerEvents(this, RSIslandManager.getPlugin());
        Scheduler.timer(() -> {
            map.forEach((uuid, armorStand) -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) {
                    return;
                }
                armorStand.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
            });
        }, 1, 1);
    }

    private static final Map<UUID, ArmorStand> map = new HashMap<>();
    private static final Map<UUID, Location> originLocation = new HashMap<>();
    private static final Map<UUID, Long> sneakTime = new HashMap<>();


    @SuppressWarnings("deprecation")
    @EventHandler
    public void on(PlayerToggleSneakEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            return;
        }

        long currentTimeMillis = System.currentTimeMillis();
        PlayerInventory inventory = event.getPlayer().getInventory();
        if (!inventory.getItemInMainHand().getType().equals(Material.AIR)) {
            return;
        }
        if (!inventory.getItemInOffHand().getType().equals(Material.AIR)) {
            return;
        }
        if (event.getPlayer().getLocation().getPitch() < 75) {
            return;
        }
        if (event.isSneaking()) {
            sneakTime.put(event.getPlayer().getUniqueId(), currentTimeMillis);
            Scheduler.playerLater(event.getPlayer(), p -> {
                if (sneakTime.getOrDefault(p.getUniqueId(), 0L).equals(currentTimeMillis)) {
                    Location location = p.getLocation();
                    Sound sound = Sound.ITEM_ARMOR_EQUIP_TURTLE;
                    p.playSound(location, sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }, 20L);
            return;
        }
        long duration = currentTimeMillis - sneakTime.getOrDefault(event.getPlayer().getUniqueId(), currentTimeMillis);
        sneakTime.remove(event.getPlayer().getUniqueId());
        if (duration < 1000) {
            return;
        }

        if (!event.getPlayer().isOnGround()) {
            return;
        }

            Location location = event.getPlayer().getLocation().add(0, -1.7, 0);
            Entity entity = event.getPlayer().getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            ArmorStand armorStand = (ArmorStand) entity;
            originLocation.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocation());
            armorStand.addPassenger(event.getPlayer());
            armorStand.setGravity(false);
            armorStand.setCanMove(false);
            armorStand.setInvulnerable(true);
            armorStand.setAI(false);
            armorStand.setArms(false);
            armorStand.setCanTick(false);
            armorStand.setVisualFire(false);
            armorStand.setDisabledSlots(EquipmentSlot.values());
            armorStand.setVisible(false);
            map.put(event.getPlayer().getUniqueId(), armorStand);
    }

    @EventHandler
    public void on(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) entity;
            if (!armorStand.isVisible()) {
                if (armorStand.getPassengers().isEmpty()) {
                    armorStand.remove();
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity.getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) entity;
            if (!armorStand.isVisible()) {
                if (armorStand.getPassengers().isEmpty()) {
                    armorStand.remove();
                }
            }
        }
    }

    @EventHandler
    public void on(EntityDismountEvent event) {
        Entity dismounted = event.getDismounted();
        if (dismounted.getType().equals(EntityType.ARMOR_STAND)) {
            if (event.getEntity() instanceof Player) {
                Location origin = originLocation.get(event.getEntity().getUniqueId());
                Location location = event.getEntity().getLocation();
                location.set(origin.getX(), origin.getY(), origin.getZ());
                event.getEntity().teleport(location);
                map.remove(event.getEntity().getUniqueId());
                dismounted.remove();
            }
        }
    }


    @EventHandler
    public void on(PlayerJoinEvent event) {
        Entity vehicle = event.getPlayer().getVehicle();
        if (vehicle instanceof ArmorStand) {
            vehicle.eject();
            vehicle.remove();
            map.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        Entity vehicle = event.getPlayer().getVehicle();
        if (vehicle instanceof ArmorStand) {
            vehicle.eject();
            vehicle.remove();
        }
        map.remove(event.getPlayer().getUniqueId());
        originLocation.remove(event.getPlayer().getUniqueId());
        sneakTime.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void on(PluginDisableEvent event) {
        Plugin plugin = event.getPlugin();
        if (plugin instanceof RSIslandManager) {
            for (UUID uuid : map.keySet()) {
                ArmorStand armorStand = map.get(uuid);
                armorStand.eject();
                armorStand.remove();
            }
        }
    }
}
