package git.jbredwards.njarm.mod.client.entity.renderer.layers;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.model.ModelHighlandCoo;
import git.jbredwards.njarm.mod.client.entity.renderer.RenderHighlandCoo;
import git.jbredwards.njarm.mod.common.entity.passive.EntityHighlandCoo;
import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
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
public class LayerHighlandCooWool implements LayerRenderer<EntityHighlandCoo>
{
    @Nonnull protected static final ResourceLocation ADULT_SHEARED = new ResourceLocation(Constants.MODID, "textures/entity/highland_coo/adult_sheared.png");
    @Nonnull protected static final ResourceLocation CHILD_SHEARED = new ResourceLocation(Constants.MODID, "textures/entity/highland_coo/child_sheared.png");
    @Nonnull protected static final ResourceLocation ADULT_UNSHEARED = new ResourceLocation(Constants.MODID, "textures/entity/highland_coo/adult_unsheared.png");
    @Nonnull protected static final ResourceLocation CHILD_UNSHEARED = new ResourceLocation(Constants.MODID, "textures/entity/highland_coo/child_unsheared.png");
    @Nonnull protected final ModelHighlandCoo WOOL_MODEL = new ModelHighlandCoo.Wool();
    @Nonnull protected final RenderHighlandCoo renderer;

    public LayerHighlandCooWool(@Nonnull RenderHighlandCoo renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(@Nonnull EntityHighlandCoo entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!entity.isInvisible()) {
            //inside joke with my sister, don't question lol
            if("JoeBobBilly_".equals(entity.getCustomNameTag()))
                GlStateManager.color(142f/255, 90f/255, 50f/255);
            //apply rainbow color
            else if("jeb_".equals(entity.getCustomNameTag()) || "jbredwards_".equals(entity.getCustomNameTag()))
                RenderUtils.colorRainbow(entity, partialTicks);
            //apply standard color
            else {
                final float[] colors = EntitySheep.getDyeRgb(entity.getFleeceColor());
                GlStateManager.color(colors[0], colors[1], colors[2]);
            }

            //render the colored part of the base model
            renderer.bindTexture(entity.isChild() ? CHILD_SHEARED : ADULT_SHEARED);
            renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            //render wool
            if(!entity.getSheared()) {
                renderer.bindTexture(entity.isChild() ? CHILD_UNSHEARED : ADULT_UNSHEARED);
                WOOL_MODEL.setModelAttributes(renderer.getMainModel());
                WOOL_MODEL.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
                WOOL_MODEL.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() { return true; }
}
