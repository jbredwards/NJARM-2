package git.jbredwards.njarm.mod.common.block.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * called by {@link World#setBlockState(BlockPos, IBlockState, int)} to update update the input state
 * to what it should be, for example this is used by fire to become blue fire
 * @author jbred
 *
 */
public interface IHasWorldState
{
    @Nonnull
    IBlockState getStateForWorld(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state);
}
