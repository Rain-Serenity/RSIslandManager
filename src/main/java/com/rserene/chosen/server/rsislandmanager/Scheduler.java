package com.rserene.chosen.server.rsislandmanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class Scheduler {

    private static final boolean FOLIA;
    private static final Plugin PLUGIN;

    static {
        boolean folia;
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
        } catch (ClassNotFoundException e) {
            folia = false;
        }
        FOLIA = folia;
        PLUGIN = RSIslandManager.getPlugin();
    }

    public static boolean isFolia() {
        return FOLIA;
    }

    public interface Task {
        void cancel();
    }

    public static Task later(Runnable runnable, long delayTicks) {
        if (FOLIA) {
            var task = Bukkit.getGlobalRegionScheduler().runDelayed(PLUGIN, t -> runnable.run(), delayTicks);
            return task::cancel;
        } else {
            var task = Bukkit.getScheduler().runTaskLater(PLUGIN, runnable, delayTicks);
            return task::cancel;
        }
    }

    public static Task timer(Runnable runnable, long delayTicks, long periodTicks) {
        if (FOLIA) {
            var task = Bukkit.getGlobalRegionScheduler().runAtFixedRate(PLUGIN, t -> runnable.run(), delayTicks, periodTicks);
            return task::cancel;
        } else {
            var task = Bukkit.getScheduler().runTaskTimer(PLUGIN, runnable, delayTicks, periodTicks);
            return task::cancel;
        }
    }

    public static Task run(Runnable runnable) {
        return later(runnable, 1);
    }

    public static Task playerLater(Player player, Consumer<Player> consumer, long delayTicks) {
        if (FOLIA) {
            var task = player.getScheduler().runDelayed(PLUGIN, t -> consumer.accept(player), null, delayTicks);
            return task::cancel;
        } else {
            var task = Bukkit.getScheduler().runTaskLater(PLUGIN, () -> consumer.accept(player), delayTicks);
            return task::cancel;
        }
    }

    public static Task playerTimer(Player player, Consumer<Player> consumer, long delayTicks, long periodTicks) {
        if (FOLIA) {
            var task = player.getScheduler().runAtFixedRate(PLUGIN, t -> consumer.accept(player), null, delayTicks, periodTicks);
            return task::cancel;
        } else {
            var task = Bukkit.getScheduler().runTaskTimer(PLUGIN, () -> consumer.accept(player), delayTicks, periodTicks);
            return task::cancel;
        }
    }
}
