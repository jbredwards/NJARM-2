package git.jbredwards.njarm.mod.common.config.client;

import git.jbredwards.fluidlogged_api.api.util.FluidloggedUtils;
import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.NBTUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID, value = Side.CLIENT)
public final class RenderingConfig implements IConfig
{
    @Config.LangKey("config.njarm.client.rendering.doTranslucentXPOrbs")
    public final boolean doTranslucentXPOrbs;
    public static boolean doTranslucentXPOrbs() { return ConfigHandler.clientCfg.renderingCfg.doTranslucentXPOrbs; }

    @Config.LangKey("config.njarm.client.rendering.doBedrockShadowSize")
    public final boolean doBedrockShadowSize;
    public static boolean doBedrockShadowSize() { return ConfigHandler.clientCfg.renderingCfg.doBedrockShadowSize; }

    @Config.RangeInt(min = 0, max = 200)
    @Config.LangKey("config.njarm.client.rendering.nightVisionFlashing")
    public final int nightVisionFlashing;
    public static int nightVisionFlashing() { return ConfigHandler.clientCfg.renderingCfg.nightVisionFlashing; }

    @Config.SlidingOption
    @Config.RangeInt(min = 0, max = 16)
    @Config.LangKey("config.njarm.client.rendering.biomeColorBlendRadius")
    public final int biomeColorBlendRadius;
    public static int biomeColorBlendRadius() { return ConfigHandler.clientCfg.renderingCfg.biomeColorBlendRadius; }

    //TODO
    /*@Config.LangKey("config.njarm.client.rendering.emissiveExpOres")
    public final boolean emissiveExpOres;
    public static boolean emissiveExpOres() { return ConfigHandler.clientCfg.renderingCfg.emissiveExpOres; }*/

    @Config.LangKey("config.njarm.client.rendering.waterColors")
    @Nonnull public final String[] waterColors;
    @Nonnull public static final Object2IntMap<Biome> FOG_COLORS = new Object2IntOpenHashMap<>();
    @Nonnull public static final Object2IntMap<Biome> SURFACE_COLORS = new Object2IntOpenHashMap<>();

    @Override
    public void onUpdate() {
        FOG_COLORS.clear();
        SURFACE_COLORS.clear();
        for(String colors : waterColors) {
            final NBTTagCompound nbt = NBTUtils.getTagFromString(colors);
            if(nbt.hasKey("Biome", NBT.TAG_STRING)) {
                final @Nullable Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(nbt.getString("Biome")));
                if(biome != null) {
                    if(nbt.hasKey("Surface", NBT.TAG_INT)) SURFACE_COLORS.put(biome, nbt.getInteger("Surface"));
                    if(nbt.hasKey("Fog", NBT.TAG_INT)) FOG_COLORS.put(biome, nbt.getInteger("Fog"));
                }
            }
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public static void applyWaterSurfaceColor(@Nonnull BiomeEvent.GetWaterColor event) {
        if(SURFACE_COLORS.containsKey(event.getBiome())) event.setNewColor(SURFACE_COLORS.get(event.getBiome()));
        else { //blend old biome color with old vanilla hardcoded water texture color to try to better recreate the indented old biome colors
            final float[] biomeComp = new Color(event.getOriginalColor()).getColorComponents(new float[3]);
            event.setNewColor(new Color(
                    Math.min(1, biomeComp[0] * 76f/255 + 47f/255 * 179f/255),
                    Math.min(1, biomeComp[1] * 76f/255 + 67f/255 * 179f/255),
                    Math.min(1, biomeComp[2] * 76f/255 + 244f/255 * 179f/255))
                    .getRGB());
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public static void applyWaterFogColor(@Nonnull EntityViewRenderEvent.FogColors event) {
        if(event.getState().getMaterial() == Material.WATER) {
            //checks the player's head is under water
            if(FluidloggedUtils.isCompatibleFluid(FluidloggedUtils.getFluidFromState(event.getState()), FluidRegistry.WATER)) {
                final float[] fogComp = new Color(BiomeColorHelper.getColorAtPos(event.getEntity().world, event.getEntity().getPosition(),
                        (biome, pos) -> FOG_COLORS.containsKey(biome) ? FOG_COLORS.get(biome) : biome.getWaterColor())).getColorComponents(new float[3]);

                event.setRed(fogComp[0] * 0.5f + 0.5f * event.getRed());
                event.setGreen(fogComp[1] * 0.5f + 0.5f * event.getGreen());
                event.setBlue(fogComp[2] * 0.5f + 0.5f * event.getBlue());
            }
        }
    }

    @SubscribeEvent //mostly copied from ItemRenderer, changed to apply biome colors
    public static void applyUnderwaterOverlayBiomeColors(@Nonnull RenderBlockOverlayEvent event) {
        if(event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER && FluidloggedUtils.isCompatibleFluid(FluidloggedUtils.getFluidFromState(event.getBlockForOverlay()), FluidRegistry.WATER)) {
            final EntityPlayer player = event.getPlayer();
            final float brightness = Minecraft.getMinecraft().player.getBrightness() * 0.2f;
            final float[] fogComp = new Color(BiomeColorHelper.getColorAtPos(Minecraft.getMinecraft().world, new BlockPos(player.getPositionEyes(event.getRenderPartialTicks())),
                    (biome, pos) -> FOG_COLORS.containsKey(biome) ? FOG_COLORS.get(biome) : biome.getWaterColor())).getColorComponents(new float[3]);

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Constants.MODID, "textures/misc/underwater.png"));
            BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
            GlStateManager.color(fogComp[0] * 0.8f + brightness, fogComp[1] * 0.8f + brightness, fogComp[2] * 0.8f + brightness, 0.5F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();

            float yaw = -Minecraft.getMinecraft().player.rotationYaw / 64;
            float pitch = Minecraft.getMinecraft().player.rotationPitch / 64;
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1, -1, -0.5).tex(4 + yaw, 4 + pitch).endVertex();
            bufferbuilder.pos(1, -1, -0.5).tex(yaw, 4 + pitch).endVertex();
            bufferbuilder.pos(1, 1, -0.5).tex(yaw, pitch).endVertex();
            bufferbuilder.pos(-1, 1, -0.5).tex(4 + yaw, pitch).endVertex();
            Tessellator.getInstance().draw();

            GlStateManager.popMatrix();
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableBlend();
            event.setCanceled(true);
        }
    }

    //needed for gson
    public RenderingConfig(boolean doTranslucentXPOrbs, boolean doBedrockShadowSize, int nightVisionFlashing, int biomeColorBlendRadius, @Nonnull String[] waterColors) {
        this.doTranslucentXPOrbs = doTranslucentXPOrbs;
        this.doBedrockShadowSize = doBedrockShadowSize;
        this.nightVisionFlashing = nightVisionFlashing;
        this.biomeColorBlendRadius = biomeColorBlendRadius;
        this.waterColors = waterColors;
    }
}
