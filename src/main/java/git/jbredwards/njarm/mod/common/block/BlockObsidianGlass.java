package git.jbredwards.njarm.mod.common.block;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class BlockObsidianGlass extends BlockBreakable
{
    public BlockObsidianGlass(@Nonnull Material materialIn, boolean ignoreSimilarityIn) { super(materialIn, ignoreSimilarityIn); }
    public BlockObsidianGlass(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn, boolean ignoreSimilarityIn) {
        super(materialIn, ignoreSimilarityIn, mapColorIn);
    }

    public int quantityDropped(@Nonnull Random random) { return 0; }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() { return BlockRenderLayer.CUTOUT; }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) { return false; }

    @Override
    protected boolean canSilkHarvest() { return true; }

    @Nonnull
    @Override
    public EnumPushReaction getPushReaction(@Nonnull IBlockState state) { return EnumPushReaction.BLOCK; }

    @Override
    public boolean isToolEffective(@Nonnull String type, @Nonnull IBlockState state) { return false; }

    @Override
    public boolean canEntityDestroy(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return !(entity instanceof EntityDragon);
    }
}
