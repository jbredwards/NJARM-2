package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.common.entity.passive.EntityHighlandCoo;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * binds entities to their renders
 * @author jbred
 *
 */
public final class EntityRendererHandler
{
    @SideOnly(Side.CLIENT)
    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityHighlandCoo.class, RenderHighlandCoo::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityXPOrb.class, RenderTranslucentXPOrb::new);
    }
}
