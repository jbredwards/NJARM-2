package git.jbredwards.njarm.mod.client.entity.renderer.item;

import git.jbredwards.njarm.mod.common.config.client.RenderingConfig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * xp orbs render as transparent
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class RenderTranslucentXPOrb extends RenderXPOrb
{
    public RenderTranslucentXPOrb(@Nonnull RenderManager renderManagerIn) {
        super(renderManagerIn);
        shadowOpaque = 0.15f;
    }

    @Override
    public void doRender(@Nonnull EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if(RenderingConfig.doTranslucentXPOrbs()) GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        if(RenderingConfig.doTranslucentXPOrbs()) GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
    }
}
