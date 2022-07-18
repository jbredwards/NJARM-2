package git.jbredwards.njarm.mod.common.block.util;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public interface IFancyFallingBlock
{
    default float getWidthForFallingBlock(@Nonnull IBlockState state) { return 0.98f; }
    default float getHeightForFallingBlock(@Nonnull IBlockState state) { return 0.98f; }
}
