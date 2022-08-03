package git.jbredwards.njarm.mod.common.block.util;

import git.jbredwards.njarm.mod.Main;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Assists in making forge:multi-layer blocks have emissive layers.
 * <p>Note that certain block particle & light related methods need to still be overriden</p>
 * @author jbred
 *
 */
public interface IEmissiveBlock extends IHasDestroyEffects
{
    @SideOnly(Side.CLIENT)
    boolean isEmissive(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer);

    /**
     * <p><b>For in-world state emissive properties you must:</b></p>
     * <p>======================================</p>
     * Override {@link net.minecraft.block.Block#getLightValue(IBlockState) Block.getLightValue(IBlockState)} with
     * <p>return isLightEmissive(state) ? 1 : 0;</p>
     * <p>======================================</p>
     * Override {@link net.minecraft.block.Block#getPackedLightmapCoords(IBlockState, net.minecraft.world.IBlockAccess,
     * net.minecraft.util.math.BlockPos) Block.getPackedLightmapCoords(IBlockState, IBlockAccess, BlockPos)} with
     * <p>return isLightEmissive(state) ? 240 : super.getPackedLightmapCoords(state, source, pos);</p>
     */
    default boolean isLightEmissive(@Nonnull IBlockState state) { return Main.proxy.isLightEmissive(state, this); }
}
