package git.jbredwards.njarm.mod.client.entity.renderer.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
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
public class LayerClothing implements LayerRenderer<EntityLivingBase>
{
    @Nonnull protected final ResourceLocation texture;
    @Nonnull protected final RenderLivingBase<?> renderer;
    @Nonnull protected final ModelBase layerModel;

    public LayerClothing(@Nonnull RenderLivingBase<?> renderer, @Nonnull ModelBase layerModel, @Nonnull ResourceLocation texture) {
        this.renderer = renderer;
        this.layerModel = layerModel;
        this.texture = texture;
    }

    @Override
    public void doRenderLayer(@Nonnull EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        layerModel.setModelAttributes(renderer.getMainModel());
        layerModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        renderer.bindTexture(texture);
        layerModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public boolean shouldCombineTextures() { return true; }
}
