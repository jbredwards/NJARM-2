package git.jbredwards.njarm.mod.client;

import git.jbredwards.njarm.mod.Constants;
import net.darkhax.bookshelf.data.ColorHandlers;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

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
        event.getBlockColors().registerBlockColorHandler(
                //ensure only the water is colored
                (state, world, pos, tintIndex) -> tintIndex == 1 ? ColorHandlers.BLOCK_WATER.colorMultiplier(state, world, pos, 1) : -1,
                Blocks.CAULDRON);
    }

    @SubscribeEvent
    public static void registerTextures(@Nonnull TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/water_still"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/water_flow"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID, "blocks/water_overlay"));
    }
}
