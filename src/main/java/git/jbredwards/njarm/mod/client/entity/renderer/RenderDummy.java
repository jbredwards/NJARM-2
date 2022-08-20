package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.client.entity.model.ModelDummy;
import git.jbredwards.njarm.mod.common.entity.item.EntityDummy;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.resources.I18n;
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
public class RenderDummy extends RenderLivingBase<EntityDummy>
{
    @Nonnull
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/dummy.png");

    public RenderDummy(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelDummy(), 0);
        this.addLayer(new LayerArmorStandArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerElytra(this));
        this.addLayer(new LayerCustomHead(((ModelDummy)getMainModel()).bipedHead));
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityDummy entity) { return TEXTURE; }

    @Override
    protected boolean canRenderName(@Nonnull EntityDummy entity) { return false; }

    @Override
    public void doRender(@Nonnull EntityDummy entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if(!entity.isInvisible() && !entity.isDead && entity.ticksToDisplay > 0)
            renderLivingLabel(entity, I18n.format("entity.njarm.dummy.lastDamage", entity.lastDamageString), x, y, z, 8);

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void applyRotations(@Nonnull EntityDummy entity, float ageInTicks, float rotationYaw, float partialTicks) {
        GlStateManager.rotate(180 - rotationYaw, 0, 1, 0);

        final float punchRot = entity.world.getTotalWorldTime() - entity.punchCooldown + partialTicks;
        if(punchRot < 5) GlStateManager.rotate((float)(Math.sin(punchRot / 1.5 * Math.PI) * 3), 0, 1, 0);

        super.applyRotations(entity, ageInTicks, rotationYaw, partialTicks);
    }

    protected static class LayerArmorStandArmor extends LayerBipedArmor
    {
        public LayerArmorStandArmor(@Nonnull RenderDummy rendererIn) { super(rendererIn); }

        @Override
        protected void initArmor() {
            modelLeggings = new ModelArmorStandArmor(0.5f);
            modelArmor = new ModelArmorStandArmor(1);
        }
    }
}
