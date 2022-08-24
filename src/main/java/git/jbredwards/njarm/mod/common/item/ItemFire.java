package git.jbredwards.njarm.mod.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        entity.setFire(1);
        return super.onLeftClickEntity(stack, player, entity);
    }
}
