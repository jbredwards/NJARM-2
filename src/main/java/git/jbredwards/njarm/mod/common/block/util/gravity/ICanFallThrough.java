package git.jbredwards.njarm.mod.common.block.util.gravity;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

/**
 * Serves as a workaround for the hardcoded {@link net.minecraft.block.BlockFalling#canFallThrough(net.minecraft.block.state.IBlockState) BlockFalling.canFallThrough()},
 * blocks that implement this will cause the previously mentioned method to return true
 * @author jbred
 *
 */
public interface ICanFallThrough
{
    default boolean canFallThrough(@Nonnull IBlockState state) { return true; }
}
