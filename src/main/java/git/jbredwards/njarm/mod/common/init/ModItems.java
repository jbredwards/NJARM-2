package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.block.BlockFoodCrate;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.item.*;
import git.jbredwards.njarm.mod.common.item.block.*;
import git.jbredwards.njarm.mod.common.item.equipment.ItemAxe;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Holds this mod's items
 * @author jbred
 *
 */
public final class ModItems
{
    @Nonnull public static final NonNullList<Item> INIT = NonNullList.create();

    //item blocks
    @Nonnull public static final ItemBlock RUBY_BLOCK = register("ruby_block", new ItemBlock(ModBlocks.RUBY_BLOCK));
    @Nonnull public static final ItemBlock SAPPHIRE_BLOCK = register("sapphire_block", new ItemBlock(ModBlocks.SAPPHIRE_BLOCK));
    @Nonnull public static final ItemBlockMeta FOOD_CRATE = register("food_crate", new ItemBlockMeta(ModBlocks.FOOD_CRATE, BlockFoodCrate.TYPE));
    @Nonnull public static final ItemBlock PLATINUM_BLOCK = register("platinum_block", new ItemBlock(ModBlocks.PLATINUM_BLOCK));
    @Nonnull public static final ItemBlock NETHERITE_BLOCK = register("netherite_block", new ItemBlock(ModBlocks.NETHERITE_BLOCK));
    @Nonnull public static final ItemBlock RUBY_ORE = register("ruby_ore", new ItemBlock(ModBlocks.RUBY_ORE));
    @Nonnull public static final ItemBlock SAPPHIRE_ORE = register("sapphire_ore", new ItemBlock(ModBlocks.SAPPHIRE_ORE));
    @Nonnull public static final ItemBlock ANCIENT_DEBRIS = register("ancient_debris", new ItemBlock(ModBlocks.ANCIENT_DEBRIS));
    @Nonnull public static final ItemBlock MAGIC_BLOCK = register("magic_block", new ItemBlock(ModBlocks.MAGIC_BLOCK));
    @Nonnull public static final ItemBlock MAGIC_ORE = register("magic_ore", new ItemBlock(ModBlocks.MAGIC_ORE));
    @Nonnull public static final ItemBlockExperienceOre XP_ORE = register("xp_ore", new ItemBlockExperienceOre(ModBlocks.XP_ORE));
    @Nonnull public static final ItemBlock FRAGILE_ICE = register("fragile_ice", new ItemBlock(ModBlocks.FRAGILE_ICE));
    @Nonnull public static final ItemBlock NETHER_REACTOR_CORE = register("nether_reactor_core", new ItemBlock(ModBlocks.NETHER_REACTOR_CORE));
    @Nonnull public static final ItemBlock GLOWING_OBSIDIAN = register("glowing_obsidian", new ItemBlock(ModBlocks.GLOWING_OBSIDIAN));
    @Nonnull public static final ItemBlock CHAIN = register("chain", new ItemBlock(ModBlocks.CHAIN));
    @Nonnull public static final ItemBlock SOUL_SOIL = register("soul_soil", new ItemBlock(ModBlocks.SOUL_SOIL));
    @Nonnull public static final ItemBlock BASALT = register("basalt", new ItemBlock(ModBlocks.BASALT));
    @Nonnull public static final ItemBlock POLISHED_BASALT = register("polished_basalt", new ItemBlock(ModBlocks.POLISHED_BASALT));
    @Nonnull public static final ItemBlock BLACKSTONE = register("blackstone", new ItemBlock(ModBlocks.BLACKSTONE));
    @Nonnull public static final ItemBlock POLISHED_BLACKSTONE = register("polished_blackstone", new ItemBlock(ModBlocks.POLISHED_BLACKSTONE));
    @Nonnull public static final ItemBlock BLACKSTONE_BRICKS = register("blackstone_bricks", new ItemBlock(ModBlocks.BLACKSTONE_BRICKS));
    @Nonnull public static final ItemBlock BLACKSTONE_CHISELED = register("blackstone_chiseled", new ItemBlock(ModBlocks.BLACKSTONE_CHISELED));
    @Nonnull public static final ItemBlock BLACKSTONE_CRACKED = register("blackstone_cracked", new ItemBlock(ModBlocks.BLACKSTONE_CRACKED));
    @Nonnull public static final ItemBlock ICE_MUSHROOM = register("ice_mushroom", new ItemBlock(ModBlocks.ICE_MUSHROOM));
    @Nonnull public static final ItemBlock MOSS = register("moss", new ItemBlock(ModBlocks.MOSS));
    @Nonnull public static final ItemBlock OBSIDIAN_BLOCK = register("obsidian_block", new ItemBlock(ModBlocks.OBSIDIAN_BLOCK));
    @Nonnull public static final ItemBlock OBSIDIAN_GLASS = register("obsidian_glass", new ItemBlock(ModBlocks.OBSIDIAN_GLASS));
    @Nonnull public static final ItemBlock MICA_ORE = register("mica_ore", new ItemBlock(ModBlocks.MICA_ORE));

    //items
    @Nonnull public static final Item RUBY = register("ruby", new Item());
    @Nonnull public static final Item SAPPHIRE = register("sapphire", new Item());
    @Nonnull public static final ItemEggShell EGG_SHELL = register("egg_shell", new ItemEggShell());
    @Nonnull public static final ItemGlint MAGIC_DUST = register("magic_dust", new ItemGlint());
    @Nonnull public static final Item OBSIDIAN_INGOT = register("obsidian_ingot", new Item());
    @Nonnull public static final Item CRUMBLING_BEDROCK = register("crumbling_bedrock", new Item());
    @Nonnull public static final Item SUNSTONE = register("sunstone", new Item());
    @Nonnull public static final ItemFood BAKED_APPLE = register("baked_apple", new ItemFood(8, 4.8f, false));
    @Nonnull public static final ItemFood CARAMEL_APPLE = register("caramel_apple", new ItemFood(4, 4.4f, false).setPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 1), 1));
    @Nonnull public static final ItemFood BAGUETTE = register("baguette", new ItemFood(10, 12, false));
    @Nonnull public static final ItemFood SUGAR_BAGUETTE = register("sugar_baguette", new ItemFood(10, 14, false).setPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 1), 1));
    @Nonnull public static final ItemChocolateMilk CHOCOLATE_MILK_BUCKET = register("chocolate_milk_bucket", new ItemChocolateMilk(), item -> item.setContainerItem(Items.BUCKET));
    @Nonnull public static final ItemMilkStackable MILK_BOTTLE = register("milk_bottle", new ItemMilkStackable(), item -> item.setContainerItem(Items.GLASS_BOTTLE).setMaxStackSize(64));
    @Nonnull public static final ItemChocolateMilk CHOCOLATE_MILK_BOTTLE = register("chocolate_milk_bottle", new ItemChocolateMilk(), item -> item.setContainerItem(Items.GLASS_BOTTLE).setMaxStackSize(64));
    @Nonnull public static final ItemPotionFood CANNED_SPINACH = register("canned_spinach", new ItemPotionFood(20, false), item -> item.setEffects(ItemPotionFood.SPINACH));
    @Nonnull public static final ItemMagicMirror MAGIC_MIRROR = register("magic_mirror", new ItemMagicMirror(ItemMagicMirror.NORMAL));
    @Nonnull public static final ItemMagicMirror BEDROCK_MAGIC_MIRROR = register("bedrock_magic_mirror", new ItemMagicMirror(ItemMagicMirror.BEDROCK), item -> item.setMaxStackSize(1));
    @Nonnull public static final ItemMagicMirror DIMENSIONAL_MAGIC_MIRROR = register("dimensional_magic_mirror", new ItemMagicMirror(ItemMagicMirror.DIMENSIONAL));
    @Nonnull public static final ItemMagicMirror BEDROCK_DIMENSIONAL_MAGIC_MIRROR = register("bedrock_dimensional_magic_mirror", new ItemMagicMirror((byte)3), item -> item.setMaxStackSize(1));

    //armor

    //tools
    @Nonnull public static final ItemSword RUBY_SWORD = register("ruby_sword", new ItemSword(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemSpade RUBY_SHOVEL = register("ruby_shovel", new ItemSpade(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemPickaxe RUBY_PICKAXE = register("ruby_pickaxe", new ItemPickaxe(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemAxe RUBY_AXE = register("ruby_axe", new ItemAxe(EquipmentConfig.RUBY_TOOL_MATERIAL));
    @Nonnull public static final ItemHoe RUBY_HOE = register("ruby_hoe", new ItemHoe(EquipmentConfig.RUBY_TOOL_MATERIAL));

    //register ores
    public static void registerOres() {
        OreDictionary.registerOre("blockMagicAlloy", MAGIC_BLOCK);
        OreDictionary.registerOre("blockNetherite", NETHERITE_BLOCK);
        OreDictionary.registerOre("blockObsidianAlloy", OBSIDIAN_BLOCK);
        OreDictionary.registerOre("blockPlatinum", PLATINUM_BLOCK);
        OreDictionary.registerOre("blockRuby", RUBY_BLOCK);
        OreDictionary.registerOre("blockSapphire", SAPPHIRE_BLOCK);
        OreDictionary.registerOre("cobblestone", BLACKSTONE);
        OreDictionary.registerOre("dustMagicNJARM", MAGIC_DUST);
        OreDictionary.registerOre("gemRuby", RUBY);
        OreDictionary.registerOre("gemSapphire", SAPPHIRE);
        OreDictionary.registerOre("gemSunstone", SUNSTONE);
        OreDictionary.registerOre("glass", OBSIDIAN_GLASS);
        OreDictionary.registerOre("ingotObsidianAlloy", OBSIDIAN_INGOT);
        OreDictionary.registerOre("oreAncientDebris", ANCIENT_DEBRIS);
        OreDictionary.registerOre("oreMagicNJARM", MAGIC_ORE);
        OreDictionary.registerOre("oreMica", MICA_ORE);
        OreDictionary.registerOre("oreRuby", RUBY_ORE);
        OreDictionary.registerOre("oreSapphire", SAPPHIRE_ORE);
    }

    //register item
    @Nonnull
    static <T extends Item> T register(@Nonnull String name, @Nonnull T item) {
        INIT.add(item.setRegistryName(Constants.MODID, name)
                .setTranslationKey(Constants.MODID + "." + name)
                .setCreativeTab(CreativeTab.INSTANCE)
        );

        return item;
    }

    //register item
    @Nonnull
    static <T extends Item> T register(@Nonnull String name, @Nonnull T item, @Nonnull Consumer<T> consumer) {
        consumer.accept(item);
        return register(name, item);
    }
}
