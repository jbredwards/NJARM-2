package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemRupee extends Item
{
    @SubscribeEvent
    public static void onItemPickup(@Nonnull PlayerEvent.ItemPickupEvent event) {
        if(ConfigHandler.itemCfg.rupeeCfg.sound && event.getStack().getItem() instanceof ItemRupee)
            SoundUtils.playSound(event.getOriginalEntity(), ModSounds.RUPEE_PICKUP, 1, 1);
    }
}
