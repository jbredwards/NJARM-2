package git.jbredwards.njarm.mod.common.block.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Offers a state sensitive version of {@link Block#addDestroyEffects(World, BlockPos, ParticleManager)}
 * @author jbred
 *
 */
public interface IHasDestroyEffects
{
    @SideOnly(Side.CLIENT)
    boolean addDestroyEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager, @Nonnull IBlockState state);

    @SideOnly(Side.CLIENT)
    static boolean addDestroyEffects(@Nonnull Block block, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager, @Nonnull IBlockState state) {
        return block instanceof IHasDestroyEffects ? ((IHasDestroyEffects)block)
                .addDestroyEffects(world, pos, manager, state)
                : block.addDestroyEffects(world, pos, manager);
    }
}
