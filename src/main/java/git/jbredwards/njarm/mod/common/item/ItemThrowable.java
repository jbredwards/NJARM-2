package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

/**
 *
 * @author jbred
 *
 */
public class ItemThrowable extends Item
{
    @Nonnull public final FromThrower fromThrower;
    @Nonnull public final BooleanSupplier canThrow;
    @Nonnull public final FromDispenser fromDispenser;

    public ItemThrowable(@Nonnull FromThrower fromThrower, @Nonnull FromDispenser fromDispenser) {
        this(fromThrower, () -> true, fromDispenser, () -> true);
    }

    public ItemThrowable(@Nonnull FromThrower fromThrower, @Nonnull BooleanSupplier canThrow, @Nonnull FromDispenser fromDispenser, @Nonnull BooleanSupplier canDispense) {
        this.fromThrower = fromThrower;
        this.canThrow = canThrow;
        this.fromDispenser = fromDispenser;
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new BehaviorProjectileDispense() {
            @Nonnull
            @Override
            public ItemStack dispenseStack(@Nonnull IBlockSource source, @Nonnull ItemStack stack) {
                if(!canDispense.getAsBoolean()) return stack;
                return super.dispenseStack(source, stack);
            }

            @Nonnull
            @Override
            protected IProjectile getProjectileEntity(@Nonnull World worldIn, @Nonnull IPosition position, @Nonnull ItemStack stackIn) {
                return fromDispenser.create(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if(canThrow.getAsBoolean()) {
            final ItemStack stack = playerIn.getHeldItem(handIn);
            if(!playerIn.isCreative()) stack.shrink(1);

            if(!worldIn.isRemote) {
                final EntityThrowable entity = fromThrower.create(worldIn, playerIn);
                entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 1.5f, 1);
                worldIn.spawnEntity(entity);

                SoundUtils.playSound(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ,
                        SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL,
                        0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @FunctionalInterface
    public interface FromThrower
    {
        @Nonnull
        EntityThrowable create(@Nonnull World world, @Nonnull EntityLivingBase thrower);
    }

    @FunctionalInterface
    public interface FromDispenser
    {
        @Nonnull
        IProjectile create(@Nonnull World world, double x, double y, double z);
    }
}
