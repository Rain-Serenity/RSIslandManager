package com.rserene.chosen.server.rsislandmanager;

import org.bukkit.*;
import com.rserene.chosen.server.rsislandmanager.Menu.*;
import com.rserene.chosen.server.rsislandmanager.Command.RimCommand;
import com.rserene.chosen.server.rsislandmanager.Tips.Synthesis;
import com.rserene.chosen.server.rsislandmanager.Tips.SynthesisCommand;
import org.bukkit.plugin.java.JavaPlugin;
import com.rserene.chosen.server.rsislandmanager.Recipe.MoreRecipe;
import com.rserene.chosen.server.rsislandmanager.Settings.*;

import java.util.Objects;

public final class RSIslandManager extends JavaPlugin {

    private static RSIslandManager instance;

    public static RSIslandManager getPlugin() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        if (this.getConfig().getBoolean("FirstSapling")) {
            try {
                new FirstSapling();
                getLogger().info("Enabled first sapling.");
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        if (this.getConfig().getBoolean("LavaProtect")) {
            try {
                new LavaProtect();
                getLogger().info("Enabled lava protect.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("PotionEffectKeeper")) {
            try {
                new PotionEffectKeeper();
                getLogger().info("Enabled potion effect keeper.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("HungerKeeper")) {
            try {
                new HungerKeeper();
                getLogger().info("Enabled hunger sapling.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("RailWay")) {
            try {
                new RailWay();
                getLogger().info("Enabled railway.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("IronElevator")) {
            try {

                new IronElevator();
                getLogger().info("Enabled iron elevator.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("SitDownAnywhere")) {
            try {

                new MoreChairs();
                getLogger().info("Enabled sit down anywhere.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("PlayerHeadDrop")) {
            try {

                new PlayerHeadDrop();
                getLogger().info("Enabled player head drop.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("SlimeChunkCommand")) {
            try {

                new SlimeChunk();
                getLogger().info("Enabled slime chunk command.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("ElderGuardianSpawner")) {
            try {

                new ElderGuardianSpawner();
                getLogger().info("Enabled elder guardian spawner.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("ShulkerRespawn")) {
            try {

                new ShulkerRespawn();
                getLogger().info("Enabled shulker respawn.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("DeepOceanGuardian")) {
            try {
                new DeepOceanGuardian();
                getLogger().info("Enabled DeepOceanGuardian.");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("CoralBoneMeal")) {
            try {
                new CoralBoneMeal();
                getLogger().info("Enabled coral bone meal.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.getConfig().getBoolean("SetGameRule")) {
            try {

                for (World world : Bukkit.getWorlds()) {
                    world.setGameRule(GameRules.KEEP_INVENTORY, true);
                    world.setDifficulty(Difficulty.HARD);
                }
                getLogger().info("SetGameRule Successfully");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (this.getConfig().getBoolean("MoreRecipe")) {
            MoreRecipe.register(this);
            if (this.getConfig().getBoolean("Synthesis")) {
                Bukkit.getPluginManager().registerEvents(new Synthesis(), this);
                getLogger().info("Register Synthesis Successfully");
            }
            if (this.getConfig().getBoolean("BanBat")) {
                Bukkit.getPluginManager().registerEvents(new DeleteSomeCreatures(), this);
                getLogger().info("Register DeleteSomeCreatures Successfully");
            }
            // 接入统计系统
            getLogger().info("Metrics Loading Successfully");
            Bukkit.getPluginManager().registerEvents(new MainMenuListener(), this);
            getLogger().info("Listener Register Successfully");
            // 注册 /rim 主命令（含 recipemenu / tips / slime 子命令，支持 Tab 补全）
            Objects.requireNonNull(Bukkit.getPluginCommand("rim")).setExecutor(new RimCommand());
            Objects.requireNonNull(Bukkit.getPluginCommand("rim")).setTabCompleter(new RimCommand());
            getLogger().info("Command Loading Successfully");
            getLogger().info("RSIslandManager Loaded Successfully !");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.clearRecipes();
    }
}
