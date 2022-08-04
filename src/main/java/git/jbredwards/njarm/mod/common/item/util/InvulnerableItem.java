package git.jbredwards.njarm.mod.common.item.util;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public interface InvulnerableItem
{
    default boolean isExplodeImmune(@Nonnull ItemStack stack) { return false; }
    default boolean isFireImmune(@Nonnull ItemStack stack) { return false; }
}
