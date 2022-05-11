package git.jbredwards.njarm.asm.plugins;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;

/**
 * class exists cause SpongeForge
 * @author jbred
 *
 */
@SuppressWarnings("unused")
public final class ASMHooks
{
    //PluginBlockCauldron
    public static boolean canCauldronRenderInLayer(@Nonnull Block block, @Nonnull BlockRenderLayer layer) {
        //only apply fix to vanilla cauldrons, as not to potentially ruin any modded ones
        return block == Blocks.CAULDRON && layer == BlockRenderLayer.TRANSLUCENT || block.getRenderLayer() == layer;
    }
}
