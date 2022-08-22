package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerFireSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
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
public class RenderFireSkeleton extends RenderSkeleton
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/fire_skeleton/skeleton.png");

    public RenderFireSkeleton(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn);
        addLayer(new LayerFireSkeleton(this));
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull AbstractSkeleton entity) { return TEXTURE; }

    @Override
    protected void preRenderCallback(@Nonnull AbstractSkeleton entity, float partialTickTime) {
        GlStateManager.scale(1.1, 1.1, 1.1);
    }
}
