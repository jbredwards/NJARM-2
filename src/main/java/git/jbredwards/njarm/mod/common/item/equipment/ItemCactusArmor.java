package git.jbredwards.njarm.mod.common.item.equipment;

import git.jbredwards.njarm.mod.common.config.item.EquipmentConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemCactusArmor extends ItemArmor
{
    public ItemCactusArmor(@Nonnull ArmorMaterial materialIn, int renderIndexIn, @Nonnull EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if(isInCreativeTab(tab)) {
            final ItemStack stack = new ItemStack(this);
            if(EquipmentConfig.cactusThorns() > 0)
                stack.addEnchantment(Enchantments.THORNS, EquipmentConfig.cactusThorns());

            items.add(stack);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        if(!super.hasEffect(stack)) return false;
        return EquipmentConfig.cactusThorns() <= 0 ||
                EnchantmentHelper.getEnchantmentLevel(Enchantments.THORNS, stack) > EquipmentConfig.cactusThorns();
    }
}
