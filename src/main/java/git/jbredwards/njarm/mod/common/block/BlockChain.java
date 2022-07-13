package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.common.config.block.ChainConfig;
import git.jbredwards.njarm.mod.common.util.AABBUtils;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public class BlockChain extends BlockRotatedPillar
{
    @Nonnull
    protected static final AxisAlignedBB aabb = AABBUtils.of(7, 0, 7, 9, 16, 9);

    public BlockChain(@Nonnull Material materialIn) { super(materialIn); }
    public BlockChain(@Nonnull Material materialIn, @Nonnull MapColor color) { super(materialIn, color); }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) { return false; }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) { return false; }

    @Override
    public boolean isLadder(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entity) {
        return ChainConfig.isLadder() && state.getValue(AXIS) == EnumFacing.Axis.Y;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        switch(state.getValue(AXIS)) {
            case Y: return aabb;
            case X: return AABBUtils.rotate(aabb, EnumFacing.Axis.Z);
            case Z: return AABBUtils.rotate(aabb, EnumFacing.Axis.X);
        }

        return aabb;
    }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() { return BlockRenderLayer.CUTOUT; }
}
