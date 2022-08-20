package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.model.ModelBlestem;
import git.jbredwards.njarm.mod.common.entity.monster.EntityBlestem;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class RenderBlestem extends RenderLiving<EntityBlestem>
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/blestem.png");
    public RenderBlestem(RenderManager renderManagerIn) { super(renderManagerIn, new ModelBlestem(), 0); }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityBlestem entity) { return TEXTURE; }
}
