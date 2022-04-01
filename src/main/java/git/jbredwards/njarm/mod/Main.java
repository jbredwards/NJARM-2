package git.jbredwards.njarm.mod;

import git.jbredwards.njarm.mod.client.particle.ParticleFactoryColorize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static git.jbredwards.njarm.mod.Constants.*;

/**
 * Initializes the mod
 * @author jbred
 *
 */
@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPENDENCIES)
public final class Main
{
    /**
     * Common-side init
     */
    @Mod.EventHandler
    public static void initCommon(@Nonnull FMLInitializationEvent event) {
        if(event.getSide().isClient()) initClient();
        //update water light opacity levels to match those from 1.13
        Blocks.FLOWING_WATER.setLightOpacity(2);
        Blocks.WATER.setLightOpacity(2);
    }

    /**
     * Client-side init
     */
    @SideOnly(Side.CLIENT)
    public static void initClient() {
        //override some vanilla particle factories to allow for custom coloring
        final ParticleManager manager = Minecraft.getMinecraft().effectRenderer;
        manager.registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(),   new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleDrip.WaterFactory()));
        manager.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(),   new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleRain.Factory()));
        manager.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleSplash.Factory()));
        manager.registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(),   new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleWaterWake.Factory()));
    }
}
