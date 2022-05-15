package git.jbredwards.njarm.mod.common.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.WorldEvents;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemBonemeal extends Item
{
    public ItemBonemeal() {
        super();
        dispenser();
    }

    protected void dispenser() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, (source, stack) -> {
            final EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
            final World world = source.getWorld();
            final BlockPos pos = source.getBlockPos();
            final BlockPos offset = pos.add(facing.getDirectionVec());
            //can apply bonemeal here
            if(ItemDye.applyBonemeal(stack, world, offset)) {
                world.playEvent(WorldEvents.BONEMEAL_PARTICLES, offset, 0);
                world.playEvent(WorldEvents.DISPENSER_DISPENSE_SOUND, pos, 0);
            }
            //can't apply bonemeal here
            else world.playEvent(WorldEvents.DISPENSER_FAIL_SOUND, pos, 0);
            world.playEvent(WorldEvents.DISPENSER_SMOKE, pos, facing.getXOffset() + 1 + (facing.getZOffset() + 1) * 3);
            return stack;
        });
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        final @Nonnull EnumActionResult result = applyBonemeal(player, worldIn, pos, hand, facing);
        if(result != EnumActionResult.PASS) return result;
        else return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Nonnull
    public EnumActionResult applyBonemeal(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing) {
        final ItemStack held = player.getHeldItem(hand);
        //player's in adventure mode (probably)
        if(!player.canPlayerEdit(pos.offset(facing), facing, held)) return EnumActionResult.FAIL;
        //try to apply bonemeal
        else if(ItemDye.applyBonemeal(held, world, pos, player, hand)) {
            if(!world.isRemote) world.playEvent(WorldEvents.BONEMEAL_PARTICLES, pos, 0);
            return EnumActionResult.SUCCESS;
        }
        //default
        else return EnumActionResult.PASS;
    }
}
