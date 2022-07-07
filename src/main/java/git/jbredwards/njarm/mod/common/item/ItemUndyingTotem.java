package git.jbredwards.njarm.mod.common.item;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class ItemUndyingTotem extends ItemBlock
{
    public ItemUndyingTotem(@Nonnull Block block) { super(block); }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(ConfigHandler.blockCfg.totemOfUndyingCfg.placeable) return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        else return EnumActionResult.PASS;
    }
}
