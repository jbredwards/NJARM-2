package git.jbredwards.njarm.mod.common.entity.passive;

import git.jbredwards.njarm.mod.common.init.ModItems;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class EntityChocolateCow extends EntityCow
{
    public EntityChocolateCow(@Nonnull World worldIn) { super(worldIn); }

    @Override
    public boolean processInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        final ItemStack held = player.getHeldItem(hand);
        if(held.getItem() == Items.BUCKET && !player.isCreative() && !isChild()) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1, 1);
            held.shrink(1);

            if(held.isEmpty()) player.setHeldItem(hand, new ItemStack(ModItems.CHOCOLATE_MILK_BUCKET));
            else if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.CHOCOLATE_MILK_BUCKET)))
                player.dropItem(new ItemStack(ModItems.CHOCOLATE_MILK_BUCKET), false);

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public EntityChocolateCow createChild(@Nonnull EntityAgeable ageable) { return new EntityChocolateCow(world); }
}
