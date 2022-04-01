package git.jbredwards.njarm.asm.mixins.vanilla;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import javax.annotation.Nullable;

/**
 * Brings back the old loading progress bar
 * @author jbred
 *
 */
@Mixin(LoadingScreenRenderer.class)
public abstract class MixinLoadingScreenRenderer
{
    @ModifyArg(method = "displayLoadingString", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/LoadingScreenRenderer;setLoadingProgress(I)V"))
    private int getLoadingProgress(int oldProgress) {
        //gets the server world, since that's what's used to store the chunk data
        final @Nullable WorldServer world = DimensionManager.getWorld(0);
        //not singleplayer, so no access to server world
        if(world == null) return oldProgress;
        //if this has access, check nearby chunk loading progress
        final BlockPos spawn = world.getSpawnPoint();
        final int spawnX = spawn.getX() >> 4;
        final int spawnZ = spawn.getZ() >> 4;
        int progress = 0;
        //gets the amount of chunks generated in a 12x12 area
        for(int x = -12; x <= 12; x++) {
            for(int z = -12; z <= 12; z++) {
                if(world.isChunkGeneratedAt(spawnX + x, spawnZ + z)) progress++;
            }
        }
        //return progress (none if loaded)
        return progress < 625 ? (int)(progress / 6.25f) : oldProgress;
    }
}
