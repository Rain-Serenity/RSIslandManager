package com.rserene.chosen.server.rsislandmanager.Recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import com.rserene.chosen.server.rsislandmanager.RSIslandManager;

public class MoreRecipe {

    public static void register(RSIslandManager plugin) {
        Bukkit.resetRecipes();

        SmithingTransformRecipe Iron_NETHER = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Ironj_recipe"),
            new ItemStack(Material.IRON_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.NETHER_BRICK),
            new RecipeChoice.MaterialChoice(Material.WHITE_DYE)
        );
        Bukkit.addRecipe(Iron_NETHER);

        CampfireRecipe Iron_NUGGET = new CampfireRecipe(
            new NamespacedKey(plugin, "iron_nugget_recipe"),
            new ItemStack(Material.IRON_NUGGET),
            Material.BONE_BLOCK,
            (float) 0.2,
            300
        );
        Bukkit.addRecipe(Iron_NUGGET);

        SmithingTransformRecipe Iron_GOLD = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Ironi_recipe"),
            new ItemStack(Material.IRON_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.GOLD_INGOT),
            new RecipeChoice.MaterialChoice(Material.WHITE_DYE)
        );
        Bukkit.addRecipe(Iron_GOLD);

        SmithingTransformRecipe Iron_NETHER_INGOT = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Ironh_recipe"),
            new ItemStack(Material.IRON_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT),
            new RecipeChoice.MaterialChoice(Material.WHITE_DYE)
        );
        Bukkit.addRecipe(Iron_NETHER_INGOT);

        SmithingTransformRecipe Iron_COPPER = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Irong_recipe"),
            new ItemStack(Material.IRON_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.COPPER_INGOT),
            new RecipeChoice.MaterialChoice(Material.WHITE_DYE)
        );
        Bukkit.addRecipe(Iron_COPPER);

        SmithingTransformRecipe Gold_IRON = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Goldf_recipe"),
            new ItemStack(Material.GOLD_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.IRON_INGOT),
            new RecipeChoice.MaterialChoice(Material.YELLOW_DYE)
        );
        Bukkit.addRecipe(Gold_IRON);

        SmithingTransformRecipe Gold_COPPER = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Golde_recipe"),
            new ItemStack(Material.GOLD_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.COPPER_INGOT),
            new RecipeChoice.MaterialChoice(Material.YELLOW_DYE)
        );
        Bukkit.addRecipe(Gold_COPPER);

        SmithingTransformRecipe Gold_NETHER_INGOT_A = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Gold_NETHER_INGOT_recipe"),
            new ItemStack(Material.GOLD_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT),
            new RecipeChoice.MaterialChoice(Material.YELLOW_DYE)
        );
        Bukkit.addRecipe(Gold_NETHER_INGOT_A);

        SmithingTransformRecipe Gold_NETHER = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Goldc_recipe"),
            new ItemStack(Material.GOLD_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.NETHER_BRICK),
            new RecipeChoice.MaterialChoice(Material.YELLOW_DYE)
        );
        Bukkit.addRecipe(Gold_NETHER);

        SmithingTransformRecipe Purpur_block = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "purpur_block_recipe"),
            new ItemStack(Material.PURPUR_BLOCK),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.STONE),
            new RecipeChoice.MaterialChoice(Material.SHULKER_BOX)
        );
        Bukkit.addRecipe(Purpur_block);

        ItemStack Podzal = new ItemStack(Material.PODZOL);
        NamespacedKey Podzalkey = new NamespacedKey(plugin, "Podzal_key");
        ShapedRecipe Podzalrecipe = new ShapedRecipe(Podzalkey, Podzal);
        Podzalrecipe.shape(" S ", " D ", " B ");
        Podzalrecipe.setIngredient('S', Material.SPRUCE_SAPLING);
        Podzalrecipe.setIngredient('D', Material.DIRT);
        Podzalrecipe.setIngredient('B', Material.BONE_MEAL);
        Bukkit.addRecipe(Podzalrecipe);

        SmithingTransformRecipe Nether_INGOT_GOLD = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Netherb_INGOT_recipe"),
            new ItemStack(Material.NETHERITE_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.GOLD_INGOT),
            new RecipeChoice.MaterialChoice(Material.BLACK_DYE)
        );
        Bukkit.addRecipe(Nether_INGOT_GOLD);

        SmithingTransformRecipe Nether_INGOT_Iron = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Nethera_INGOT_recipe"),
            new ItemStack(Material.NETHERITE_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.IRON_INGOT),
            new RecipeChoice.MaterialChoice(Material.BLACK_DYE)
        );
        Bukkit.addRecipe(Nether_INGOT_Iron);

        SmithingTransformRecipe COPPER_INGOT = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "COPPER_INGOT_recipe"),
            new ItemStack(Material.COPPER_INGOT),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT),
            new RecipeChoice.MaterialChoice(Material.ORANGE_DYE)
        );
        Bukkit.addRecipe(COPPER_INGOT);

        StonecuttingRecipe GravelStonecuttingRecipe = new StonecuttingRecipe(
            new NamespacedKey(plugin, "GravelStonecuttingRecipe_recipe"),
            new ItemStack(Material.GRAVEL),
            Material.COBBLESTONE
        );
        Bukkit.addRecipe(GravelStonecuttingRecipe);

        SmithingTransformRecipe Mycelium = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "mycelium_recipe"),
            new ItemStack(Material.MYCELIUM),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.GRASS_BLOCK),
            new RecipeChoice.MaterialChoice(Material.BROWN_MUSHROOM)
        );
        Bukkit.addRecipe(Mycelium);

        SmithingTransformRecipe COBBLED_DEEPSLATE = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "COBBLED_DEEPSLATE_recipe"),
            new ItemStack(Material.COBBLED_DEEPSLATE),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE),
            new RecipeChoice.MaterialChoice(Material.BLACK_DYE)
        );
        Bukkit.addRecipe(COBBLED_DEEPSLATE);

        SmithingTransformRecipe Grass_block = new SmithingTransformRecipe(
            new NamespacedKey(plugin, "Grass_block_recipe"),
            new ItemStack(Material.GRASS_BLOCK),
            RecipeChoice.empty(),
            new RecipeChoice.MaterialChoice(Material.DIRT),
            new RecipeChoice.MaterialChoice(Material.GRASS_BLOCK)
        );
        Bukkit.addRecipe(Grass_block);

        CampfireRecipe DIAMOND = new CampfireRecipe(
            new NamespacedKey(plugin, "diamond_recipe"),
            new ItemStack(Material.DIAMOND),
            Material.POISONOUS_POTATO,
            (float) 1.0,
            300
        );
        Bukkit.addRecipe(DIAMOND);

        BlastingRecipe Sand = new BlastingRecipe(
            new NamespacedKey(plugin, "sand_recipe"),
            new ItemStack(Material.SAND),
            Material.GRAVEL,
            (float) 0.8,
            100
        );
        Bukkit.addRecipe(Sand);

        BlastingRecipe BLAZE_POWDER = new BlastingRecipe(
            new NamespacedKey(plugin, "BLAZE_POWDER_recipe"),
            new ItemStack(Material.BLAZE_POWDER),
            Material.REDSTONE,
            (float) 0.8,
            110
        );
        Bukkit.addRecipe(BLAZE_POWDER);

        BlastingRecipe Quartz = new BlastingRecipe(
            new NamespacedKey(plugin, "Quartz_recipe"),
            new ItemStack(Material.QUARTZ),
            Material.GLASS,
            (float) 0.8,
            100
        );
        Bukkit.addRecipe(Quartz);

        SmokingRecipe SoulSoil = new SmokingRecipe(
            new NamespacedKey(plugin, "SoulSoil_recipe"),
            new ItemStack(Material.SOUL_SOIL),
            Material.COARSE_DIRT,
            (float) 0.2,
            180
        );
        Bukkit.addRecipe(SoulSoil);

        SmokingRecipe Netherrack = new SmokingRecipe(
            new NamespacedKey(plugin, "Netherrack_recipe"),
            new ItemStack(Material.NETHERRACK),
            Material.COBBLESTONE,
            (float) 1.2,
            200
        );
        Bukkit.addRecipe(Netherrack);

        StonecuttingRecipe SoulSand = new StonecuttingRecipe(
            new NamespacedKey(plugin, "SoulSand_recipe"),
            new ItemStack(Material.SOUL_SAND),
            Material.SOUL_SOIL
        );
        Bukkit.addRecipe(SoulSand);

        ItemStack END_STONE = new ItemStack(Material.END_STONE);
        NamespacedKey END_STONE_key = new NamespacedKey(plugin, "END_STONE_recipe");
        ShapedRecipe END_STONE_recipe = new ShapedRecipe(END_STONE_key, END_STONE);
        END_STONE_recipe.shape("YEY", "ESE", "YEY");
        END_STONE_recipe.setIngredient('Y', Material.YELLOW_DYE);
        END_STONE_recipe.setIngredient('E', Material.ENDER_PEARL);
        END_STONE_recipe.setIngredient('S', Material.STONE);
        Bukkit.addRecipe(END_STONE_recipe);

        ItemStack AMETHYST_SHARD = new ItemStack(Material.AMETHYST_SHARD);
        NamespacedKey AMETHYST_SHARD_KEY = new NamespacedKey(plugin, "COLA_KEY");
        ShapelessRecipe AMETHYST_SHARDRecipe = new ShapelessRecipe(AMETHYST_SHARD_KEY, AMETHYST_SHARD);
        AMETHYST_SHARDRecipe.addIngredient(Material.PRISMARINE_SHARD);
        AMETHYST_SHARDRecipe.addIngredient(Material.PURPLE_DYE);
        Bukkit.addRecipe(AMETHYST_SHARDRecipe);

        ItemStack BLAZE_ROD_SHARD = new ItemStack(Material.BLAZE_ROD);
        NamespacedKey BLAZE_ROD_SHARD_KEY = new NamespacedKey(plugin, "BLAZE_ROD_KEY");
        ShapelessRecipe BLAZE_ROD_Recipe = new ShapelessRecipe(BLAZE_ROD_SHARD_KEY, BLAZE_ROD_SHARD);
        BLAZE_ROD_Recipe.addIngredient(Material.BLAZE_POWDER);
        BLAZE_ROD_Recipe.addIngredient(Material.BLAZE_POWDER);
        Bukkit.addRecipe(BLAZE_ROD_Recipe);

        ItemStack COAL = new ItemStack(Material.COAL);
        NamespacedKey COAL_KEY = new NamespacedKey(plugin, "COAL_KEY");
        ShapelessRecipe COALRecipe = new ShapelessRecipe(COAL_KEY, COAL);
        COALRecipe.addIngredient(Material.CHARCOAL);
        Bukkit.addRecipe(COALRecipe);

        ItemStack FLINT = new ItemStack(Material.FLINT);
        NamespacedKey FLINT_KEY = new NamespacedKey(plugin, "FLINT_KEY");
        ShapelessRecipe FLINTRecipe = new ShapelessRecipe(FLINT_KEY, FLINT);
        FLINTRecipe.addIngredient(2, Material.GRAVEL);
        FLINTRecipe.addIngredient(1, Material.WOODEN_SHOVEL);
        Bukkit.addRecipe(FLINTRecipe);

        plugin.getLogger().info("MoreRecipe Loaded Successfully");
    }
}
