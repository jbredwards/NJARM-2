package git.jbredwards.njarm.mod.common.item.block;

import git.jbredwards.njarm.mod.client.util.SpriteStorage;
import git.jbredwards.njarm.mod.common.block.BlockExperienceOre;
import git.jbredwards.njarm.mod.common.config.client.ParticlesConfig;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jbred
 *
 */
public class ItemBlockExperienceOre extends ItemBlockMeta
{
    @Nonnull
    protected final SpriteStorage[] overlays = new SpriteStorage[3];

    public ItemBlockExperienceOre(@Nonnull Block block) {
        super(block, BlockExperienceOre.TYPE);
        overlays[0] = new SpriteStorage(ParticlesConfig::xpMagicOreParticle);
        overlays[1] = new SpriteStorage(ParticlesConfig::netherXpMagicOreParticle);
        overlays[2] = new SpriteStorage(ParticlesConfig::endXpMagicOreParticle);
    }

    //renders the experience ore part of the item like it's glowing
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
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
        final int meta = MathHelper.clamp(stack.getMetadata(), 0, 2);
        for(EnumFacing facing : EnumFacing.values()) renderer.renderQuads(buffer, retexture(model.getQuads(null, facing, 0), meta), -1, stack);
        renderer.renderQuads(buffer, retexture(model.getQuads(null, null, 0), meta), -1, stack);
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

    //changes the texture to the ore overlay
    @SideOnly(Side.CLIENT)
    protected List<BakedQuad> retexture(List<BakedQuad> quads, int meta) {
        final TextureAtlasSprite sprite = overlays[meta].getSprite();
        return quads.stream().map(quad -> new BakedQuadRetextured(quad, sprite)).collect(Collectors.toList());
    }
}
