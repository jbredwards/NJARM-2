package git.jbredwards.njarm.mod.client.entity.renderer.layers;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.RenderMoobloom;
import git.jbredwards.njarm.mod.common.entity.passive.EntityMoobloom;
import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class LayerMoobloom implements LayerRenderer<EntityMoobloom>
{
    @Nonnull protected static final ResourceLocation GRASS_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/moobloom/grass.png");
    @Nonnull protected final RenderMoobloom renderer;

    public LayerMoobloom(@Nonnull RenderMoobloom renderer) { this.renderer = renderer; }

    @Override
    public void doRenderLayer(@Nonnull EntityMoobloom entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!entity.isInvisible()) {
            if(!entity.getSheared()) renderBlocks(entity.getFlower(), entity.isChild());

            //apply rainbow color
            if("jeb_".equals(entity.getCustomNameTag()) || "jbredwards_".equals(entity.getCustomNameTag()))
                RenderUtils.colorRainbow(entity, partialTicks);

            //apply grass color
            else {
                final int color = BiomeColorHelper.getGrassColorAtPos(entity.world, entity.getPosition());
                GlStateManager.color(((color >> 16) & 255) / 255f, ((color >> 8) & 255) / 255f, (color & 255) / 255f);
            }

            renderer.bindTexture(GRASS_TEXTURE);
            renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    protected void renderBlocks(@Nonnull IBlockState flower, boolean isChild) {
        final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        renderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        GlStateManager.enableCull();
        GlStateManager.cullFace(GlStateManager.CullFace.FRONT);

        if(isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1, -1, 1);
            GlStateManager.translate(0, -0.3, 0.1);
            GlStateManager.rotate(42, 0, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5, -0.5, 0.5);
            blockRenderer.renderBlockBrightness(flower, 1);
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        }

        else {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1, -1, 1);
            GlStateManager.translate(0.2, 0.36, 0.5);
            GlStateManager.rotate(42, 0, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5, -0.5, 0.5);
            blockRenderer.renderBlockBrightness(flower, 1);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.1, 0, -0.6);
            GlStateManager.rotate(42, 0, 1, 0);
            GlStateManager.translate(-0.5, -0.5, 0.5);
            blockRenderer.renderBlockBrightness(flower, 1);
            GlStateManager.popMatrix();

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            ((ModelCow)renderer.getMainModel()).head.postRender(0.0625f);
            GlStateManager.scale(1, -1, 1);
            GlStateManager.translate(0, 0.7, -0.2);
            GlStateManager.rotate(12, 0, 1, 0);
            GlStateManager.translate(-0.5, -0.45, 0.5);
            blockRenderer.renderBlockBrightness(flower, 1);
            GlStateManager.popMatrix();
        }

        GlStateManager.cullFace(GlStateManager.CullFace.BACK);
        GlStateManager.disableCull();
    }

    @Override
    public boolean shouldCombineTextures() { return true; }
}
