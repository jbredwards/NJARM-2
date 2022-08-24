package git.jbredwards.njarm.mod.common.item.equipment;

import git.jbredwards.njarm.mod.Constants;
import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ItemObsidianArmor extends ItemArmor
{
    public ItemObsidianArmor(@Nonnull ArmorMaterial materialIn, int renderIndexIn, @Nonnull EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void applyKnockbackResist(@Nonnull LivingKnockBackEvent event) {
        if(EquipmentConfig.obsidianResistKnockback()) {
            float armorModifier = 1;
            for(ItemStack stack : event.getEntityLiving().getArmorInventoryList())
                if(stack.getItem() instanceof ItemObsidianArmor) armorModifier -= 0.25;

            if(armorModifier < 1) {
                if(armorModifier <= 0) event.setCanceled(true);
                else event.setStrength(event.getStrength() * armorModifier);
            }
        }
    }
}
