package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerGlowingEyes;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
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
public class RenderFireCreeper extends RenderCreeper
{
    @Nonnull
    protected static final ResourceLocation
            TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/fire_creeper/creeper.png"),
            EYES = new ResourceLocation(Constants.MODID, "textures/entity/fire_creeper/eyes.png");

    public RenderFireCreeper(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn);
        addLayer(new LayerGlowingEyes(this, true, EYES));
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCreeper entity) { return TEXTURE; }
}
