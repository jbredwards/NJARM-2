package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.entity.item.EntityDummy;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemDummy extends Item
{
    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(facing != EnumFacing.DOWN) {
            final BlockPos placeAt = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) ? pos : pos.offset(facing);
            final ItemStack held = player.getHeldItem(hand);

            if(player.canPlayerEdit(placeAt, facing, held)) {
                final boolean isReplaceable = worldIn.getBlockState(placeAt).getBlock().isReplaceable(worldIn, placeAt)
                        && worldIn.getBlockState(placeAt.up()).getBlock().isReplaceable(worldIn, placeAt.up());

                if(isReplaceable && worldIn.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(placeAt, placeAt.add(1, 2, 1))).isEmpty()) {
                    if(!worldIn.isRemote) {
                        final EntityDummy dummy = new EntityDummy(worldIn);
                        dummy.setLocationAndAngles(placeAt.getX() + 0.5, placeAt.getY(), placeAt.getZ() + 0.5,
                                MathHelper.floor((MathHelper.wrapDegrees(player.rotationYaw - 180) + 22.5) / 45) * 45,
                                45 * (float)Math.PI / 180);

                        SoundUtils.playSound(worldIn, placeAt, SoundEvents.ENTITY_ARMORSTAND_PLACE, SoundCategory.BLOCKS, 0.75f, 0.8f);
                        ItemMonsterPlacer.applyItemEntityDataToEntity(worldIn, player, held, dummy);
                        worldIn.spawnEntity(dummy);

                        worldIn.setBlockToAir(placeAt);
                        worldIn.setBlockToAir(placeAt.up());
                        held.shrink(1);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }
        }

        return EnumActionResult.FAIL;
    }
}
