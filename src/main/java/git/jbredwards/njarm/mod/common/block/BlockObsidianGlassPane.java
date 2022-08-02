package git.jbredwards.njarm.mod.common.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class BlockObsidianGlassPane extends BlockPane
{
    public BlockObsidianGlassPane(@Nonnull Material materialIn) { super(materialIn, false); }

    @Nonnull
    @Override
    public EnumPushReaction getPushReaction(@Nonnull IBlockState state) { return EnumPushReaction.BLOCK; }

    @Override
    public boolean isToolEffective(@Nonnull String type, @Nonnull IBlockState state) { return false; }

    @Override
    public boolean canEntityDestroy(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return !(entity instanceof EntityDragon);
    }

    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return MapColor.OBSIDIAN;
    }
}
