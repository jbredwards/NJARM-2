package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.block.*;
import git.jbredwards.njarm.mod.common.item.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.NonNullList;

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
    @Nonnull public static final BlockBlueFire BLUE_FIRE = register("blue_fire", new BlockBlueFire(), Blocks.FIRE);
    @Nonnull public static final BlockBubbleColumn BUBBLE_COLUMN = register("bubble_column", new BlockBubbleColumn(BUBBLE_COLUMN_MATERIAL), Blocks.WATER, block -> block.setLightOpacity(0));

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
                .setLightOpacity(parent.getDefaultState().getLightOpacity())
                .setSoundType(parent.getSoundType())
                .setHarvestLevel(parent.getHarvestTool(parent.getDefaultState()), parent.getHarvestLevel(parent.getDefaultState()));

        return register(name, block, properties);
    }
}
