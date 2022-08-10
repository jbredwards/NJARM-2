package git.jbredwards.njarm.mod.client.entity.renderer;

import git.jbredwards.njarm.mod.common.entity.item.EntityChargedSunstone;
import git.jbredwards.njarm.mod.common.entity.passive.*;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.item.EntityFallingBlock;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityChocolateCow.class, RenderChocolateCow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlock.class, RenderMultiLayerFallingBlock::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHighlandCoo.class, RenderHighlandCoo::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMoobloom.class, RenderMoobloom::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMudPig.class, RenderMudPig::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityXPOrb.class, RenderTranslucentXPOrb::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityChargedSunstone.class, manager ->
                new RenderSnowball<>(manager, ModItems.CHARGED_SUNSTONE, Minecraft.getMinecraft().getRenderItem()));
    }
}
