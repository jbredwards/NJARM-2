package git.jbredwards.njarm.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
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
public class BlockIceMushroom extends BlockMushroom implements IShearable
{
    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if(!canBlockStay(worldIn, pos, state) || worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11) worldIn.setBlockToAir(pos);
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        if(!canBlockStay(worldIn, pos, state)) worldIn.setBlockToAir(pos);
    }

    @Override
    public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
        return canBlockStay(worldIn, pos, getDefaultState());
    }

    @Override
    public boolean canBlockStay(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        final IBlockState down = worldIn.getBlockState(pos.down());
        return down.getBlock().canSustainPlant(down, worldIn, pos.down(), EnumFacing.UP, this);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) { return Items.AIR; }

    @Override
    public boolean canGrow(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        return NonNullList.from(item);
    }

    @Nonnull
    @Override
    public EnumPlantType getPlantType(@Nonnull IBlockAccess world, @Nonnull BlockPos pos) { return EnumPlantType.Cave; }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() { return BlockRenderLayer.TRANSLUCENT; }
}
