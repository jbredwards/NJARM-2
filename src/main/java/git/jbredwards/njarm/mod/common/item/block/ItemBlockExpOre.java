package git.jbredwards.njarm.mod.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemBlockExpOre extends ItemBlock
{
    public ItemBlockExpOre(@Nonnull Block block) { super(block); }

    //renders the experience ore part of the item like it's glowing
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        ForgeHooksClient.setRenderLayer(BlockRenderLayer.CUTOUT);
        final RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
        final IBakedModel model = renderer.getItemModelWithOverrides(stack, null, null);
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        final float lastBrightnessX = OpenGlHelper.lastBrightnessX, lastBrightnessY = OpenGlHelper.lastBrightnessY;
        //open gl start
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE_MINUS_DST_COLOR);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.pushMatrix();
        //do the render
        buffer.begin(7, DefaultVertexFormats.ITEM);
        for(EnumFacing facing : EnumFacing.values()) renderer.renderQuads(buffer, model.getQuads(null, facing, 0), -1, stack);
        renderer.renderQuads(buffer, model.getQuads(null, null, 0), -1, stack);
        ForgeHooksClient.setRenderLayer(null);
        Tessellator.getInstance().draw();
        //open gl finish
        GlStateManager.popMatrix();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
        //don't change the enchantment effect
        return super.hasEffect(stack);
    }
}
