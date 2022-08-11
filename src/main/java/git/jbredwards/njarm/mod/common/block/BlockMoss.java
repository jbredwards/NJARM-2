package git.jbredwards.njarm.mod.common.block;

import git.jbredwards.njarm.mod.common.block.util.IHasRunningEffects;
import git.jbredwards.njarm.mod.common.util.AABBUtils;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jbred
 *
 */
public class BlockMoss extends BlockBush implements IShearable, IHasRunningEffects
{
    @Nonnull
    public static final AxisAlignedBB AABB = AABBUtils.of(0, 0, 0, 16, 2, 16);

    public BlockMoss(@Nonnull Material materialIn) { this(materialIn, materialIn.getMaterialMapColor()); }
    public BlockMoss(@Nonnull Material materialIn, @Nonnull MapColor mapColorIn) { super(materialIn, mapColorIn); }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return AABB;
    }

    @Override
    public int quantityDropped(@Nonnull Random random) { return 0; }

    @Override
    protected boolean canSustainBush(@Nonnull IBlockState state) { return state.isTopSolid(); }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        return NonNullList.from(new ItemStack(this));
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() { return BlockRenderLayer.SOLID; }
}
