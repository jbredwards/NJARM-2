package git.jbredwards.njarm.mod.common.block.util;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public interface ILayeredFallingBlock
{
    boolean fallOnto(@Nonnull EntityFallingBlock fallingBlock, @Nonnull World world, @Nonnull BlockPos pos,
            @Nonnull IBlockState fallingState, @Nonnull IBlockState replacedState, @Nonnull IBlockState hitState);

    @SubscribeEvent
    static void canFallOnto(@Nonnull BlockEvent.EntityPlaceEvent event) {
        if(event.getEntity() instanceof EntityFallingBlock) {
            final @Nullable IBlockState fallingState = ((EntityFallingBlock)event.getEntity()).getBlock();
            if(fallingState != null && fallingState.getBlock() instanceof ILayeredFallingBlock) {
                final boolean fallOnto = ((ILayeredFallingBlock)fallingState.getBlock()).fallOnto(
                        (EntityFallingBlock)event.getEntity(),
                        event.getWorld(),
                        event.getPos(),
                        fallingState,
                        event.getWorld().getBlockState(event.getPos()),
                        event.getPlacedAgainst()
                );

                if(fallOnto) event.setCanceled(true);
            }
        }
    }
}
