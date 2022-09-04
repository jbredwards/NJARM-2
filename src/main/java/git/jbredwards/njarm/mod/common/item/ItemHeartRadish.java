package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemHeartRadish extends ItemFood
{
    public ItemHeartRadish(int amount, float saturation, boolean isWolfFood) { super(amount, saturation, isWolfFood); }
    public ItemHeartRadish(int amount, boolean isWolfFood) { super(amount, isWolfFood); }

    @Override
    protected void onFoodEaten(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        player.heal(this == ModItems.GOLD_HEART_RADISH ? player.getMaxHealth() : 4);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return this == ModItems.GOLD_HEART_RADISH || super.hasEffect(stack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) { return this == ModItems.GOLD_HEART_RADISH ? 64 : 32; }
}
