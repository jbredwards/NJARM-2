package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.item.ChargedSunstoneConfig;
import git.jbredwards.njarm.mod.common.init.ModItems;
import git.jbredwards.njarm.mod.common.item.util.InvulnerableItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemSunstone extends Item implements InvulnerableItem
{
    @Override
    public boolean isFireImmune(@Nonnull ItemStack stack) { return true; }

    @Override
    public boolean isExplodeImmune(@Nonnull ItemStack stack) { return true; }

    @SubscribeEvent
    public static void onLightningStrike(@Nonnull EntityStruckByLightningEvent event) {
        if(event.getEntity() instanceof EntityItem && !event.getEntity().world.isRemote) {
            final EntityItem entity = (EntityItem)event.getEntity();
            final ItemStack stack = entity.getItem();

            if(stack.getItem() == ModItems.SUNSTONE) {
                if(ChargedSunstoneConfig.fromLightning()) entity.setItem(new ItemStack(ModItems.CHARGED_SUNSTONE,
                        stack.getCount(), stack.getMetadata(), stack.getTagCompound()));

                event.setCanceled(true);
            }

            else if(stack.getItem() == ModItems.CHARGED_SUNSTONE) event.setCanceled(true);
        }
    }
}
