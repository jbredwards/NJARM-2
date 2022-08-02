package git.jbredwards.njarm.mod.client;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.client.ParticlesConfig;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.darkhax.bookshelf.data.ColorHandlers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID, value = Side.CLIENT)
public final class ClientEventHandler
{
    @SubscribeEvent
    public static void blockColorHandler(@Nonnull ColorHandlerEvent.Block event) {
        //vanilla blocks
        event.getBlockColors().registerBlockColorHandler(withIndex(ColorHandlers.BLOCK_WATER), Blocks.CAULDRON);
        //modded blocks
        event.getBlockColors().registerBlockColorHandler(ColorHandlers.BLOCK_GRASS, ModBlocks.MOSS);
        event.getBlockColors().registerBlockColorHandler(withIndex(ColorHandlers.BLOCK_GRASS), ModBlocks.OVERGROWN_DIRT);
        event.getBlockColors().registerBlockColorHandler(withIndex(ColorHandlers.BLOCK_GRASS), ModBlocks.OVERGROWN_STONE);
    }

    @SubscribeEvent
    public static void itemColorHandler(@Nonnull ColorHandlerEvent.Item event) {
        //modded items
        event.getItemColors().registerItemColorHandler((stack, tintIndex)
                -> ColorizerGrass.getGrassColor(0.5, 1), ModItems.MOSS);
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    static IBlockColor withIndex(@Nonnull IBlockColor color) {
        return (state, world, pos, tintIndex) -> tintIndex == 1
                ? color.colorMultiplier(state, world, pos, 1)
                : -1;
    }

    @SubscribeEvent
    public static void registerTextures(@Nonnull TextureStitchEvent.Pre event) {
        if(event.getMap() == Minecraft.getMinecraft().getTextureMapBlocks()) {
            //ensure these sprites are always registered, as their use cases are hardcoded
            event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/water_still"));
            event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/water_flow"));
            event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/water_overlay"));
            event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "misc/underwater"));
            event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/blue_fire_layer_0"));
            event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/blue_fire_layer_1"));
            event.getMap().registerSprite(new ResourceLocation(ParticlesConfig.getMagicOreParticle()));
            event.getMap().registerSprite(new ResourceLocation(ParticlesConfig.getXpOreParticle()));
            event.getMap().registerSprite(new ResourceLocation(ParticlesConfig.getNetherXpOreParticle()));
            event.getMap().registerSprite(new ResourceLocation(ParticlesConfig.getEndXpOreParticle()));
        }
    }
}
