package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerClothing;
import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerGlowingEyes;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;
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
public class RenderMummy extends RenderZombie
{
    @Nonnull
    protected static final ResourceLocation
            TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/mummy/mummy.png"),
            OVERLAY = new ResourceLocation(Constants.MODID, "textures/entity/mummy/overlay.png"),
            EYES = new ResourceLocation(Constants.MODID, "textures/entity/mummy/eyes.png");

    public RenderMummy(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn);
        addLayer(new LayerClothing(this, new ModelZombie(0.15f, false), OVERLAY));
        addLayer(new LayerGlowingEyes(this, true, EYES));
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityZombie entity) {return TEXTURE;}
}
