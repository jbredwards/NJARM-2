package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.entity.passive.EntityChocolateCow;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
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
public class RenderChocolateCow extends RenderLiving<EntityChocolateCow>
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/chocolate_cow.png");
    public RenderChocolateCow(@Nonnull RenderManager manager) { super(manager, new ModelCow(), 0.7f); }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityChocolateCow entity) { return TEXTURE; }
}
