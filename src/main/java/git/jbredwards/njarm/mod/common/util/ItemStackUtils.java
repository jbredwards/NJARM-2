package git.jbredwards.njarm.mod.common.util;

import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        return StackUtils.copyStackWithSize(stack, stack.getCount() * scale);
    }

    /**
     * @return true if the given IBlockState has the ore dict entry
     */
    public static boolean hasOreName(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull String name) {
        final ItemStack stack = state.getBlock().getItem(world, pos, state);
        return !stack.isEmpty() && StackUtils.hasOreName(stack, name);
    }

    /**
     * Useful for adding an enchantment to an ItemStack during its construction
     */
    @Nonnull
    public static ItemStack addEnchantment(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment, int level) {
        stack.addEnchantment(enchantment, level);
        return stack;
    }
}
