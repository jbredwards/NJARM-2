package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.entity.item.EntityBlestemArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
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
public class RenderBlestemArrow extends RenderArrow<EntityBlestemArrow>
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/blestem_arrow.png");
    public RenderBlestemArrow(@Nonnull RenderManager renderManagerIn) { super(renderManagerIn); }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityBlestemArrow entity) { return TEXTURE; }
}
