package git.jbredwards.njarm.mod.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemFoodLeftovers extends ItemPotionFood
{
    public ItemFoodLeftovers(int amount, float saturation, boolean isWolfFood) { super(amount, saturation, isWolfFood); }
    public ItemFoodLeftovers(int amount, boolean isWolfFood) { super(amount, isWolfFood); }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving) {
        final ItemStack remaining = super.onItemUseFinish(stack, worldIn, entityLiving);
        if(!hasContainerItem(stack)) return remaining;

        final ItemStack container = getContainerItem(stack);
        if(remaining.isEmpty()) return container;

        else if(entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).inventory.addItemStackToInventory(container))
            ((EntityPlayer)entityLiving).dropItem(container, false);

        return remaining;
    }
}
