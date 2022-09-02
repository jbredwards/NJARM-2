package git.jbredwards.njarm.mod.common.item;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemQuickFood extends ItemFood
{
    public int maxItemUseDuration = 16;

    public ItemQuickFood(int amount, float saturation, boolean isWolfFood) { super(amount, saturation, isWolfFood); }
    public ItemQuickFood(int amount, boolean isWolfFood) { super(amount, isWolfFood); }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) { return maxItemUseDuration; }
}
