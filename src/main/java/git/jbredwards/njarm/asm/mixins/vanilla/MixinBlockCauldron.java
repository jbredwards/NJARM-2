package git.jbredwards.njarm.asm.mixins.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;

/**
 * Allows cauldrons to have transparent water
 * @author jbred
 *
 */
@Mixin(BlockCauldron.class)
public abstract class MixinBlockCauldron extends Block
{
    public MixinBlockCauldron(@Nonnull Material materialIn) { super(materialIn); }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        //only apply to vanilla, as not to potentially ruin any modded ones
        if(state.getBlock() == Blocks.CAULDRON) return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
        else return super.canRenderInLayer(state, layer);
    }
}
