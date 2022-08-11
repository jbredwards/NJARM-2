package git.jbredwards.njarm.mod.common.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class BlockTreeLog extends BlockLog
{
    @Nonnull
    protected final MapColor mapColor;
    public BlockTreeLog(@Nonnull MapColor mapColor) { this.mapColor = mapColor; }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, LOG_AXIS); }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        switch(state.getValue(LOG_AXIS)) {
            case X:  return 4;
            case Y:  return 0;
            case Z:  return 8;
            default: return 12;
        }
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch(meta & 12) {
            case 4:  return getDefaultState().withProperty(LOG_AXIS, EnumAxis.X);
            case 0:  return getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y);
            case 8:  return getDefaultState().withProperty(LOG_AXIS, EnumAxis.Z);
            default: return getDefaultState().withProperty(LOG_AXIS, EnumAxis.NONE);
        }
    }

    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return mapColor;
    }
}
