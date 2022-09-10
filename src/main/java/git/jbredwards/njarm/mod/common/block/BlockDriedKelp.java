package git.jbredwards.njarm.mod.common.block;

import net.darkhax.bookshelf.data.Blockstates;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class BlockDriedKelp extends Block
{
    public BlockDriedKelp(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockDriedKelp(@Nonnull Material materialIn, @Nonnull MapColor color) {
        super(materialIn, color);
        setDefaultState(getDefaultState().withProperty(Blockstates.FACING, EnumFacing.UP));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, Blockstates.FACING); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) { return state.getValue(Blockstates.FACING).getIndex(); }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(Blockstates.FACING, EnumFacing.byIndex(meta));
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer) {
        return getDefaultState().withProperty(Blockstates.FACING, facing);
    }
}
