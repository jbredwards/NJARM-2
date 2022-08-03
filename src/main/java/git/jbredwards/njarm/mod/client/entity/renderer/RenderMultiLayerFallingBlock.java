package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.fluidlogged_api.mod.asm.plugins.ASMHooks;
import git.jbredwards.njarm.mod.common.block.util.IEmissiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * allows falling blocks to use multi-layer models
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class RenderMultiLayerFallingBlock extends RenderFallingBlock
{
    protected boolean renderName = true;
    public RenderMultiLayerFallingBlock(@Nonnull RenderManager renderManagerIn) { super(renderManagerIn); }

    @Override
    public void doRender(@Nonnull EntityFallingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if(entity.getBlock() != null) {
            final IBlockState state = entity.getBlock();
            final Block block = state.getBlock();
            renderName = false;

            for(BlockRenderLayer layer : BlockRenderLayer.values()) {
                if(ASMHooks.canRenderBlockInLayer(block, state, layer)) {
                    ForgeHooksClient.setRenderLayer(layer);
                    final boolean emissive = block instanceof IEmissiveBlock
                            && ((IEmissiveBlock)block).isEmissive(state, layer);

                    final float lastX = OpenGlHelper.lastBrightnessX;
                    final float lastY = OpenGlHelper.lastBrightnessY;

                    if(emissive) OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
                    super.doRender(entity, x, y, z, entityYaw, partialTicks);
                    if(emissive) OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
                }
            }


            renderName = true;
            if(!renderOutlines) renderName(entity, x, y, z);
            ForgeHooksClient.setRenderLayer(null);
        }
    }

    @Override
    protected void renderName(@Nonnull EntityFallingBlock entity, double x, double y, double z) {
        if(renderName) super.renderName(entity, x, y, z);
    }
}
