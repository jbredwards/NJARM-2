package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.model.ModelPigman;
import git.jbredwards.njarm.mod.common.entity.monster.EntityPigman;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
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
public class RenderPigman extends RenderBiped<EntityPigman>
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/pigman.png");
    public RenderPigman(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelPigman.Base(0), 0.5f);
        addLayer(new LayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                modelLeggings = new ModelPigman.Armor(0.5f);
                //not just 1 cause than the lower part of the helmet texture has issues with the nose
                modelArmor = new ModelPigman.Armor(1.001f);
            }
        });
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityPigman entity) { return TEXTURE; }

    @Override
    protected void applyRotations(@Nonnull EntityPigman entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        if(entityLiving.ticksInOverworld > 0) rotationYaw += Math.cos(ageInTicks * 3.25) * Math.PI;
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
}
