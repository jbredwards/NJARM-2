package git.jbredwards.njarm.mod.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemFire extends ItemFireball
{
    @Nonnull
    @Override
    public String getTranslationKey() { return Blocks.FIRE.getTranslationKey(); }

    @Nonnull
    @Override
    public String getTranslationKey(@Nonnull ItemStack stack) { return getTranslationKey(); }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, @Nonnull EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
        target.setFire(1);
        return super.hitEntity(stack, target, attacker);
    }
}
