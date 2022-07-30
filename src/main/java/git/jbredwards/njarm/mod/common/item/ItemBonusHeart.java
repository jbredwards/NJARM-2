package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.capability.IBonusHealth;
import git.jbredwards.njarm.mod.common.config.item.BonusHeartConfig;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class ItemBonusHeart extends Item
{
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        final @Nullable IBonusHealth cap = IBonusHealth.get(playerIn);
        if(cap != null) {
            final ItemStack held = playerIn.getHeldItem(handIn);

            if(!playerIn.isSneaking()) {
                if(ChatUtils.getOrError(playerIn, playerIn.getMaxHealth() + BonusHeartConfig.amount() <= 1024, "err.njarm.bonusHeart.reachedMaxHealth")) {
                    if(!playerIn.isCreative()) held.shrink(1);
                    cap.incrementBonusHealth(BonusHeartConfig.amount());

                    if(BonusHeartConfig.heal()) playerIn.heal(playerIn.getMaxHealth());
                    return new ActionResult<>(EnumActionResult.SUCCESS, held);
                }
            }

            else {
                final int count = held.getCount();
                int amountUsed = 0;

                float currHealth = playerIn.getMaxHealth();
                while((currHealth += BonusHeartConfig.amount()) <= 1024 && amountUsed < count) amountUsed++;

                if(ChatUtils.getOrError(playerIn, amountUsed > 0, "err.njarm.bonusHeart.reachedMaxHealth")) {
                    if(!playerIn.isCreative()) held.shrink(amountUsed);
                    cap.incrementBonusHealth(BonusHeartConfig.amount() * amountUsed);

                    if(BonusHeartConfig.heal()) playerIn.heal(playerIn.getMaxHealth());
                    return new ActionResult<>(EnumActionResult.SUCCESS, held);
                }
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
