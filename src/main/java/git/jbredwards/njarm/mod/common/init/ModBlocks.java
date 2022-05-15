package git.jbredwards.njarm.mod.common.init;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.item.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.function.Consumer;

import static net.minecraft.init.Blocks.*;

/**
 * Holds this mod's blocks
 * @author jbred
 *
 */
public final class ModBlocks
{
    @Nonnull public static final NonNullList<Block> INIT = NonNullList.create();

    //blocks
    @Nonnull public static final Block RUBY_BLOCK = register("ruby_block", new Block(Material.IRON, MapColor.RED),
            DIAMOND_BLOCK, block -> block.setHarvestLevel("pickaxe", 3));
    @Nonnull public static final Block SAPPHIRE_BLOCK = register("sapphire_block", new Block(Material.IRON, MapColor.BLUE),
            DIAMOND_BLOCK, block -> block.setHarvestLevel("pickaxe", 4));

    //registry
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block) { return register(name, block, (Consumer<T>)null); }
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block, @Nullable Consumer<T> properties) {
        INIT.add(block.setRegistryName(Constants.MODID, name)
                .setTranslationKey(Constants.MODID + "." + name)
                .setCreativeTab(CreativeTab.INSTANCE));

        if(properties != null) properties.accept(block);
        return block;
    }

    //copy properties from parent prior to registration
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block, @Nonnull Block parent) { return register(name, block, parent, null); }
    @SuppressWarnings("ConstantConditions")
    @Nonnull private static <T extends Block> T register(@Nonnull String name, @Nonnull T block, @Nonnull Block parent, @Nullable Consumer<T> properties) {
        block.setResistance(parent.getExplosionResistance(null) * 5 / 3)
                .setHardness(parent.getDefaultState().getBlockHardness(null, null))
                .setLightLevel(parent.getDefaultState().getLightValue())
                .setLightOpacity(parent.getDefaultState().getLightOpacity())
                .setSoundType(parent.getSoundType())
                .setHarvestLevel(parent.getHarvestTool(parent.getDefaultState()), parent.getHarvestLevel(parent.getDefaultState()));

        return register(name, block, properties);
    }
}
