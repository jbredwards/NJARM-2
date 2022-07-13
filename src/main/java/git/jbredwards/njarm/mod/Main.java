package git.jbredwards.njarm.mod;

import com.cleanroommc.assetmover.AssetMoverAPI;
import com.google.common.collect.ImmutableMap;
import git.jbredwards.fluidlogged_api.api.util.FluidloggedUtils;
import git.jbredwards.njarm.mod.client.entity.renderer.EntityRendererHandler;
import git.jbredwards.njarm.mod.client.particle.ParticleFactoryColorize;
import git.jbredwards.njarm.mod.common.capability.*;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.message.*;
import git.jbredwards.njarm.mod.common.world.generation.OreGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

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
    @SuppressWarnings("NotNullFieldNotInitialized")
    @SidedProxy(clientSide = "git.jbredwards.njarm.mod.Main$ClientProxy", serverSide = "git.jbredwards.njarm.mod.Main$CommonProxy")
    @Nonnull public static CommonProxy proxy;

    @SuppressWarnings("NotNullFieldNotInitialized")
    @Nonnull public static SimpleNetworkWrapper wrapper;

    @Mod.EventHandler
    private static void construct(@Nonnull FMLConstructionEvent event) { proxy.construct(); }

    @Mod.EventHandler
    private static void preInit(@Nonnull FMLPreInitializationEvent event) { proxy.preInit(); }

    @Mod.EventHandler
    private static void init(@Nonnull FMLInitializationEvent event) { proxy.init(); }

    @Mod.EventHandler
    private static void postInit(@Nonnull FMLPostInitializationEvent event) { proxy.postInit(); }

    //handles server-side code
    public static class CommonProxy
    {
        protected void construct() {}

        protected void preInit() {
            //register capabilities
            CapabilityManager.INSTANCE.register(IBlueFire.class, IBlueFire.Storage.INSTANCE, IBlueFire.Impl::new);
            CapabilityManager.INSTANCE.register(IBubbleColumn.class, IBubbleColumn.Storage.INSTANCE, IBubbleColumn.Impl::new);
            //register packets
            wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
            wrapper.registerMessage(MessageBlueFire.Handler.INSTANCE, MessageBlueFire.class, 1, Side.CLIENT);
            wrapper.registerMessage(MessageParticle.Handler.INSTANCE, MessageParticle.class, 2, Side.CLIENT);
            //world generators
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
        }

        protected void postInit() {
            //update water light opacity levels to match those from 1.13
            Blocks.FLOWING_WATER.setLightOpacity(2);
            Blocks.WATER.setLightOpacity(2);
        }

        //side specific functions called throughout the mod
        public boolean getLightForGlowingOre() { return false; }
    }

    //handles client-side code
    @SuppressWarnings("unused")
    public static class ClientProxy extends CommonProxy
    {
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

        //use optifine emissive textures if that mod is loaded
        public boolean getLightForGlowingOre() {
            return !FMLClientHandler.instance().hasOptifine() && MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT;
        }

        //gather vanilla assets
        @Override
        protected void construct() {
            final ImmutableMap<String, String> assets = ImmutableMap.<String,String>builder()
                    //sounds
                    .put("assets/minecraft/sounds/block/beacon/ambient.ogg", String.format("assets/%s/sounds/blocks/nether_reactor_core/ambient.ogg", MODID))
                    .put("assets/minecraft/sounds/block/beacon/activate.ogg", String.format("assets/%s/sounds/blocks/nether_reactor_core/activate.ogg", MODID))
                    .put("assets/minecraft/sounds/block/beacon/deactivate.ogg", String.format("assets/%s/sounds/blocks/nether_reactor_core/deactivate.ogg", MODID))
                    .put("assets/minecraft/sounds/item/armor/equip_netherite1.ogg", String.format("assets/%s/sounds/items/netherite_equip1.ogg", MODID))
                    .put("assets/minecraft/sounds/item/armor/equip_netherite2.ogg", String.format("assets/%s/sounds/items/netherite_equip2.ogg", MODID))
                    .put("assets/minecraft/sounds/item/armor/equip_netherite3.ogg", String.format("assets/%s/sounds/items/netherite_equip3.ogg", MODID))
                    .put("assets/minecraft/sounds/item/armor/equip_netherite4.ogg", String.format("assets/%s/sounds/items/netherite_equip4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/barrel/close.ogg", String.format("assets/%s/sounds/blocks/barrel/close.ogg", MODID))
                    .put("assets/minecraft/sounds/block/barrel/open1.ogg", String.format("assets/%s/sounds/blocks/barrel/open1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/barrel/open2.ogg", String.format("assets/%s/sounds/blocks/barrel/open2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/upwards_ambient1.ogg", String.format("assets/%s/sounds/blocks/bubble_column/upwards_ambient1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/upwards_ambient2.ogg", String.format("assets/%s/sounds/blocks/bubble_column/upwards_ambient2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/upwards_ambient3.ogg", String.format("assets/%s/sounds/blocks/bubble_column/upwards_ambient3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/upwards_ambient4.ogg", String.format("assets/%s/sounds/blocks/bubble_column/upwards_ambient4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/upwards_ambient5.ogg", String.format("assets/%s/sounds/blocks/bubble_column/upwards_ambient5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/whirlpool_ambient1.ogg", String.format("assets/%s/sounds/blocks/bubble_column/whirlpool_ambient1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/whirlpool_ambient2.ogg", String.format("assets/%s/sounds/blocks/bubble_column/whirlpool_ambient2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/whirlpool_ambient3.ogg", String.format("assets/%s/sounds/blocks/bubble_column/whirlpool_ambient3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/whirlpool_ambient4.ogg", String.format("assets/%s/sounds/blocks/bubble_column/whirlpool_ambient4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/whirlpool_ambient5.ogg", String.format("assets/%s/sounds/blocks/bubble_column/whirlpool_ambient5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/upwards_inside.ogg", String.format("assets/%s/sounds/blocks/bubble_column/upwards_inside.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bubble_column/whirlpool_inside.ogg", String.format("assets/%s/sounds/blocks/bubble_column/whirlpool_inside.ogg", MODID))
                    .put("assets/minecraft/sounds/block/lodestone/lock1.ogg", String.format("assets/%s/sounds/blocks/lodestone/lock1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/lodestone/lock2.ogg", String.format("assets/%s/sounds/blocks/lodestone/lock2.ogg", MODID))
                    .put("assets/minecraft/sounds/music/game/nether/crimson_forest/chrysopoeia.ogg", String.format("assets/%s/sounds/music/chrysopoeia.ogg", MODID))
                    .put("assets/minecraft/sounds/music/game/nether/nether_wastes/rubedo.ogg", String.format("assets/%s/sounds/music/rubedo.ogg", MODID))
                    .put("assets/minecraft/sounds/music/game/nether/soulsand_valley/so_below.ogg", String.format("assets/%s/sounds/music/so_below.ogg", MODID))
                    .put("assets/minecraft/sounds/music/game/water/axolotl.ogg", String.format("assets/%s/sounds/music/axolotl.ogg", MODID))
                    .put("assets/minecraft/sounds/music/game/water/dragon_fish.ogg", String.format("assets/%s/sounds/music/dragon_fish.ogg", MODID))
                    .put("assets/minecraft/sounds/music/game/water/shuniji.ogg", String.format("assets/%s/sounds/music/shuniji.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/break1.ogg", String.format("assets/%s/sounds/blocks/bone/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/break2.ogg", String.format("assets/%s/sounds/blocks/bone/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/break3.ogg", String.format("assets/%s/sounds/blocks/bone/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/break4.ogg", String.format("assets/%s/sounds/blocks/bone/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/break5.ogg", String.format("assets/%s/sounds/blocks/bone/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/step1.ogg", String.format("assets/%s/sounds/blocks/bone/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/step2.ogg", String.format("assets/%s/sounds/blocks/bone/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/step3.ogg", String.format("assets/%s/sounds/blocks/bone/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/step4.ogg", String.format("assets/%s/sounds/blocks/bone/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/bone_block/step5.ogg", String.format("assets/%s/sounds/blocks/bone/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/break1.ogg", String.format("assets/%s/sounds/blocks/chain/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/break2.ogg", String.format("assets/%s/sounds/blocks/chain/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/break3.ogg", String.format("assets/%s/sounds/blocks/chain/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/break4.ogg", String.format("assets/%s/sounds/blocks/chain/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/step1.ogg", String.format("assets/%s/sounds/blocks/chain/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/step2.ogg", String.format("assets/%s/sounds/blocks/chain/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/step3.ogg", String.format("assets/%s/sounds/blocks/chain/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/step4.ogg", String.format("assets/%s/sounds/blocks/chain/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/step5.ogg", String.format("assets/%s/sounds/blocks/chain/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/chain/step6.ogg", String.format("assets/%s/sounds/blocks/chain/step6.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/coral1.ogg", String.format("assets/%s/sounds/blocks/coral/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/coral2.ogg", String.format("assets/%s/sounds/blocks/coral/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/coral3.ogg", String.format("assets/%s/sounds/blocks/coral/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/coral4.ogg", String.format("assets/%s/sounds/blocks/coral/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/step/coral1.ogg", String.format("assets/%s/sounds/blocks/coral/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/step/coral2.ogg", String.format("assets/%s/sounds/blocks/coral/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/step/coral3.ogg", String.format("assets/%s/sounds/blocks/coral/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/step/coral4.ogg", String.format("assets/%s/sounds/blocks/coral/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/step/coral5.ogg", String.format("assets/%s/sounds/blocks/coral/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/step/coral6.ogg", String.format("assets/%s/sounds/blocks/coral/step6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/break1.ogg", String.format("assets/%s/sounds/blocks/basalt/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/break2.ogg", String.format("assets/%s/sounds/blocks/basalt/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/break3.ogg", String.format("assets/%s/sounds/blocks/basalt/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/break4.ogg", String.format("assets/%s/sounds/blocks/basalt/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/break5.ogg", String.format("assets/%s/sounds/blocks/basalt/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/step1.ogg", String.format("assets/%s/sounds/blocks/basalt/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/step2.ogg", String.format("assets/%s/sounds/blocks/basalt/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/step3.ogg", String.format("assets/%s/sounds/blocks/basalt/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/step4.ogg", String.format("assets/%s/sounds/blocks/basalt/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/step5.ogg", String.format("assets/%s/sounds/blocks/basalt/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/basalt/step6.ogg", String.format("assets/%s/sounds/blocks/basalt/step6.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/wet_grass1.ogg", String.format("assets/%s/sounds/blocks/wet_grass/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/wet_grass2.ogg", String.format("assets/%s/sounds/blocks/wet_grass/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/wet_grass3.ogg", String.format("assets/%s/sounds/blocks/wet_grass/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/dig/wet_grass4.ogg", String.format("assets/%s/sounds/blocks/wet_grass/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/step/wet_grass1.ogg", String.format("assets/%s/sounds/blocks/wet_grass/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/step/wet_grass2.ogg", String.format("assets/%s/sounds/blocks/wet_grass/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/step/wet_grass3.ogg", String.format("assets/%s/sounds/blocks/wet_grass/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/step/wet_grass4.ogg", String.format("assets/%s/sounds/blocks/wet_grass/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/step/wet_grass5.ogg", String.format("assets/%s/sounds/blocks/wet_grass/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/step/wet_grass6.ogg", String.format("assets/%s/sounds/blocks/wet_grass/step6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/break1.ogg", String.format("assets/%s/sounds/blocks/netherwart/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/break2.ogg", String.format("assets/%s/sounds/blocks/netherwart/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/break3.ogg", String.format("assets/%s/sounds/blocks/netherwart/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/break4.ogg", String.format("assets/%s/sounds/blocks/netherwart/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/break5.ogg", String.format("assets/%s/sounds/blocks/netherwart/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/break6.ogg", String.format("assets/%s/sounds/blocks/netherwart/break6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/step1.ogg", String.format("assets/%s/sounds/blocks/netherwart/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/step2.ogg", String.format("assets/%s/sounds/blocks/netherwart/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/step3.ogg", String.format("assets/%s/sounds/blocks/netherwart/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/step4.ogg", String.format("assets/%s/sounds/blocks/netherwart/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherwart/step5.ogg", String.format("assets/%s/sounds/blocks/netherwart/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/break1.ogg", String.format("assets/%s/sounds/blocks/netherrack/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/break2.ogg", String.format("assets/%s/sounds/blocks/netherrack/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/break3.ogg", String.format("assets/%s/sounds/blocks/netherrack/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/break4.ogg", String.format("assets/%s/sounds/blocks/netherrack/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/break5.ogg", String.format("assets/%s/sounds/blocks/netherrack/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/break6.ogg", String.format("assets/%s/sounds/blocks/netherrack/break6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/step1.ogg", String.format("assets/%s/sounds/blocks/netherrack/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/step2.ogg", String.format("assets/%s/sounds/blocks/netherrack/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/step3.ogg", String.format("assets/%s/sounds/blocks/netherrack/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/step4.ogg", String.format("assets/%s/sounds/blocks/netherrack/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/step5.ogg", String.format("assets/%s/sounds/blocks/netherrack/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherrack/step6.ogg", String.format("assets/%s/sounds/blocks/netherrack/step6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/break1.ogg", String.format("assets/%s/sounds/blocks/netherite/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/break2.ogg", String.format("assets/%s/sounds/blocks/netherite/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/break3.ogg", String.format("assets/%s/sounds/blocks/netherite/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/break4.ogg", String.format("assets/%s/sounds/blocks/netherite/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/step1.ogg", String.format("assets/%s/sounds/blocks/netherite/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/step2.ogg", String.format("assets/%s/sounds/blocks/netherite/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/step3.ogg", String.format("assets/%s/sounds/blocks/netherite/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/step4.ogg", String.format("assets/%s/sounds/blocks/netherite/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/step5.ogg", String.format("assets/%s/sounds/blocks/netherite/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/netherite/step6.ogg", String.format("assets/%s/sounds/blocks/netherite/step6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/break1.ogg", String.format("assets/%s/sounds/blocks/nether_ore/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/break2.ogg", String.format("assets/%s/sounds/blocks/nether_ore/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/break3.ogg", String.format("assets/%s/sounds/blocks/nether_ore/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/break4.ogg", String.format("assets/%s/sounds/blocks/nether_ore/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/step1.ogg", String.format("assets/%s/sounds/blocks/nether_ore/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/step2.ogg", String.format("assets/%s/sounds/blocks/nether_ore/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/step3.ogg", String.format("assets/%s/sounds/blocks/nether_ore/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/step4.ogg", String.format("assets/%s/sounds/blocks/nether_ore/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_ore/step5.ogg", String.format("assets/%s/sounds/blocks/nether_ore/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break1.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break2.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break3.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break4.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break5.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break6.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break7.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break7.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break8.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break8.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/break9.ogg", String.format("assets/%s/sounds/blocks/soul_sand/break9.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/step1.ogg", String.format("assets/%s/sounds/blocks/soul_sand/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/step2.ogg", String.format("assets/%s/sounds/blocks/soul_sand/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/step3.ogg", String.format("assets/%s/sounds/blocks/soul_sand/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/step4.ogg", String.format("assets/%s/sounds/blocks/soul_sand/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_sand/step5.ogg", String.format("assets/%s/sounds/blocks/soul_sand/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/break1.ogg", String.format("assets/%s/sounds/blocks/soul_soil/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/break2.ogg", String.format("assets/%s/sounds/blocks/soul_soil/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/break3.ogg", String.format("assets/%s/sounds/blocks/soul_soil/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/break4.ogg", String.format("assets/%s/sounds/blocks/soul_soil/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/break5.ogg", String.format("assets/%s/sounds/blocks/soul_soil/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/break6.ogg", String.format("assets/%s/sounds/blocks/soul_soil/break6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/step1.ogg", String.format("assets/%s/sounds/blocks/soul_soil/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/step2.ogg", String.format("assets/%s/sounds/blocks/soul_soil/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/step3.ogg", String.format("assets/%s/sounds/blocks/soul_soil/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/step4.ogg", String.format("assets/%s/sounds/blocks/soul_soil/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/soul_soil/step5.ogg", String.format("assets/%s/sounds/blocks/soul_soil/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/break1.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/break2.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/break3.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/break4.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/break5.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/break6.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/break6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/step1.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/step1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/step2.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/step2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/step3.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/step3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/step4.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/step4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/step5.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/step5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/nether_bricks/step6.ogg", String.format("assets/%s/sounds/blocks/nether_bricks/step6.ogg", MODID))
                    .put("assets/minecraft/sounds/block/ancient_debris/break1.ogg", String.format("assets/%s/sounds/blocks/ancient_debris/break1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/ancient_debris/break2.ogg", String.format("assets/%s/sounds/blocks/ancient_debris/break2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/ancient_debris/break3.ogg", String.format("assets/%s/sounds/blocks/ancient_debris/break3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/ancient_debris/break4.ogg", String.format("assets/%s/sounds/blocks/ancient_debris/break4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/ancient_debris/break5.ogg", String.format("assets/%s/sounds/blocks/ancient_debris/break5.ogg", MODID))
                    .put("assets/minecraft/sounds/block/lodestone/place1.ogg", String.format("assets/%s/sounds/blocks/lodestone/place1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/lodestone/place2.ogg", String.format("assets/%s/sounds/blocks/lodestone/place2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/lodestone/place3.ogg", String.format("assets/%s/sounds/blocks/lodestone/place3.ogg", MODID))
                    .put("assets/minecraft/sounds/block/lodestone/place4.ogg", String.format("assets/%s/sounds/blocks/lodestone/place4.ogg", MODID))
                    .put("assets/minecraft/sounds/block/beacon/power1.ogg", String.format("assets/%s/sounds/blocks/beacon/power1.ogg", MODID))
                    .put("assets/minecraft/sounds/block/beacon/power2.ogg", String.format("assets/%s/sounds/blocks/beacon/power2.ogg", MODID))
                    .put("assets/minecraft/sounds/block/beacon/power3.ogg", String.format("assets/%s/sounds/blocks/beacon/power3.ogg", MODID))
                    //textures
                    .put("assets/minecraft/textures/block/water_still.png", String.format("assets/%s/textures/blocks/water_still.png", MODID))
                    .put("assets/minecraft/textures/block/water_still.png.mcmeta", String.format("assets/%s/textures/blocks/water_still.png.mcmeta", MODID))
                    .put("assets/minecraft/textures/block/water_flow.png", String.format("assets/%s/textures/blocks/water_flow.png", MODID))
                    .put("assets/minecraft/textures/block/water_flow.png.mcmeta", String.format("assets/%s/textures/blocks/water_flow.png.mcmeta", MODID))
                    .put("assets/minecraft/textures/block/water_overlay.png", String.format("assets/%s/textures/blocks/water_overlay.png", MODID))
                    .put("assets/minecraft/textures/block/netherite_block.png", String.format("assets/%s/textures/blocks/netherite_block.png", MODID))
                    .put("assets/minecraft/textures/block/ancient_debris_side.png", String.format("assets/%s/textures/blocks/ancient_debris_side.png", MODID))
                    .put("assets/minecraft/textures/block/ancient_debris_top.png", String.format("assets/%s/textures/blocks/ancient_debris_top.png", MODID))
                    .put("assets/minecraft/textures/block/soul_soil.png", String.format("assets/%s/textures/blocks/soul_soil.png", MODID))
                    .build();

            LOGGER.info("Attempting to gather the vanilla 1.18.2 assets required by this mod, this may take a while if it's your first load...");
            final ProgressManager.ProgressBar progressBar = ProgressManager.push("Downloading vanilla assets", assets.size());
            assets.forEach((key, value) -> { //display progress, otherwise it looks like the game froze
                progressBar.step(key.replaceFirst("assets/minecraft/", ""));
                AssetMoverAPI.fromMinecraft("1.18.2", ImmutableMap.of(key, value));
            });

            ProgressManager.pop(progressBar);
            LOGGER.info("Success!");
        }
    }
}
