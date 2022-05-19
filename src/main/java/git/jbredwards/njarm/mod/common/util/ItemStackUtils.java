package git.jbredwards.njarm.mod.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ItemStackUtils
{
    /**
     * @return a copy of the input stack with its count multiplied
     */
    @Nonnull
    public static ItemStack copyStackWithScale(@Nonnull ItemStack stack, int scale) {
        return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() * scale);
    }
}
