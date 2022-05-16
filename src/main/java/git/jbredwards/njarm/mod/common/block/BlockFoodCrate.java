package git.jbredwards.njarm.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Supplier;

/**
 *
 * @author jbred
 *
 */
public class BlockFoodCrate extends Block
{
    @Nonnull
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockFoodCrate(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockFoodCrate(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        setDefaultState(getDefaultState().withProperty(TYPE, Type.WHEAT_SEEDS));
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return state.getValue(TYPE).getItem();
    }

    @Override
    public int quantityDropped(@Nonnull Random random) { return 9; }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for(int meta = 0; meta < Type.values().length; meta++) { items.add(new ItemStack(this, 1, meta)); }
    }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT || super.canRenderInLayer(state, layer);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, TYPE); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) { return state.getValue(TYPE).ordinal(); }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, Type.values()[meta % Type.values().length]);
    }

    public enum Type implements IStringSerializable
    {
        @Nonnull WHEAT_SEEDS("wheat_seeds", 0, () -> Items.WHEAT_SEEDS),
        @Nonnull CARROT("carrot", 0, () -> Items.CARROT),
        @Nonnull POTATO("potato", 0, () -> Items.POTATO),
        @Nonnull BEETROOT("beetroot", 0, () -> Items.BEETROOT),
        @Nonnull BEETROOT_SEEDS("beetroot_seeds", 0, () -> Items.BEETROOT_SEEDS),
        @Nonnull APPLE("apple", 0, () -> Items.APPLE),
        @Nonnull FISH("fish", 0, () -> Items.FISH),
        @Nonnull SALMON("salmon", 1, () -> Items.FISH),
        @Nonnull NEMO("nemo", 2, () -> Items.FISH),
        @Nonnull PUFFERFISH("pufferfish", 3, () -> Items.FISH);
        @Nonnull private final String name;
        @Nonnull private final Supplier<Item> item;
        public final int itemMeta;

        Type(@Nonnull String name, int itemMeta, @Nonnull Supplier<Item> item) {
            this.name = name;
            this.item = item;
            this.itemMeta = itemMeta;
        }

        @Nonnull
        @Override
        public String getName() { return name; }

        @Nonnull
        public Item getItem() { return item.get(); }
    }
}
