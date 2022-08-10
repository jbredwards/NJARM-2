package git.jbredwards.njarm.mod.client.entity.renderer.layers;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.RenderMudPig;
import git.jbredwards.njarm.mod.common.entity.passive.EntityMudPig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class LayerMudPig implements LayerRenderer<EntityMudPig>
{
    @Nonnull protected static final ResourceLocation MUD_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/mud_pig.png");
    @Nonnull protected final ModelPig mudModel = new ModelPig(0.1f);
    @Nonnull protected final RenderMudPig renderer;

    public LayerMudPig(@Nonnull RenderMudPig renderer) { this.renderer = renderer; }

    @Override
    public void doRenderLayer(@Nonnull EntityMudPig entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        renderer.bindTexture(MUD_TEXTURE);
        mudModel.setModelAttributes(renderer.getMainModel());
        mudModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        //renders flower
        if(entity.hasFlower()) {
            if(!entity.isChild()) {
                final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
                renderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

                GlStateManager.enableCull();
                GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
                GlStateManager.pushMatrix();

                mudModel.head.postRender(0.0625f);
                GlStateManager.scale(0.5, -0.5, 0.5);
                GlStateManager.translate(-0.4, 0.5, 0.1);
                GlStateManager.rotate(12, 0, 1, 0);
                blockRenderer.renderBlockBrightness(Blocks.RED_FLOWER.getStateFromMeta(entity.getFlower()), 15);

                GlStateManager.popMatrix();
                GlStateManager.cullFace(GlStateManager.CullFace.BACK);
                GlStateManager.disableCull();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() { return true; }
}
