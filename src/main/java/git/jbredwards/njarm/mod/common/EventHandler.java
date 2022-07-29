package git.jbredwards.njarm.mod.common;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class EventHandler
{
    @SubscribeEvent
    public static void onEntityInteract(@Nonnull PlayerInteractEvent.EntityInteract event) {
        bottlesMilkCows(event.getTarget(), event.getEntityPlayer(), event.getHand());
    }

    static void bottlesMilkCows(@Nonnull Entity entity, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if(entity instanceof EntityCow && !((EntityCow)entity).isChild() && !player.isCreative()) {
            final ItemStack held = player.getHeldItem(hand);
            if(held.getItem() == Items.GLASS_BOTTLE) {
                player.playSound(SoundEvents.ENTITY_COW_MILK, 1, 1);
                held.shrink(1);

                if(held.isEmpty()) player.setHeldItem(hand, new ItemStack(ModItems.MILK_BOTTLE));
                else if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.MILK_BOTTLE)))
                    player.dropItem(new ItemStack(ModItems.MILK_BOTTLE), false);
            }
        }
    }
}
