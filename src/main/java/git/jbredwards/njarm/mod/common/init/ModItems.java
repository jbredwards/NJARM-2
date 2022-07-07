package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.block.BlockFoodCrate;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.item.*;
import git.jbredwards.njarm.mod.common.item.block.*;
import git.jbredwards.njarm.mod.common.item.equipment.ItemAxe;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

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

    //items
    @Nonnull public static final Item RUBY = register("ruby", new Item());
    @Nonnull public static final Item SAPPHIRE = register("sapphire", new Item());
    @Nonnull public static final ItemEggShell EGG_SHELL = register("egg_shell", new ItemEggShell());
    @Nonnull public static final ItemGlint MAGIC_DUST = register("magic_dust", new ItemGlint());
    @Nonnull public static final Item OBSIDIAN_INGOT = register("obsidian_ingot", new Item());
    @Nonnull public static final Item CRUMBLING_BEDROCK = register("crumbling_bedrock", new Item());
    @Nonnull public static final Item SUNSTONE = register("sunstone", new Item());
    @Nonnull public static final ItemFood BAKED_APPLE = register("baked_apple", new ItemFood(8, 4.8f, false));

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
        OreDictionary.registerOre("blockPlatinum", PLATINUM_BLOCK);
        OreDictionary.registerOre("blockRuby", RUBY_BLOCK);
        OreDictionary.registerOre("blockSapphire", SAPPHIRE_BLOCK);
        OreDictionary.registerOre("dustMagic", MAGIC_DUST);
        OreDictionary.registerOre("gemRuby", RUBY);
        OreDictionary.registerOre("gemSapphire", SAPPHIRE);
        OreDictionary.registerOre("gemSunstone", SUNSTONE);
        OreDictionary.registerOre("ingotObsidian", OBSIDIAN_INGOT);
        OreDictionary.registerOre("oreAncientDebris", ANCIENT_DEBRIS);
        OreDictionary.registerOre("oreMagic", MAGIC_ORE);
        OreDictionary.registerOre("oreRuby", RUBY_ORE);
        OreDictionary.registerOre("oreSapphire", SAPPHIRE_ORE);
    }

    //register item
    @Nonnull
    private static <T extends Item> T register(@Nonnull String name, @Nonnull T item) {
        INIT.add(item.setRegistryName(Constants.MODID, name)
                .setTranslationKey(Constants.MODID + "." + name)
                .setCreativeTab(CreativeTab.INSTANCE)
        );

        return item;
    }
}
