package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
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
public class RenderFireSkeleton extends RenderSkeleton
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/fire_skeleton.png");
    public RenderFireSkeleton(@Nonnull RenderManager renderManagerIn) { super(renderManagerIn); }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull AbstractSkeleton entity) { return TEXTURE; }
}
