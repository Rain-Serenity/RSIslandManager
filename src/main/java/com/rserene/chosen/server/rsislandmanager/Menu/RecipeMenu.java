package com.rserene.chosen.server.rsislandmanager.Menu;

import com.rserene.chosen.server.rsislandmanager.RSIslandManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.Function;

public class RecipeMenu {

    public static final String TITLE = "§7额外合成配方";
    public static final int ITEMS_PER_PAGE = 21;

    public static final int SLOT_BACK  = 45;
    public static final int SLOT_CLOSE = 53;

    private static final Map<UUID, Integer> CURRENT_PAGE = new HashMap<>();

    public record SInfo(Material t, Material b, Material a, Material r) {}
    public record ShpInfo(String[] s, Map<Character, Material> i, Material r) {}
    public record ShlInfo(List<Material> i, Material r) {}
    public record CkInfo(Material i, Material r, String t) {}
    public record StInfo(Material i, Material r) {}

    private static final LinkedHashMap<Material, List<SInfo>> COMBINED = new LinkedHashMap<>();
    static {
        COMBINED.put(Material.IRON_INGOT, List.of(
            new SInfo(Material.AIR, Material.NETHER_BRICK,    Material.WHITE_DYE,  Material.IRON_INGOT),
            new SInfo(Material.AIR, Material.GOLD_INGOT,      Material.WHITE_DYE,  Material.IRON_INGOT),
            new SInfo(Material.AIR, Material.NETHERITE_INGOT, Material.WHITE_DYE,  Material.IRON_INGOT),
            new SInfo(Material.AIR, Material.COPPER_INGOT,    Material.WHITE_DYE,  Material.IRON_INGOT)));
        COMBINED.put(Material.GOLD_INGOT, List.of(
            new SInfo(Material.AIR, Material.IRON_INGOT,      Material.YELLOW_DYE, Material.GOLD_INGOT),
            new SInfo(Material.AIR, Material.COPPER_INGOT,    Material.YELLOW_DYE, Material.GOLD_INGOT),
            new SInfo(Material.AIR, Material.NETHERITE_INGOT, Material.YELLOW_DYE, Material.GOLD_INGOT),
            new SInfo(Material.AIR, Material.NETHER_BRICK,    Material.YELLOW_DYE, Material.GOLD_INGOT)));
        COMBINED.put(Material.NETHERITE_INGOT, List.of(
            new SInfo(Material.AIR, Material.GOLD_INGOT,      Material.BLACK_DYE,  Material.NETHERITE_INGOT),
            new SInfo(Material.AIR, Material.IRON_INGOT,      Material.BLACK_DYE,  Material.NETHERITE_INGOT),
            new SInfo(Material.AIR, Material.COPPER_INGOT,    Material.BLACK_DYE,  Material.NETHERITE_INGOT),
            new SInfo(Material.AIR, Material.NETHER_BRICK,    Material.BLACK_DYE,  Material.NETHERITE_INGOT)));
        COMBINED.put(Material.COPPER_INGOT, List.of(
            new SInfo(Material.AIR, Material.NETHERITE_INGOT, Material.ORANGE_DYE, Material.COPPER_INGOT),
            new SInfo(Material.AIR, Material.IRON_INGOT,      Material.ORANGE_DYE, Material.COPPER_INGOT),
            new SInfo(Material.AIR, Material.GOLD_INGOT,      Material.ORANGE_DYE, Material.COPPER_INGOT),
            new SInfo(Material.AIR, Material.NETHER_BRICK,    Material.ORANGE_DYE, Material.COPPER_INGOT)));
    }

    private static final List<SInfo> SMITHING = List.of(
        new SInfo(Material.AIR, Material.DIRT,           Material.SHORT_GRASS,     Material.GRASS_BLOCK),
        new SInfo(Material.AIR, Material.COBBLESTONE,    Material.BLACK_DYE,       Material.COBBLED_DEEPSLATE),
        new SInfo(Material.AIR, Material.STONE,          Material.SHULKER_BOX,     Material.PURPUR_BLOCK),
        new SInfo(Material.AIR, Material.GRASS_BLOCK,    Material.BROWN_MUSHROOM,  Material.MYCELIUM),
        new SInfo(Material.AIR, Material.RED_MUSHROOM,   Material.BROWN_MUSHROOM,  Material.NETHER_WART),
        new SInfo(Material.AIR, Material.NETHER_WART,    Material.BLAZE_POWDER,    Material.CRIMSON_FUNGUS),
        new SInfo(Material.AIR, Material.CRIMSON_FUNGUS, Material.MANGROVE_PROPAGULE, Material.WARPED_FUNGUS),
        new SInfo(Material.AIR, Material.NETHERRACK,     Material.CRIMSON_FUNGUS,  Material.CRIMSON_NYLIUM),
        new SInfo(Material.AIR, Material.NETHERRACK,     Material.WARPED_FUNGUS,   Material.WARPED_NYLIUM));

    private static final List<ShpInfo> SHAPED = List.of(
        new ShpInfo(new String[]{" S ", " D ", " B "}, Map.of('S',Material.SPRUCE_SAPLING,'D',Material.DIRT,'B',Material.BONE_MEAL), Material.PODZOL),
        new ShpInfo(new String[]{"YEY", "ESE", "YEY"}, Map.of('Y',Material.YELLOW_DYE,'E',Material.ENDER_PEARL,'S',Material.STONE), Material.END_STONE),
        new ShpInfo(new String[]{"NIN", "IDI", "NIN"}, Map.of('N',Material.NETHERRACK,'I',Material.NETHERITE_INGOT,'D',Material.DIAMOND), Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
        new ShpInfo(new String[]{"GGG", "GBG", "GGG"}, Map.of('G',Material.GOLD_INGOT,'B',Material.BLACKSTONE), Material.GILDED_BLACKSTONE));

    private static final List<ShlInfo> SHAPELESS = List.of(
        new ShlInfo(List.of(Material.CHARCOAL), Material.COAL),
        new ShlInfo(List.of(Material.BLAZE_POWDER, Material.BLAZE_POWDER), Material.BLAZE_ROD),
        new ShlInfo(List.of(Material.PRISMARINE_SHARD, Material.PURPLE_DYE), Material.AMETHYST_SHARD),
        new ShlInfo(List.of(Material.GRAVEL, Material.GRAVEL, Material.WOODEN_SHOVEL), Material.FLINT));

    private static final List<CkInfo> COOKING = List.of(
        new CkInfo(Material.BONE_BLOCK,       Material.IRON_NUGGET,  "营火"),
        new CkInfo(Material.POISONOUS_POTATO, Material.DIAMOND,     "营火"),
        new CkInfo(Material.SKELETON_SKULL,   Material.WITHER_SKELETON_SKULL, "营火"),
        new CkInfo(Material.GRAVEL,           Material.SAND,         "高炉"),
        new CkInfo(Material.GLASS,            Material.QUARTZ,       "高炉"),
        new CkInfo(Material.REDSTONE,         Material.BLAZE_POWDER, "高炉"),
        new CkInfo(Material.COBBLESTONE,      Material.NETHERRACK,   "烟熏炉"),
        new CkInfo(Material.COARSE_DIRT,      Material.SOUL_SOIL,    "烟熏炉"));

    private static final List<StInfo> STONECUT = List.of(
        new StInfo(Material.COBBLESTONE, Material.GRAVEL),
        new StInfo(Material.SOUL_SOIL,   Material.SOUL_SAND));

    private static final List<Map.Entry<ItemStack, Function<Player, Inventory>>> ALL = new ArrayList<>();
    public static final int ALL_SIZE;
    static { buildAll(); ALL_SIZE = ALL.size(); }

    private static void buildAll() {
        // 锭
        for (Map.Entry<Material, List<SInfo>> e : COMBINED.entrySet()) {
            Material r = e.getKey();
            ALL.add(Map.entry(item(r, "§e" + name(r), "§7锻造台  " + e.getValue().size() + " 种原料"),
                p -> buildCombined(p, r)));
        }
        // 杂项
        for (CkInfo c : COOKING)
            ALL.add(Map.entry(item(c.r(), "§7" + name(c.r()), "§7" + c.t()), p -> buildCooking(c)));
        for (ShlInfo s : SHAPELESS)
            ALL.add(Map.entry(item(s.r(), "§7" + name(s.r()), "§7无序合成"), p -> buildShapeless(s)));
        // 方块
        for (SInfo s : SMITHING)
            ALL.add(Map.entry(item(s.r(), "§d" + name(s.r()), "§7锻造台"), p -> buildSmithing(s)));
        for (ShpInfo s : SHAPED)
            ALL.add(Map.entry(item(s.r(), "§b" + name(s.r()), "§7有序合成"), p -> buildShaped(s)));
        for (StInfo s : STONECUT)
            ALL.add(Map.entry(item(s.r(), "§7" + name(s.r()), "§7切石机"), p -> buildStonecut(s)));
    }

    // ===================== 自动循环 =====================
    private static final Map<UUID, BukkitRunnable> RUNNERS = new HashMap<>();

    public static void startCycling(Player player, Material result, Inventory inv) {
        stopCycling(player);
        BukkitRunnable task = new BukkitRunnable() {
            private int cnt = 0;
            @Override
            public void run() {
                if (!player.isOnline()) { cancel(); return; }
                List<SInfo> list = COMBINED.get(result);
                if (list == null || list.isEmpty()) { cancel(); return; }
                cnt = (cnt + 1) % list.size();
                SInfo cur = list.get(cnt);
                inv.setItem(22, mkItem(cur.b(), "§6基底: " + name(cur.b()), "§7放入基底栏"));
                inv.setItem(24, mkItem(cur.a(), "§6附加: " + name(cur.a()), "§7放入附加栏"));
                inv.setItem(40, mkItem(Material.PAPER, "§7配方 " + (cnt + 1) + " / " + list.size(),
                    "§7" + name(cur.b()) + " + " + name(cur.a())));
                setSlot(inv, 4, Material.SMITHING_TABLE,
                    "§6" + name(result) + " §7(§e" + (cnt + 1) + "§7/§e" + list.size() + "§7)",
                    "§7任意一种锭加对应染料");
            }
        };
        task.runTaskTimer(RSIslandManager.getPlugin(), 20L, 20L);
        RUNNERS.put(player.getUniqueId(), task);
    }

    public static void stopCycling(Player player) {
        BukkitRunnable t = RUNNERS.remove(player.getUniqueId());
        if (t != null) { try { t.cancel(); } catch (Exception ignored) {} }
    }

    public static void clearState(Player player) {
        stopCycling(player);
    }

    // ===================== 工具 =====================
    public static String name(Material mat) {
        if (mat == null) return "§7?";
        return switch (mat) {
            case AIR -> "§7空"; case NETHER_BRICK -> "§4下界砖"; case WHITE_DYE -> "§f白色染料";
            case GOLD_INGOT -> "§6金锭"; case NETHERITE_INGOT -> "§8下界合金锭"; case COPPER_INGOT -> "§6铜锭";
            case IRON_INGOT -> "§f铁锭"; case YELLOW_DYE -> "§e黄色染料"; case BLACK_DYE -> "§8黑色染料";
            case ORANGE_DYE -> "§6橙色染料"; case STONE -> "§7石头"; case SHULKER_BOX -> "§d潜影盒";
            case GRASS_BLOCK -> "§a草方块"; case BROWN_MUSHROOM -> "§6棕色蘑菇"; case COBBLESTONE -> "§8圆石";
            case DIRT -> "§7泥土"; case GOLDEN_APPLE -> "§6金苹果"; case BONE_BLOCK -> "§f骨块";
            case SPRUCE_SAPLING -> "§6云杉树苗"; case BONE_MEAL -> "§f骨粉"; case ENDER_PEARL -> "§d末影珍珠";
            case CHARCOAL -> "§8木炭"; case COAL -> "§8煤炭"; case BLAZE_POWDER -> "§6烈焰粉";
            case BLAZE_ROD -> "§6烈焰棒"; case PRISMARINE_SHARD -> "§b海晶碎片"; case PURPLE_DYE -> "§d紫色染料";
            case AMETHYST_SHARD -> "§d紫水晶碎片"; case GRAVEL -> "§7砂砾"; case WOODEN_SHOVEL -> "§6木锹";
            case FLINT -> "§7燧石"; case REDSTONE -> "§c红石"; case GLASS -> "§f玻璃"; case QUARTZ -> "§f石英";
            case COARSE_DIRT -> "§7砂土"; case SOUL_SOIL -> "§8灵魂土"; case SOUL_SAND -> "§6灵魂沙";
            case NETHERRACK -> "§4下界岩"; case SAND -> "§e沙子"; case DIAMOND -> "§b钻石";
            case IRON_NUGGET -> "§f铁粒"; case PURPUR_BLOCK -> "§d紫珀块"; case MYCELIUM -> "§7菌丝土"; case POISONOUS_POTATO -> "§a毒马铃薯"; case SHORT_GRASS -> "§a草";
            case COBBLED_DEEPSLATE -> "§8深板岩圆石"; case END_STONE -> "§e末地石"; case PODZOL -> "§6灰化土";
            case NETHERITE_UPGRADE_SMITHING_TEMPLATE -> "§d下界合金升级模板";
            case NETHER_WART -> "§4下界疣"; case CRIMSON_FUNGUS -> "§c绯红菌"; case WARPED_FUNGUS -> "§3诡异菌";
            case CRIMSON_NYLIUM -> "§c绯红菌岩"; case WARPED_NYLIUM -> "§3诡异菌岩";
            case RED_MUSHROOM -> "§c红色蘑菇"; case MANGROVE_PROPAGULE -> "§2红树胎生苗";
            case GILDED_BLACKSTONE -> "§6镶金黑石"; case BLACKSTONE -> "§8黑石";
            case SKELETON_SKULL -> "§f骷髅头颅"; case WITHER_SKELETON_SKULL -> "§8凋零骷髅头颅";
            default -> "§f" + mat.getKey().getKey();
        };
    }

    private static ItemStack item(Material mat, String display, String lore) {
        ItemStack s = new ItemStack(mat);
        ItemMeta m = s.getItemMeta();
        if (m == null) return s;
        m.displayName(Component.text(display));
        m.lore(List.of(Component.text(lore), Component.text("§7点击查看详情")));
        s.setItemMeta(m); return s;
    }

    private static ItemStack pane() {
        ItemStack p = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta m = p.getItemMeta();
        if (m != null) { m.displayName(Component.text("§7")); p.setItemMeta(m); } return p;
    }

    private static void fill(Inventory inv) {
        ItemStack p = pane();
        for (int i = 0; i < 54; i++) {
            int r = i / 9, c = i % 9;
            if (r == 0 || r == 5 || c == 0 || c == 8) inv.setItem(i, p);
        }
    }

    static void setSlot(Inventory inv, int s, Material mat, String display, String lore) {
        ItemStack i = new ItemStack(mat);
        ItemMeta m = i.getItemMeta();
        if (m == null) return;
        m.displayName(Component.text(display));
        if (!lore.isEmpty()) m.lore(List.of(Component.text(lore)));
        i.setItemMeta(m); inv.setItem(s, i);
    }

    static ItemStack mkItem(Material mat, String display, String lore) {
        if (mat == Material.AIR) {
            ItemStack p = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta m = p.getItemMeta();
            if (m != null) { m.displayName(Component.text("§8空")); if (!lore.isEmpty()) m.lore(List.of(Component.text(lore))); p.setItemMeta(m); }
            return p;
        }
        ItemStack i = new ItemStack(mat);
        ItemMeta m = i.getItemMeta();
        if (m == null) return i;
        m.displayName(Component.text(display));
        if (!lore.isEmpty()) m.lore(List.of(Component.text(lore)));
        i.setItemMeta(m); return i;
    }

    // ===================== 主菜单（分页） =====================
    public static Inventory buildMain(int page) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text(TITLE));
        fill3(inv);
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, ALL.size());
        for (int i = start; i < end; i++) {
            int local = i - start;
            int r = local / 7, c = local % 7;
            inv.setItem(9 + r * 9 + 1 + c, ALL.get(i).getKey());
        }
        int totalPages = (ALL.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        setSlot(inv, 4, Material.KNOWLEDGE_BOOK, "§6自定义合成配方",
            "§7第 " + (page + 1) + " / " + totalPages + " 页 · 共 " + ALL.size() + " 个配方");
        if (page > 0)
            setSlot(inv, 45, Material.ARROW, "§7← 上一页", "");
        if (page < totalPages - 1)
            setSlot(inv, 52, Material.ARROW, "§7下一页 →", "");
        setSlot(inv, SLOT_CLOSE, Material.BARRIER, "§c关闭", "");
        return inv;
    }

    public static Inventory getDetail(int slot, Player player, int page) {
        if (slot < 9 || slot >= 36) return null;
        int c = slot % 9;
        if (c == 0 || c == 8) return null;
        int localIdx = ((slot - 9) / 9) * 7 + (c - 1);
        int idx = page * ITEMS_PER_PAGE + localIdx;
        if (idx < 0 || idx >= ALL.size()) return null;
        return ALL.get(idx).getValue().apply(player);
    }

    private static void fill3(Inventory inv) {
        ItemStack p = pane();
        for (int i = 0; i < 54; i++) {
            int r = i / 9, c = i % 9;
            if (r == 0 || r == 4 || r == 5 || c == 0 || c == 8) inv.setItem(i, p);
        }
    }

    public static int getPage(Player player) {
        return CURRENT_PAGE.getOrDefault(player.getUniqueId(), 0);
    }

    public static void setPage(Player player, int page) {
        CURRENT_PAGE.put(player.getUniqueId(), page);
    }

    // ===================== 合并锻造（自动循环） =====================
    public static Inventory buildCombined(Player player, Material result) {
        List<SInfo> list = COMBINED.get(result);
        if (list == null || list.isEmpty()) return buildMain(getPage(player));
        SInfo cur = list.get(0);

        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§8[ §6锻造 " + name(result) + " §8]"));
        fill(inv);
        setSlot(inv, 4, Material.SMITHING_TABLE, "§6" + name(result) + " §7(§e1§7/§e" + list.size() + "§7)", "§7任意一种锭加对应染料");
        inv.setItem(20, mkItem(cur.t(), "§7模板: " + name(cur.t()), ""));
        inv.setItem(21, mkItem(Material.GREEN_STAINED_GLASS_PANE, "§a+", ""));
        inv.setItem(22, mkItem(cur.b(), "§6基底: " + name(cur.b()), "§7放入基底栏"));
        inv.setItem(23, mkItem(Material.GREEN_STAINED_GLASS_PANE, "§a+", ""));
        inv.setItem(24, mkItem(cur.a(), "§6附加: " + name(cur.a()), "§7放入附加栏"));
        for (int s : new int[]{30,31,32}) inv.setItem(s, mkItem(Material.LIME_STAINED_GLASS_PANE, "§a>", ""));
        inv.setItem(33, mkItem(cur.r(), "§e产物: " + name(cur.r()), ""));
        inv.setItem(40, mkItem(Material.PAPER, "§7配方 1 / " + list.size(), "§7" + name(cur.b()) + " + " + name(cur.a())));
        setSlot(inv, SLOT_BACK, Material.OAK_DOOR, "§c< 返回", "");
        setSlot(inv, SLOT_CLOSE, Material.BARRIER, "§c关闭", "");

        startCycling(player, result, inv);
        return inv;
    }

    // ===================== 普通详情 =====================
    public static Inventory buildSmithing(SInfo info) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§8[ §6锻造台 §8]")); fill(inv);
        setSlot(inv, 4, Material.SMITHING_TABLE, "§6锻造台合成", "§7将材料放入锻造台对应栏位");
        inv.setItem(20, mkItem(info.t(), "§7模板: " + name(info.t()), ""));
        inv.setItem(21, mkItem(Material.GREEN_STAINED_GLASS_PANE, "§a+", ""));
        inv.setItem(22, mkItem(info.b(), "§6基底: " + name(info.b()), "§7放入基底栏"));
        inv.setItem(23, mkItem(Material.GREEN_STAINED_GLASS_PANE, "§a+", ""));
        inv.setItem(24, mkItem(info.a(), "§6附加: " + name(info.a()), "§7放入附加栏"));
        for (int s : new int[]{30,31,32}) inv.setItem(s, mkItem(Material.LIME_STAINED_GLASS_PANE, "§a>", ""));
        inv.setItem(33, mkItem(info.r(), "§e产物: " + name(info.r()), ""));
        setSlot(inv, SLOT_BACK, Material.OAK_DOOR, "§c< 返回", "");
        setSlot(inv, SLOT_CLOSE, Material.BARRIER, "§c关闭", ""); return inv;
    }

    public static Inventory buildShaped(ShpInfo info) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§8[ §6有序合成 §8]")); fill(inv);
        setSlot(inv, 4, Material.CRAFTING_TABLE, "§6有序合成", "§7按固定图案在工作台摆放");
        int[][] gs = {{19,20,21},{28,29,30},{37,38,39}};
        for (int r = 0; r < 3; r++) {
            String row = (r < info.s().length) ? info.s()[r] : "   ";
            for (int c = 0; c < 3; c++) {
                char ch = (c < row.length()) ? row.charAt(c) : ' ';
                if (ch == ' ') inv.setItem(gs[r][c], mkItem(Material.AIR, "§8空", ""));
                else { Material m = info.i().get(ch); if (m != null) inv.setItem(gs[r][c], mkItem(m, "§7" + name(m), "")); }
            }
        }
        inv.setItem(32, mkItem(Material.LIME_STAINED_GLASS_PANE, "§a>", ""));
        inv.setItem(33, mkItem(info.r(), "§e产物: " + name(info.r()), ""));
        setSlot(inv, SLOT_BACK, Material.OAK_DOOR, "§c< 返回", "");
        setSlot(inv, SLOT_CLOSE, Material.BARRIER, "§c关闭", ""); return inv;
    }

    public static Inventory buildShapeless(ShlInfo info) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§8[ §6无序合成 §8]")); fill(inv);
        setSlot(inv, 4, Material.CRAFTING_TABLE, "§6无序合成", "§7材料随意摆放在工作台即可");
        int[] sl = {19,20,21,28,29,30};
        for (int i = 0; i < info.i().size() && i < sl.length; i++) inv.setItem(sl[i], mkItem(info.i().get(i), "§7" + name(info.i().get(i)), ""));
        inv.setItem(32, mkItem(Material.LIME_STAINED_GLASS_PANE, "§a>", ""));
        inv.setItem(33, mkItem(info.r(), "§e产物: " + name(info.r()), ""));
        setSlot(inv, SLOT_BACK, Material.OAK_DOOR, "§c< 返回", "");
        setSlot(inv, SLOT_CLOSE, Material.BARRIER, "§c关闭", ""); return inv;
    }

    public static Inventory buildCooking(CkInfo info) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§8[ §6" + info.t() + " §8]")); fill(inv);
        Material icon = switch (info.t()) { case "营火" -> Material.CAMPFIRE; case "高炉" -> Material.BLAST_FURNACE; case "烟熏炉" -> Material.SMOKER; default -> Material.FURNACE; };
        setSlot(inv, 4, icon, "§6" + info.t() + "烧炼", "§7将材料放入" + info.t());
        inv.setItem(20, mkItem(info.i(), "§7输入: " + name(info.i()), "§7放入" + info.t() + "上格"));
        inv.setItem(22, mkItem(Material.LIME_STAINED_GLASS_PANE, "§a烧炼", ""));
        inv.setItem(24, mkItem(info.r(), "§e输出: " + name(info.r()), ""));
        if (!"营火".equals(info.t())) {
            inv.setItem(38, mkItem(Material.COAL, "§6燃料", "§7使用任意燃料"));
        }
        setSlot(inv, SLOT_BACK, Material.OAK_DOOR, "§c< 返回", "");
        setSlot(inv, SLOT_CLOSE, Material.BARRIER, "§c关闭", ""); return inv;
    }

    public static Inventory buildStonecut(StInfo info) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§8[ §6切石机 §8]")); fill(inv);
        setSlot(inv, 4, Material.STONECUTTER, "§6切石机加工", "§7将材料放入切石机");
        inv.setItem(20, mkItem(info.i(), "§7输入: " + name(info.i()), "§7放入切石机"));
        inv.setItem(22, mkItem(Material.LIME_STAINED_GLASS_PANE, "§a>", ""));
        inv.setItem(24, mkItem(info.r(), "§e输出: " + name(info.r()), ""));
        setSlot(inv, SLOT_BACK, Material.OAK_DOOR, "§c< 返回", "");
        setSlot(inv, SLOT_CLOSE, Material.BARRIER, "§c关闭", ""); return inv;
    }

    // ===================== 入口 =====================
    public static void open(Player player) {
        CURRENT_PAGE.put(player.getUniqueId(), 0);
        player.openInventory(buildMain(0));
    }
}
