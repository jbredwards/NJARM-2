package git.jbredwards.njarm.mod.common.item.equipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemFeatherArmor extends ItemArmor
{
    public ItemFeatherArmor(@Nonnull ArmorMaterial materialIn, int renderIndexIn, @Nonnull EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
    }

    @Override
    public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        if(!player.isSneaking() && player.motionY < 0 && !player.isElytraFlying()) {
            player.motionY *= 0.6;
            player.fallDistance = 0;
        }
    }
}
