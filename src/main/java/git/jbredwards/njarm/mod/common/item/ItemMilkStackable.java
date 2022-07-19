package git.jbredwards.njarm.mod.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.PotionEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

/**
 *
 * @author jbred
 *
 */
public class ItemMilkStackable extends ItemBucketMilk
{
    protected boolean canRemoveEffect(@Nonnull PotionEffect effect) { return true; }
    protected void onPlayerConsume(@Nonnull EntityPlayer player) {}

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving) {
        if(!worldIn.isRemote) for(Iterator<PotionEffect> it = entityLiving.getActivePotionEffects().iterator(); it.hasNext();) {
            final PotionEffect effect = it.next();
            if(canRemoveEffect(effect) && effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET))
                    && !MinecraftForge.EVENT_BUS.post(new PotionEvent.PotionRemoveEvent(entityLiving, effect))) {

                entityLiving.onFinishedPotionEffect(effect);
                it.remove();
            }
        }

        if(entityLiving instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entityLiving;
            onPlayerConsume(player);

            if(player instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)player, stack);
                player.addStat(StatList.getObjectUseStats(this));
            }

            if(!player.isCreative()) {
                stack.shrink(1);
                if(stack.isEmpty()) return getContainerItem(stack);
                else if(hasContainerItem(stack) && !player.inventory.addItemStackToInventory(getContainerItem(stack)))
                    player.dropItem(getContainerItem(stack), false);
            }
        }

        return stack;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nonnull NBTTagCompound nbt) { return null; }
}
