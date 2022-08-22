package git.jbredwards.njarm.mod.client.entity.renderer.layers;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.RenderFireSkeleton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class LayerFireSkeleton implements LayerRenderer<AbstractSkeleton>
{
    @Nonnull protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/fire_skeleton/overlay.png");
    @Nonnull protected final RenderFireSkeleton renderer;
    @Nonnull protected final ModelSkeleton model = new ModelSkeleton(0.1f, false);

    public LayerFireSkeleton(@Nonnull RenderFireSkeleton renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(@Nonnull AbstractSkeleton entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        final double frame = (entity.ticksExisted + partialTicks) * 0.01;
        final boolean invisible = entity.isInvisible();

        GlStateManager.depthMask(!invisible);
        renderer.bindTexture(TEXTURE);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0, frame, 0);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.enableBlend();
        GlStateManager.color(0.5f, 0.5f, 0.5f);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        model.setModelAttributes(renderer.getMainModel());
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(invisible);
    }

    @Override
    public boolean shouldCombineTextures() { return false; }
}
