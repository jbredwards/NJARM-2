package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.asm.plugins.ASMHooks;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.block.*;
import git.jbredwards.njarm.mod.common.item.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

import java.util.function.Consumer;

/**
 * Holds this mod's blocks
 * @author jbred
 *
 */
public final class ModBlocks
{
    @Nonnull public static final NonNullList<Block> INIT = NonNullList.create();

    //block materials
    @Nonnull public static final MaterialLiquid BUBBLE_COLUMN_MATERIAL = new MaterialLiquid(MapColor.WATER);

    //blocks
    @Nonnull public static final Block RUBY_BLOCK = register("ruby_block", new Block(Material.IRON, MapColor.RED), Blocks.DIAMOND_BLOCK, block -> block.setHarvestLevel("pickaxe", 3));
    @Nonnull public static final Block SAPPHIRE_BLOCK = register("sapphire_block", new Block(Material.IRON, MapColor.BLUE), Blocks.DIAMOND_BLOCK, block -> block.setHarvestLevel("pickaxe", 4));
    @Nonnull public static final BlockFoodCrate FOOD_CRATE = register("food_crate", new BlockFoodCrate(Material.WOOD), Blocks.LOG);
    @Nonnull public static final Block PLATINUM_BLOCK = register("platinum_block", new Block(Material.IRON, MapColor.SNOW), Blocks.DIAMOND_BLOCK);
    @Nonnull public static final Block NETHERITE_BLOCK = register("netherite_block", new Block(Material.IRON, MapColor.GRAY), Blocks.OBSIDIAN, block -> block.setSoundType(ModSounds.NETHERITE));
    @Nonnull public static final BlockOre RUBY_ORE = register("ruby_ore", new BlockOre(Material.ROCK).setItemDropped(() -> ModItems.RUBY).setExpDropped(rand -> MathHelper.getInt(rand, 3, 7)), Blocks.DIAMOND_ORE, block -> block.setHarvestLevel("pickaxe", 3));
    @Nonnull public static final BlockOre SAPPHIRE_ORE = register("sapphire_ore", new BlockOre(Material.ROCK, MapColor.NETHERRACK).setItemDropped(() -> ModItems.SAPPHIRE).setExpDropped(rand -> MathHelper.getInt(rand, 3, 7)), Blocks.DIAMOND_ORE, block -> block.setSoundType(ModSounds.NETHER_ORE));
    @Nonnull public static final BlockRotatedPillar ANCIENT_DEBRIS = register("ancient_debris", new BlockRotatedPillar(Material.ROCK, MapColor.NETHERRACK), Blocks.OBSIDIAN, block -> block.setSoundType(ModSounds.ANCIENT_DEBRIS));
    @Nonnull public static final Block MAGIC_BLOCK = register("magic_block", new Block(Material.IRON, MapColor.PINK), Blocks.DIAMOND_BLOCK);
    @Nonnull public static final BlockMagicOre MAGIC_ORE = register("magic_ore", new BlockMagicOre(Material.ROCK, MapColor.SAND, false).setItemDropped(() -> ModItems.MAGIC_DUST).setQuantityDropped(rand -> MathHelper.getInt(rand, 1, 3)).setDoesFortuneAdd().setExpDropped(rand -> MathHelper.getInt(rand, 1, 5)), Blocks.END_STONE);
    @Nonnull public static final BlockMagicOre LIT_MAGIC_ORE = register("lit_magic_ore", new BlockMagicOre(Material.ROCK, MapColor.SAND, true).setItemDropped(() -> ModItems.MAGIC_DUST).setQuantityDropped(rand -> MathHelper.getInt(rand, 1, 3)).setDoesFortuneAdd().setExpDropped(rand -> MathHelper.getInt(rand, 1, 5)), Blocks.END_STONE);
    @Nonnull public static final BlockExperienceOre XP_ORE = register("xp_ore", new BlockExperienceOre(Material.ROCK).setItemDropped(() -> Items.AIR).setQuantityDropped(rand -> 0).setExpDropped(rand -> MathHelper.getInt(rand, 5, 14)), Blocks.COAL_ORE);
    @Nonnull public static final BlockFragileIce FRAGILE_ICE = register("fragile_ice", new BlockFragileIce(), Blocks.ICE, block -> block.setHardness(0).setResistance(0));
    @Nonnull public static final BlockUndyingTotem TOTEM_OF_UNDYING = register("totem_of_undying", ASMHooks.TOTEM_BLOCK.get(), Blocks.GOLD_BLOCK, block -> block.setHardness(0).setResistance(0)); //moved to ASMHooks for vanilla class loading reasons...
    @Nonnull public static final BlockNetherCore NETHER_REACTOR_CORE = register("nether_reactor_core", new BlockNetherCore(Material.IRON, MapColor.DIAMOND), Blocks.DIAMOND_BLOCK);
    @Nonnull public static final BlockGlowingObsidian GLOWING_OBSIDIAN = register("glowing_obsidian", new BlockGlowingObsidian(), Blocks.OBSIDIAN, block -> block.setLightLevel(0.8f));
    @Nonnull public static final BlockChain CHAIN = register("chain", new BlockChain(Material.IRON), Blocks.IRON_BLOCK, block -> block.setSoundType(ModSounds.CHAIN));
    @Nonnull public static final Block SOUL_SOIL = register("soul_soil", new Block(Material.SAND, MapColor.BROWN), Blocks.SOUL_SAND, block -> block.setSoundType(ModSounds.SOUL_SOIL));
    @Nonnull public static final BlockRotatedPillar BASALT = register("basalt", new BlockRotatedPillar(Material.ROCK, MapColor.GRAY), Blocks.STONE, block -> block.setSoundType(ModSounds.BASALT));
    @Nonnull public static final BlockRotatedPillar POLISHED_BASALT = register("polished_basalt", new BlockRotatedPillar(Material.ROCK, MapColor.GRAY), Blocks.STONE, block -> block.setSoundType(ModSounds.BASALT));
    @Nonnull public static final BlockRotatedPillar BLACKSTONE = register("blackstone", new BlockRotatedPillar(Material.ROCK, MapColor.BLACK), Blocks.STONE);
    @Nonnull public static final Block POLISHED_BLACKSTONE = register("polished_blackstone", new Block(Material.ROCK, MapColor.BLACK), Blocks.STONE);
    @Nonnull public static final Block BLACKSTONE_BRICKS = register("blackstone_bricks", new Block(Material.ROCK, MapColor.BLACK), Blocks.STONEBRICK);
    @Nonnull public static final Block BLACKSTONE_CHISELED = register("blackstone_chiseled", new Block(Material.ROCK, MapColor.BLACK), Blocks.STONEBRICK);
    @Nonnull public static final Block BLACKSTONE_CRACKED = register("blackstone_cracked", new Block(Material.ROCK, MapColor.BLACK), Blocks.STONEBRICK);
    @Nonnull public static final BlockIceMushroom ICE_MUSHROOM = register("ice_mushroom", new BlockIceMushroom(), block -> block.setSoundType(SoundType.GLASS));
    @Nonnull public static final BlockBlueFire BLUE_FIRE = register("blue_fire", new BlockBlueFire(), Blocks.FIRE);
    @Nonnull public static final BlockBubbleColumn BUBBLE_COLUMN = register("bubble_column", new BlockBubbleColumn(BUBBLE_COLUMN_MATERIAL), Blocks.WATER);

    //registry
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block) { return register(name, block, b -> {}); }
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block, @Nonnull Consumer<T> properties) {
        INIT.add(block.setRegistryName(Constants.MODID, name)
                .setTranslationKey(Constants.MODID + "." + name)
                .setCreativeTab(CreativeTab.INSTANCE));

        properties.accept(block);
        return block;
    }

    //copy properties from parent prior to registration
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block, @Nonnull Block parent) { return register(name, block, parent, b -> {}); }
    @SuppressWarnings("ConstantConditions")
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block, @Nonnull Block parent, @Nonnull Consumer<T> properties) {
        block.setResistance(parent.getExplosionResistance(null) * 5 / 3)
                .setHardness(parent.getDefaultState().getBlockHardness(null, null))
                .setLightLevel(parent.getDefaultState().getLightValue() / 15f)
                .setSoundType(parent.getSoundType())
                .setHarvestLevel(parent.getHarvestTool(parent.getDefaultState()), parent.getHarvestLevel(parent.getDefaultState()));

        return register(name, block, properties);
    }
}
