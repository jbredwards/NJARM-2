package git.jbredwards.njarm.mod.common.block;

import com.google.common.collect.ImmutableList;
import net.darkhax.bookshelf.item.ICustomModel;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 *
 * @author jbred
 *
 */
public class BlockTreeLeaves extends BlockLeaves implements ICustomModel
{
    @Nonnull protected final MapColor mapColor;
    @Nonnull protected final Supplier<Item> sapling;

    public BlockTreeLeaves(@Nonnull MapColor mapColor) { this(mapColor, () -> Items.AIR); }
    public BlockTreeLeaves(@Nonnull MapColor mapColor, @Nonnull Supplier<Item> sapling) {
        this.mapColor = mapColor;
        this.sapling = sapling;
        leavesFancy = false;
    }

    //change the ability for the play to toggle leaf transparency
    //visuals are bugged in the end (vanilla issue), so end leaves have this disabled
    public void disableFancyLeaves() { leavesFancy = true; }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        int meta = 0;
        if(state.getValue(DECAYABLE)) meta |= 4;
        if(state.getValue(CHECK_DECAY)) meta |= 8;
        return meta;
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return mapColor;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) { return sapling.get(); }

    @Nullable
    @Override
    public BlockPlanks.EnumType getWoodType(int meta) { return null; }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return ImmutableList.of(new ItemStack(this));
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return !leavesFancy && Blocks.LEAVES.getDefaultState().isOpaqueCube();
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : Blocks.LEAVES.getRenderLayer();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return leavesFancy ? Blocks.LEAVES.getDefaultState().shouldSideBeRendered(world, pos, side) : super.shouldSideBeRendered(state, world, pos, side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerMeshModels() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(CHECK_DECAY, DECAYABLE).build());
    }
}
