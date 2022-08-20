package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.entity.item.EntityBlestemArrow;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemBlestemArrow extends ItemArrow
{
    public ItemBlestemArrow() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new BehaviorProjectileDispense() {
            @Nonnull
            @Override
            protected IProjectile getProjectileEntity(@Nonnull World worldIn, @Nonnull IPosition position, @Nonnull ItemStack stackIn) {
                final EntityBlestemArrow arrow = new EntityBlestemArrow(worldIn, position.getX(), position.getY(), position.getZ());
                arrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
                return arrow;
            }
        });
    }

    @Nonnull
    @Override
    public EntityArrow createArrow(@Nonnull World worldIn, @Nonnull ItemStack stack, @Nonnull EntityLivingBase shooter) {
        return new EntityBlestemArrow(worldIn, shooter);
    }
}
