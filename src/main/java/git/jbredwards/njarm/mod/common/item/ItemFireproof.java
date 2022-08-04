package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.item.util.InvulnerableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemFireproof extends Item implements InvulnerableItem
{
    @Override
    public boolean isFireImmune(@Nonnull ItemStack stack) { return true; }
}
