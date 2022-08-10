package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.client.entity.renderer.layers.LayerMudPig;
import git.jbredwards.njarm.mod.common.entity.passive.EntityMudPig;
import net.minecraft.client.model.ModelPig;
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
public class RenderMudPig extends RenderLiving<EntityMudPig>
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig.png");

    public RenderMudPig(@Nonnull RenderManager manager) {
        super(manager, new ModelPig(), 0.7f);
        addLayer(new LayerMudPig(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityMudPig entity) { return TEXTURE; }
}
