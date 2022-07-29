package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class ItemMagicMirror extends Item
{
    public static final byte NORMAL = 0, BEDROCK = 1, DIMENSIONAL = 2;
    public final byte mirrorType;

    public ItemMagicMirror(byte mirrorType) { this.mirrorType = mirrorType; }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        else if(useMirror(worldIn, playerIn, playerIn)) {
            final ItemStack stack = playerIn.getHeldItem(handIn);
            damageAndCooldown(stack, playerIn);

            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        if(!(target instanceof EntityPlayer) && playerIn.isSneaking()) {
            if(playerIn.world.isRemote) return true;
            else if(useMirror(playerIn.world, playerIn, target)) {
                damageAndCooldown(stack, playerIn);
                return true;
            }
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @SuppressWarnings("ConstantConditions")
    protected boolean useMirror(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target) {
        final int dimToWarp = player.getSpawnDimension();
        final boolean changedDims = world.provider.getDimension() != dimToWarp;

        if((mirrorType & DIMENSIONAL) != 0 || !changedDims) {
            final World newWorld = changedDims ? DimensionManager.getWorld(dimToWarp) : world;

            @Nullable BlockPos bedPos = player.getBedLocation(dimToWarp);
            if(bedPos != null) //check for bed location and adjust if obstructed
                bedPos = EntityPlayer.getBedSpawnLocation(newWorld, bedPos, player.isSpawnForced(dimToWarp));

            if(bedPos == null) { //fallback to world spawn for players
                if(target instanceof EntityPlayer) bedPos = newWorld.getTopSolidOrLiquidBlock(newWorld.getSpawnPoint());
                if(!player.hasSpawnDimension()) player.sendMessage(new TextComponentTranslation("tile.bed.notValid"));
            }

            if(bedPos != null) {
                target.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.5f, 1);
                spawnParticles((WorldServer)world, target);

                final BlockPos newPos = bedPos;
                if(changedDims) target.changeDimension(dimToWarp, (worldIn, entity, yaw) -> {
                    entity.rotationYaw = yaw;
                    entity.fallDistance = 0;
                    entity.setPositionAndUpdate(newPos.getX() + 0.5, newPos.getY(), newPos.getZ() + 0.5);
                    entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.5f, 1);
                });

                else {
                    target.fallDistance = 0;
                    target.setPositionAndUpdate(newPos.getX() + 0.5, newPos.getY(), newPos.getZ() + 0.5);
                    SoundUtils.playSound(target, SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.5f, 1);
                }

                spawnParticles((WorldServer)newWorld, target);
                return true;
            }
        }

        else player.sendMessage(new TextComponentTranslation("err.njarm.magicMirror.wrongDimension"));
        return false;
    }

    protected void damageAndCooldown(@Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
        player.getCooldownTracker().setCooldown(this, 100);
        if(!player.isCreative() && (mirrorType & BEDROCK) == 0) {
            if(stack.getMaxDamage() > 0) stack.damageItem(1, player);
            else {
                player.renderBrokenItemStack(stack);
                stack.shrink(1);
            }
        }
    }

    protected void spawnParticles(@Nonnull WorldServer world, @Nonnull EntityLivingBase target) {
        world.spawnParticle(EnumParticleTypes.PORTAL, false, target.posX, target.posY + target.height / 2 - 0.5,
                target.posZ, 128, target.width / 2, target.height / 4, target.width / 2, 0.2);
    }
}
