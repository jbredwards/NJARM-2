package git.jbredwards.njarm.mod.common.item.block;

import git.jbredwards.njarm.mod.common.item.util.InvulnerableItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemBlockFireproof extends ItemBlock implements InvulnerableItem
{
    public ItemBlockFireproof(@Nonnull Block block) { super(block); }

    @Override
    public boolean isFireImmune(@Nonnull ItemStack stack) { return true; }
}
