package git.jbredwards.njarm.mod.common.item.equipment;

import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import git.jbredwards.njarm.mod.common.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemSapphireArmor extends ItemArmor
{
    public ItemSapphireArmor(@Nonnull ArmorMaterial materialIn, int renderIndexIn, @Nonnull EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
    }

    @Override
    public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        if(!world.isRemote) {
            for(ItemStack stack : player.getArmorInventoryList())
                if(!(stack.getItem() instanceof ItemSapphireArmor))
                    return;

            if(EquipmentConfig.sapphireFireResist()) player.addPotionEffect(
                    new PotionEffect(MobEffects.FIRE_RESISTANCE, 20));

            if(EquipmentConfig.sapphireBlueFireResist()) player.addPotionEffect(
                    new PotionEffect(ModPotions.BLUE_FIRE_RESISTANCE, 20));
        }
    }
}
