package git.jbredwards.njarm.mod.common.item.equipment;

import git.jbredwards.njarm.mod.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemShulkerArmor extends ItemArmor
{
    public ItemShulkerArmor(@Nonnull ArmorMaterial materialIn, int renderIndexIn, @Nonnull EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
    }

    @Override
    public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        player.removePotionEffect(MobEffects.LEVITATION);
    }

    @SubscribeEvent
    public static void fixLevitationApplicability(@Nonnull PotionEvent.PotionApplicableEvent event) {
        if(event.getPotionEffect().getPotion() == MobEffects.LEVITATION) {
            for(ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
                if(stack.getItem() instanceof ItemShulkerArmor) {
                    event.setResult(Event.Result.DENY);
                    return;
                }
            }
        }
    }
}
