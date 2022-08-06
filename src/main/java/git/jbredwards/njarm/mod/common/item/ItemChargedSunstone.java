package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.item.ChargedSunstoneConfig;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemChargedSunstone extends ItemThrowable
{
    @Nonnull
    protected static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation(Constants.MODID, "textures/items/charged_sunstone_glint.png");

    public ItemChargedSunstone(@Nonnull FromThrower fromThrower, @Nonnull FromDispenser fromDispenser) { super(fromThrower, fromDispenser); }
    public ItemChargedSunstone(@Nonnull FromThrower fromThrower, @Nonnull BooleanSupplier canThrow, @Nonnull FromDispenser fromDispenser, @Nonnull BooleanSupplier canDispense) {
        super(fromThrower, canThrow, fromDispenser, canDispense);
    }

    @SubscribeEvent
    public static void onLightningStrike(@Nonnull EntityStruckByLightningEvent event) {
        if(event.getEntity() instanceof EntityItem && !event.getEntity().world.isRemote) {
            final EntityItem entity = (EntityItem)event.getEntity();
            final ItemStack stack = entity.getItem();

            if(stack.getItem() == ModItems.SUNSTONE) {
                if(ChargedSunstoneConfig.fromLightning()) entity.setItem(new ItemStack(ModItems.CHARGED_SUNSTONE,
                        stack.getCount(), stack.getMetadata(), stack.getTagCompound()));

                event.setCanceled(true);
            }

            else if(stack.getItem() == ModItems.CHARGED_SUNSTONE) event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        Minecraft.getMinecraft().getTextureManager().bindTexture(LIGHTNING_TEXTURE);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        GlStateManager.matrixMode(5890);

        final IBakedModel model = RenderUtils.getBakedModel(stack);
        final float f = (Minecraft.getSystemTime() % 3000) / 3000f / 24;
        final float f1 = (Minecraft.getSystemTime() % 4873) / 4873f / 24;

        GlStateManager.pushMatrix();
        GlStateManager.scale(16, FMLClientHandler.instance().hasOptifine() ? 16 : 8, 16);
        GlStateManager.translate(f, f1, 0);
        Minecraft.getMinecraft().getRenderItem().renderModel(model, stack);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.scale(16, FMLClientHandler.instance().hasOptifine() ? 16 : 8, 16);
        GlStateManager.translate(-f1, -f, 0);
        Minecraft.getMinecraft().getRenderItem().renderModel(model, stack);
        GlStateManager.popMatrix();

        GlStateManager.matrixMode(5888);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        return super.hasEffect(stack);
    }
}
