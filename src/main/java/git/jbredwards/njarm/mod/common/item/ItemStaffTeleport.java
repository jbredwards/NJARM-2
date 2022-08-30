package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.config.item.TeleportStaffConfig;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class ItemStaffTeleport extends Item
{
    @Nonnull
    ItemStack magicStack = ItemStack.EMPTY;

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        final ItemStack held = playerIn.getHeldItem(handIn);
        magicStack = ItemStack.EMPTY;

        final boolean skipAmoCount = playerIn.isCreative() || TeleportStaffConfig.ammoChance() == 0;
        if(skipAmoCount || ChatUtils.getOrError(playerIn, !prepareMagicAmo(playerIn).isEmpty(), "err.njarm.teleportStaff.insufficientAmo")) {
            final double range = TeleportStaffConfig.range();
            final Vec3d eyePos = playerIn.getPositionEyes(1);
            final @Nullable RayTraceResult trace = worldIn.rayTraceBlocks(eyePos,
                    eyePos.add(playerIn.getLookVec().scale(range)), true, false, false);

            if(trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                final double amountToConsume = TeleportStaffConfig.ammoChance() * (eyePos.distanceTo(trace.hitVec) / 10);
                int magicCount = 0;

                if(!skipAmoCount) {
                    ItemStack stack;
                    if(!(stack = playerIn.getHeldItemMainhand()).isEmpty() && StackUtils.hasOreName(stack, "dustMagicNJARM")) magicCount += stack.getCount();
                    if(!(stack = playerIn.getHeldItemOffhand()).isEmpty() && StackUtils.hasOreName(stack, "dustMagicNJARM")) magicCount += stack.getCount();

                    for(int i = 0; i < playerIn.inventory.getSizeInventory() && magicCount <= amountToConsume; i++) {
                        if(!(stack = playerIn.inventory.getStackInSlot(i)).isEmpty() && StackUtils.hasOreName(stack, "dustMagicNJARM"))
                            magicCount += stack.getCount();
                    }
                }

                if(skipAmoCount || ChatUtils.getOrError(playerIn, magicCount >= amountToConsume, "err.njarm.teleportStaff.insufficientAmo")) {
                    if(playerIn.isRiding()) playerIn.dismountRidingEntity();
                    if(ChatUtils.getOrError(playerIn, playerIn.attemptTeleport(trace.getBlockPos().getX() + 0.5, trace.hitVec.y + 2, trace.getBlockPos().getZ() + 0.5), "err.njarm.teleportStaff.obstructed")) {
                        if(!worldIn.isRemote) {
                            SoundUtils.playSound(playerIn, SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1, 1);

                            playerIn.getCooldownTracker().setCooldown(this, TeleportStaffConfig.cooldown());
                            playerIn.fallDistance = 0;

                            if(!playerIn.isCreative()) {
                                held.damageItem((int)(eyePos.distanceTo(trace.hitVec) / 5 + 0.5), playerIn);
                                for(double i = amountToConsume; i > 0; i--) {
                                    if(worldIn.rand.nextFloat() < i) {
                                        if(magicStack.isEmpty()) prepareMagicAmo(playerIn);
                                        magicStack.shrink(1);
                                    }
                                }
                            }
                        }

                        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
                    }
                }
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    public ItemStack prepareMagicAmo(@Nonnull EntityPlayer player) {
        if(!(magicStack = player.getHeldItemMainhand()).isEmpty() && StackUtils.hasOreName(magicStack, "dustMagicNJARM")) return magicStack;
        else if(!(magicStack = player.getHeldItemOffhand()).isEmpty() && StackUtils.hasOreName(magicStack, "dustMagicNJARM")) return magicStack;

        else for(int i = 0; i < player.inventory.getSizeInventory(); i++)
            if(!(magicStack = player.inventory.getStackInSlot(i)).isEmpty() && StackUtils.hasOreName(magicStack, "dustMagicNJARM"))
                return magicStack;

        return magicStack = ItemStack.EMPTY;
    }
}
