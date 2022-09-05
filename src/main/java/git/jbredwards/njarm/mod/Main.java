package git.jbredwards.njarm.mod;

import com.cleanroommc.assetmover.AssetMoverAPI;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import git.jbredwards.fluidlogged_api.api.util.FluidloggedUtils;
import git.jbredwards.njarm.mod.client.audio.MusicConditions;
import git.jbredwards.njarm.mod.client.audio.MusicConditionTicker;
import git.jbredwards.njarm.mod.client.entity.EntityRendererHandler;
import git.jbredwards.njarm.mod.client.particle.ParticleFactoryColorize;
import git.jbredwards.njarm.mod.common.block.util.IEmissiveBlock;
import git.jbredwards.njarm.mod.common.capability.*;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.message.*;
import git.jbredwards.njarm.mod.common.world.gen.*;
import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;

import java.io.IOException;
import java.nio.charset.Charset;

import static git.jbredwards.njarm.mod.Constants.*;

/**
 * Initializes the mod
 * @author jbred
 *
 */
@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPENDENCIES)
public final class Main
{
    @SuppressWarnings("NotNullFieldNotInitialized")
    @SidedProxy(clientSide = "git.jbredwards.njarm.mod.Main$ClientProxy", serverSide = "git.jbredwards.njarm.mod.Main$CommonProxy")
    @Nonnull public static CommonProxy proxy;

    @SuppressWarnings("NotNullFieldNotInitialized")
    @Nonnull public static SimpleNetworkWrapper wrapper;

    @Mod.EventHandler
    private static void construct(@Nonnull FMLConstructionEvent event) throws IOException { proxy.construct(); }

    @Mod.EventHandler
    private static void preInit(@Nonnull FMLPreInitializationEvent event) { proxy.preInit(); }

    @Mod.EventHandler
    private static void init(@Nonnull FMLInitializationEvent event) { proxy.init(); }

    @Mod.EventHandler
    private static void postInit(@Nonnull FMLPostInitializationEvent event) { proxy.postInit(); }

    //handles server-side code
    public static class CommonProxy
    {
        protected void construct() throws IOException {}

        protected void preInit() {
            //register capabilities
            CapabilityManager.INSTANCE.register(IBlueFire.class, IBlueFire.Storage.INSTANCE, IBlueFire.Impl::new);
            CapabilityManager.INSTANCE.register(IBonusHealth.class, IBonusHealth.Storage.INSTANCE, IBonusHealth.NONE);
            CapabilityManager.INSTANCE.register(IBubbleColumn.class, IBubbleColumn.Storage.INSTANCE, IBubbleColumn.Impl::new);
            CapabilityManager.INSTANCE.register(IHorseCarrotTime.class, IHorseCarrotTime.Storage.INSTANCE, IHorseCarrotTime.Impl::new);
            //register packets
            wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
            wrapper.registerMessage(MessageBlueFire.Handler.INSTANCE, MessageBlueFire.class, 1, Side.CLIENT);
            wrapper.registerMessage(MessageParticle.Handler.INSTANCE, MessageParticle.class, 2, Side.CLIENT);
            //world generators
            GameRegistry.registerWorldGenerator(new BasaltPillarGenerator(), 3);
            GameRegistry.registerWorldGenerator(new DesertMobSpawnerGenerator(), 4);
            GameRegistry.registerWorldGenerator(new EndLakeGenerator(), 1);
            GameRegistry.registerWorldGenerator(new EndForestGenerator(), 5);
            GameRegistry.registerWorldGenerator(new NetherMobSpawnerGenerator(), 4);
            GameRegistry.registerWorldGenerator(new LavaMagmaGenerator(), 10);
            GameRegistry.registerWorldGenerator(new OreGenerator(), 3);
        }

        protected void init() {
            //run config init
            ConfigHandler.onFMLInit();
            //improve block SoundTypes
            Blocks.BONE_BLOCK.setSoundType(ModSounds.BONE);
            Blocks.QUARTZ_ORE.setSoundType(ModSounds.NETHER_ORE);
            Blocks.NETHER_BRICK.setSoundType(ModSounds.NETHER_BRICKS);
            Blocks.NETHER_BRICK_FENCE.setSoundType(ModSounds.NETHER_BRICKS);
            Blocks.NETHER_BRICK_STAIRS.setSoundType(ModSounds.NETHER_BRICKS);
            Blocks.NETHER_WART.setSoundType(ModSounds.NETHERWART);
            Blocks.NETHER_WART_BLOCK.setSoundType(ModSounds.NETHERWART);
            Blocks.NETHERRACK.setSoundType(ModSounds.NETHERRACK);
            Blocks.SOUL_SAND.setSoundType(ModSounds.SOUL_SAND);
            Blocks.WATERLILY.setSoundType(ModSounds.WET_GRASS);
            //improve the potion color for the health boost effect
            MobEffects.HEALTH_BOOST.liquidColor = 10420224;
        }

        protected void postInit() {
            //update water light opacity levels to match those from 1.13
            Blocks.FLOWING_WATER.setLightOpacity(2);
            Blocks.WATER.setLightOpacity(2);
        }

        //side specific functions called throughout the mod
        public boolean isLightEmissive(@Nonnull IBlockState state, @Nonnull IEmissiveBlock block) { return false; }
        public void markRenderUpdate() {}
    }

    //handles client-side code
    @SuppressWarnings("unused")
    public static class ClientProxy extends CommonProxy
    {
        //gather vanilla assets
        @Override
        protected void construct() throws IOException {
            LOGGER.info("Attempting to gather the vanilla assets required by this mod, this may take a while if it's your first load...");
            final String[][] assets = new Gson().fromJson(IOUtils.toString(Loader.class.getResourceAsStream("/assets/assetmover.jsonc"), Charset.defaultCharset()), String[][].class);
            final ProgressManager.ProgressBar progressBar = ProgressManager.push("AssetMover", assets.length);
            for(String[] asset : assets) { //display progress, otherwise it looks like the game froze
                progressBar.step(asset[2].replaceFirst(String.format("assets/%s/", MODID), ""));
                AssetMoverAPI.fromMinecraft(asset[0], ImmutableMap.of(asset[1], asset[2]));
            }

            ProgressManager.pop(progressBar);
            LOGGER.info("Success!");
        }

        @Override
        protected void preInit() {
            EntityRendererHandler.registerEntityRenderers();
            //handle common-side stuff
            super.preInit();
        }

        @Override
        protected void init() {
            //override some vanilla particle factories to allow for custom coloring
            final ParticleManager manager = Minecraft.getMinecraft().effectRenderer;
            manager.registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(),   new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleDrip.WaterFactory()));
            manager.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(),   new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleRain.Factory()));
            manager.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleSplash.Factory()));
            manager.registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(),   new ParticleFactoryColorize(BiomeColorHelper::getWaterColorAtPos, new ParticleWaterWake.Factory()));
            //fix bubble particles spawning in illegal blocks (like cauldrons)
            final IParticleFactory bubbleFactory = manager.particleTypes.get(EnumParticleTypes.WATER_BUBBLE.getParticleID());
            manager.registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), (int particleID, @Nonnull World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) ->
                    FluidloggedUtils.getFluidOrReal(world, new BlockPos(x, y, z)).getMaterial() == Material.WATER ? bubbleFactory.createParticle(particleID, world, x, y, z, xSpeed, ySpeed, zSpeed, args) : null);

            //handle common-side stuff
            super.init();
        }

        //backport the 1.13+ underwater music functionality
        @Override
        protected void postInit() {
            MusicConditions.registerConditions();
            ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(),
                    new MusicConditionTicker(), "field_147126_aw");

            //handle common-side stuff
            super.postInit();
        }

        //use optifine emissive textures if that mod is loaded (works better with OF shaderpacks)
        //otherwise use this mod's emissive rendering
        @Override
        public boolean isLightEmissive(@Nonnull IBlockState state, @Nonnull IEmissiveBlock block) {
            return !FMLClientHandler.instance().hasOptifine() && block.isEmissive(state, MinecraftForgeClient.getRenderLayer());
        }

        //called when certain configs change
        @Override
        public void markRenderUpdate() { RenderUtils.markRenderersForReload(true); }
    }
}
