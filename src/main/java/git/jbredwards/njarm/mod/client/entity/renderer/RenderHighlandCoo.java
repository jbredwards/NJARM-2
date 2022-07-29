package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.model.ModelHighlandCoo;
import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerHighlandCooWool;
import git.jbredwards.njarm.mod.common.entity.passive.EntityHighlandCoo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class RenderHighlandCoo extends RenderLiving<EntityHighlandCoo>
{
    @Nonnull protected static final ResourceLocation ADULT_BASE = new ResourceLocation(Constants.MODID, "textures/entity/highland_coo/adult_base.png");
    @Nonnull protected static final ResourceLocation CHILD_BASE = new ResourceLocation(Constants.MODID, "textures/entity/highland_coo/child_base.png");

    public RenderHighlandCoo(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelHighlandCoo.Base(), 0.7f);
        addLayer(new LayerHighlandCooWool(this));
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(@Nonnull EntityHighlandCoo entity) { return entity.isChild() ? CHILD_BASE : ADULT_BASE; }
}
