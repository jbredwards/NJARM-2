package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerGlowingEyes;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.entity.passive.EntitySquid;
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
public class RenderGlowSquid extends RenderSquid
{
    @Nonnull
    protected static final ResourceLocation
            TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/glow_squid/glow_squid.png"),
            EYES = new ResourceLocation(Constants.MODID, "textures/entity/glow_squid/glow_squid_eyes.png");

    public RenderGlowSquid(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn);
        addLayer(new LayerGlowingEyes(this, true, EYES));
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySquid entity) { return TEXTURE; }
}
