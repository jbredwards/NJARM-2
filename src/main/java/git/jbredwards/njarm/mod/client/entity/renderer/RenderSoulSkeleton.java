package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerClothing;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class RenderSoulSkeleton extends RenderSkeleton
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/soul_skeleton/skeleton.png");

    public RenderSoulSkeleton(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn);
        addLayer(new LayerClothing(this, new ModelSkeleton(0.25f, true),
                new ResourceLocation(Constants.MODID, "textures/entity/soul_skeleton/overlay.png")));
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull AbstractSkeleton entity) { return TEXTURE; }
}
