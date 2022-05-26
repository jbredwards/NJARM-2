package git.jbredwards.njarm.mod.common.config.client;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.terraingen.BiomeEvent;
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
    @Config.Comment("Experience orbs are translucent, like when they were first added.")
    public final boolean doTranslucentXPOrbs;
    public static boolean doTranslucentXPOrbs() { return ConfigHandler.clientCfg.renderingCfg.doTranslucentXPOrbs; }

    @Config.Comment("Entity shadows shrink the further the entity is from the ground, like in the Bedrock Edition of the game.")
    public final boolean doBedrockShadowSize;
    public static boolean doBedrockShadowSize() { return ConfigHandler.clientCfg.renderingCfg.doBedrockShadowSize; }

    @Config.SlidingOption
    @Config.RangeInt(min = 0, max = 200)
    @Config.Comment("How many ticks of night vision remaining when the screen starts to flash? " +
            "Setting this to 0 will disable it entirely, vanilla by default starts at 200 ticks (10 seconds).")
    public final int nightVisionFlashing;
    public static int nightVisionFlashing() { return ConfigHandler.clientCfg.renderingCfg.nightVisionFlashing; }

    @Config.SlidingOption
    @Config.RangeInt(min = 0, max = 7)
    @Config.Comment("Functions the same as the vanilla 1.13+ option, vanilla 1.12 has this set to 1.")
    public final int biomeColorBlendRadius;
    public static int biomeColorBlendRadius() { return ConfigHandler.clientCfg.renderingCfg.biomeColorBlendRadius; }

    @Config.Comment("Water surface color & fog color based on biome ids, first the biome, then the surface color, lastly the fog color.")
    @Nonnull public final String[] waterColors;
    @Nonnull public static final Object2IntMap<Biome> FOG_COLORS = new Object2IntOpenHashMap<>();
    @Nonnull public static final Object2IntMap<Biome> SURFACE_COLORS = new Object2IntOpenHashMap<>();

    @Override
    public void onUpdate() {
        FOG_COLORS.clear();
        SURFACE_COLORS.clear();
        for(String colors : waterColors) {
            try {
                final NBTTagCompound nbt = JsonToNBT.getTagFromJson(colors);
                if(nbt.hasKey("Biome", NBT.TAG_STRING)) {
                    final @Nullable Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(nbt.getString("Biome")));
                    if(biome != null) {
                        if(nbt.hasKey("Surface", NBT.TAG_INT)) SURFACE_COLORS.put(biome, nbt.getInteger("Surface"));
                        if(nbt.hasKey("Fog", NBT.TAG_INT)) FOG_COLORS.put(biome, nbt.getInteger("Fog"));
                    }
                }
            }
            //oops
            catch(NBTException e) { e.printStackTrace(); }
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

    @SubscribeEvent //mostly copied from ItemRenderer, changed to apply biome colors
    public static void applyUnderwaterOverlayBiomeColors(@Nonnull RenderBlockOverlayEvent event) {
        if(event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER) {
            final EntityPlayer player = event.getPlayer();
            final float[] biomeComp = new Color(BiomeColorHelper.getWaterColorAtPos(Minecraft.getMinecraft().world,
                    new BlockPos(player.getPositionEyes(event.getRenderPartialTicks())))).getColorComponents(new float[3]);
            final float brightness = Minecraft.getMinecraft().player.getBrightness() * 0.5f;

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/misc/underwater.png"));
            BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
            GlStateManager.color(biomeComp[0] * 0.5f + brightness, biomeComp[1] * 0.5f + brightness, biomeComp[2] * 0.5f + brightness, 0.5F);
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

    //old fog color implementation
    /*@SubscribeEvent(receiveCanceled = true)
    public static void applyWaterFogColor(@Nonnull EntityViewRenderEvent.FogColors event) {
        if(event.getState().getMaterial() == Material.WATER) {
            final World world = event.getEntity().world;
            final BlockPos pos = new BlockPos(event.getEntity());
            final Vec3d original = new Vec3d(event.getRed(), event.getGreen(), event.getBlue());
            final Vec3d water = Blocks.WATER.getFogColor(world, pos, event.getState(), event.getEntity(), original, (float)event.getRenderPartialTicks());

            //checks the player's head is under water
            if(original != water) {
                final Biome biome = world.getBiomeForCoordsBody(pos);
                final Color oldColor = new Color(ColorHandlers.BLOCK_WATER.colorMultiplier(event.getState(), world, pos, 0));
                final Color newColor;

                if(FOG_COLORS.containsKey(biome)) newColor = new Color(FOG_COLORS.get(biome));
                else newColor = oldColor;

                final float[] oldComp = oldColor.getColorComponents(new float[3]);
                final float[] newComp = newColor.getColorComponents(new float[3]);

                final float blendR = oldComp[0] * 0.5f + newComp[0] * 0.5f;
                final float blendG = oldComp[1] * 0.5f + newComp[1] * 0.5f;
                final float blendB = oldComp[2] * 0.5f + newComp[2] * 0.5f;

                event.setRed(blendR);
                event.setGreen(blendG);
                event.setBlue(blendB);
            }
        }
    }*/

    //needed for gson
    public RenderingConfig(boolean doTranslucentXPOrbs, boolean doBedrockShadowSize, int nightVisionFlashing, int biomeColorBlendRadius, @Nonnull String[] waterColors) {
        this.doTranslucentXPOrbs = doTranslucentXPOrbs;
        this.doBedrockShadowSize = doBedrockShadowSize;
        this.nightVisionFlashing = nightVisionFlashing;
        this.biomeColorBlendRadius = biomeColorBlendRadius;
        this.waterColors = waterColors;
    }
}
