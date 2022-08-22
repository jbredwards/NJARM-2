package git.jbredwards.njarm.mod.client.entity.renderer.layers;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.RenderSoulSkeleton;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.AbstractSkeleton;
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
public class LayerSoulSkeletonClothing implements LayerRenderer<AbstractSkeleton>
{
    @Nonnull protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/soul_skeleton/overlay.png");

    @Nonnull protected final RenderSoulSkeleton renderer;
    @Nonnull protected final ModelSkeleton layerModel = new ModelSkeleton(0.25f, true);

    public LayerSoulSkeletonClothing(@Nonnull RenderSoulSkeleton renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(@Nonnull AbstractSkeleton entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.color(1, 1, 1);
        layerModel.setModelAttributes(renderer.getMainModel());
        layerModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        renderer.bindTexture(TEXTURE);
        layerModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public boolean shouldCombineTextures() { return true; }
}
