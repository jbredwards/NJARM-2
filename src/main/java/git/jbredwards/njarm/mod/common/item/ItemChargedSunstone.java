package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.item.util.InvulnerableItem;
import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

/**
 *
 * @author jbred
 *
 */
public class ItemChargedSunstone extends ItemThrowable implements InvulnerableItem
{
    @Nonnull
    protected static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation(Constants.MODID, "textures/items/charged_sunstone_glint.png");

    public ItemChargedSunstone(@Nonnull FromThrower fromThrower, @Nonnull FromDispenser fromDispenser) { super(fromThrower, fromDispenser); }
    public ItemChargedSunstone(@Nonnull FromThrower fromThrower, @Nonnull BooleanSupplier canThrow, @Nonnull FromDispenser fromDispenser, @Nonnull BooleanSupplier canDispense) {
        super(fromThrower, canThrow, fromDispenser, canDispense);
    }

    @Override
    public boolean isFireImmune(@Nonnull ItemStack stack) { return true; }

    @Override
    public boolean isExplodeImmune(@Nonnull ItemStack stack) { return true; }

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        Minecraft.getMinecraft().getTextureManager().bindTexture(LIGHTNING_TEXTURE);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        GlStateManager.matrixMode(5890);

        final float f = (Minecraft.getSystemTime() % 3000) / 3000f / 24;
        final float f1 = (Minecraft.getSystemTime() % 4873) / 4873f / 24;

        GlStateManager.pushMatrix();
        GlStateManager.scale(16, 8, 16);
        GlStateManager.translate(f, f1, 0);
        Minecraft.getMinecraft().getRenderItem().renderModel(RenderUtils.getBakedModel(stack), stack);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.scale(16, 8, 16);
        GlStateManager.translate(-f1, -f, 0);
        Minecraft.getMinecraft().getRenderItem().renderModel(RenderUtils.getBakedModel(stack), stack);
        GlStateManager.popMatrix();

        GlStateManager.matrixMode(5888);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();

        return super.hasEffect(stack);
    }
}
